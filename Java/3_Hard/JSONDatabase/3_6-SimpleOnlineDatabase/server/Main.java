package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static String[] database = new String[1000];

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

                if (message.equalsIgnoreCase("exit")) {
                    isExit = true;
                    output.writeUTF("exit");
                } else {
                    String[] messages = message.split(" ", 3);
                    int index = Integer.parseInt(messages[1]) - 1;
                    if (messages[0].equals("get") && !elementExists(index)) {
                        output.writeUTF("ERROR");
                    } else {
                        switch (messages[0]) {
                            case "set" -> {
                                database[index] = messages[2];
                                output.writeUTF("OK");
                            }
                            case "delete" -> {
                                database[index] = null;
                                output.writeUTF("OK");
                            }
                            case "get" -> output.writeUTF(database[index]);
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

    public static boolean elementExists(int index) {
        return database[index] != null;
    }


}
