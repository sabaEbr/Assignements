package com.GO.network;


import com.GO.network.communication.Client;

import java.io.IOException;


public class GoNetwork extends Client implements Runnable{

    private static String serverPort = "20020";
    private static String serverIP = "127.0.0.1"; // localhost server

    private volatile String jsonGameData;
    private volatile long connectionID;
    private volatile boolean eventSignal; // Signal event to get of post data ready for parsing

    private volatile byte mode; // get or post
    public final byte getMode = 0;
    public final byte postMode = 1;
    public final byte terminateMode = 2;

    private int updateFreq = 2; // 2 Hz

    public GoNetwork() throws Exception{
        super(serverIP, serverPort);

        this.jsonGameData = this.msgin;
        setEventSignal(true);

        // Init to get mode
        setGetMode();
    }

    public void setGetMode(){
        this.mode = getMode;
    }

    public void setPostMode(){
        this.mode = postMode;
    }

    public synchronized void setJsonGameData(String jsonGameData){
        this.jsonGameData = jsonGameData;
    }

    public synchronized String getJsonGameData() {
        return jsonGameData;
    }

    public synchronized void setEventSignal(boolean state){
        this.eventSignal = state;
    }

    public synchronized boolean getEventSignal(){
        return eventSignal;
    }

    public synchronized void setConnectionID(Long ID){
        this.connectionID = ID;
    }

    public synchronized long getConnectionID(){
        return this.connectionID;
    }

    @Override
    public void run() {

        long lastUpdate = System.currentTimeMillis();
        while(mode != terminateMode){
            if(System.currentTimeMillis() - lastUpdate > 100/updateFreq){
                if(mode == getMode){
                    try {
                        this.msgin = super.get(getConnectionID());
                        if(! msgin.equals(this.jsonGameData)){
                            setJsonGameData(msgin);
                            setEventSignal(true);
                        }
                    } catch (IOException e){
                        System.out.println("Error Connecting to Server");
                    }
                } else if(mode == postMode){
                    try {
                        if (getEventSignal()){
                            this.msgin = super.post(jsonGameData);
                            if(! msgin.equals(this.jsonGameData)){
                                setJsonGameData(msgin);
                                synchronized (this){
                                    notifyAll();
                                }
                                setEventSignal(false);
                            } else{
                                setEventSignal(false);
                            }
                        }

                    } catch (IOException e){
                        System.out.println("Error Connecting to Server");
                    }
                }

                lastUpdate = System.currentTimeMillis();
            }

        }

    }
}
