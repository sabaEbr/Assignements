package com.GO.core.engine;


import com.GO.network.GoNetwork;


public class GoMultiplayer extends GoEngine {

    private GoNetwork network;
    private byte playerInGame; // 1 or 2


    public GoMultiplayer(byte mode, byte nCells){
        super(mode, nCells);

        try {
            network = new GoNetwork(nCells);
            unMarshall(network.getJsonGameData());
            network.setConnectionID(gameID);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error initiating Go network");
            // Send game to end of game state with error message
        }

        playerInGame = nPlayers;
        playValidity = false;

        Thread thread = new Thread(network);
        thread.start();
    }

    @Override
    public boolean newMove(int x, int y){
        if(turn == playerInGame && playValidity) {
            if(super.newMove(x, y)){
                playValidity = false;
                network.setJsonGameData(marshall());
                network.setEventSignal(true);
                try {
                    synchronized (network){
                        network.wait();
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                    System.out.println("Error waiting for move send to Go network");
                }
                network.setGetMode();
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public void triggerTurnSwitch() {
        if(turn == playerInGame && playValidity) {
            super.triggerTurnSwitch();
            playValidity = false;
            network.setJsonGameData(marshall());
            if (status == 1) {
                network.setTerminateMode();
            } else {
                network.setEventSignal(true);
            }
            try {
                synchronized (network) {
                    network.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error waiting for turn skip send to Go network");
            }
            if (status != 1) {
                network.setGetMode();
            }
        }
    }

    @Override
    public void update(){
        if(network.getEventSignal()) {
            try {
                unMarshall(network.getJsonGameData());
                if(status == 1){
                    network.setTerminateMode();
                    try {
                        synchronized (network) {
                            network.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Error ending game in Go network");
                    }
                }else if(turn == playerInGame && status != 2){
                    playValidity = true;
                    network.setPostMode();
                    network.setEventSignal(false); // Done
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Error updating Go network");
            }
        }
    }

}
