package server;

import common.COMMANDS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Main {
    private static final HashMap<String, String> database = new HashMap<>();

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        String message;
        boolean isExit = false;

        try (ServerSocket server = new ServerSocket(port, 100, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            do {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                message = input.readUTF();

                if (message.equalsIgnoreCase(COMMANDS.EXIT.toString())) {
                    isExit = true;
                    output.writeUTF(COMMANDS.EXIT.toString().toLowerCase());
                } else {
                    String[] messages = message.split(" ", 3);
                    String key = messages[1];
                    if ((messages[0].equals(COMMANDS.GET.toString().toLowerCase()) && !elementExists(key)) ||
                            (messages[0].equals(COMMANDS.DELETE.toString().toLowerCase()) && !elementExists(key))) {
                        output.writeUTF("{\"response\":\"ERROR\",\"reason\":\"No such key\"}");
                    } else {
                        switch (messages[0]) {
                            case "set":
                                database.put(key, messages[2]);
                                output.writeUTF("{\"response\":\"OK\"}");
                                break;
                            case "delete":
                                database.put(key, null);
                                output.writeUTF("{\"response\":\"OK\"}");
                                break;
                            case "get":
                                output.writeUTF("{\"response\":\"OK\",\"value\":\"" + database.get(key) + "\"}");
                                break;
                            case "exit":
                                output.writeUTF("{\"response\":\"exit\"}");
                        }
                    }
                }
                input.close();
                output.close();
                socket.close();
            } while (!isExit);
        } catch (IOException e) {
            System.out.println("Could not start sever!");
        }
    }

    public static boolean elementExists(String index) {
        return database.get(index) != null;
    }


}
