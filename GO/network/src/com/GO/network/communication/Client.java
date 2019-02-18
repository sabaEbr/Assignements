package com.GO.network.communication;


import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Client implements Runnable{

    private String serverPort = "20020";
    private String serverIP = "127.0.0.1"; // localhost server

    private String msgin = "", msgout = ""; // message input and output strings

    private String jStones = ""; // JSON representation of stones
    private String jTurn = "";
    private String jPlayer = "";

    private int gameID;
    private int playerID;

    public static void main (String[] args) throws Exception{
//        try {
//            new Client();
//        } catch (IOException e){
//            System.out.println("Error: HTTP Request failed -- errno 1");
//            System.exit(1);
//        }

        URL url = new URL("http://127.0.0.1:20020/new_game");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            String[] split_answer = inputLine.split("&", 2);
            int gameID = Integer.parseInt(split_answer[0]);
            int playerID = Integer.parseInt(split_answer[1]);
            System.out.println(gameID + " and " + playerID);
        }
        in.close();
    }

    public Client() throws IOException {

        URL url = new URL("http://" + serverIP + serverPort + "/new_game");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((msgin = in.readLine()) != null) {
            String[] split_answer = msgin.split("&", 2);
            gameID = Integer.parseInt(split_answer[0]);
            playerID = Integer.parseInt(split_answer[1]);
        }

        in.close();

    }

    @Override
    public void run() {

    }
}
