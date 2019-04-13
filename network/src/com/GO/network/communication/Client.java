package com.GO.network.communication;


import java.io.IOException;
import java.net.*;
import java.io.*;


public class Client {

    private String baseURLPath;

    protected String msgin = "", msgout = ""; // message input and output strings


    public Client(String serverIP, String serverPort, byte nCells) throws IOException {
        this.baseURLPath = "http://" + serverIP + ":" + serverPort;

        msgin = requestHTTP(baseURLPath + "/new/" + Byte.toString(nCells));
    }

    public String post(String msg) throws IOException{

        msgin = requestHTTP(baseURLPath + "/post/" + msg);

        return msgin;

    }

    public String get(String msg) throws IOException{

        msgin = requestHTTP(baseURLPath + "/get/" + msg);

        return msgin;
    }

    public String terminate(String msg) throws IOException{
        msgin = requestHTTP(baseURLPath + "/terminate/" + msg);

        return msgin;
    }

    public static String requestHTTP(String s_url) throws IOException{
        URL url = new URL(s_url);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line, final_answer="No response";

        while ((line = in.readLine()) != null) {
            final_answer = line;
        }

        in.close();

        return final_answer;
    }
}
