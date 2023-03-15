package server;

import common.COMMANDS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Session implements Runnable {
    private final Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String message = input.readUTF();
            if (message.equalsIgnoreCase(COMMANDS.EXIT.toString())) {
                Database.updateJsonFile();
                output.writeUTF("{\"response\":\"OK\"}");
                Main.setExit(true);
            } else {
                String[] messages = message.split(" ", 3);
                String key = messages[1];
                if ((messages[0].equals(COMMANDS.GET.toString().toLowerCase()) && elementDoesNotExist(key)) ||
                        (messages[0].equals(COMMANDS.DELETE.toString().toLowerCase()) && elementDoesNotExist(key))) {
                    output.writeUTF("{\"response\":\"ERROR\",\"reason\":\"No such key\"}");
                } else {
                    switch (messages[0]) {
                        case "set":
                            Database.set(key, messages[2]);
                            output.writeUTF("{\"response\":\"OK\"}");
                            break;
                        case "delete":
                            Database.delete(key);
                            output.writeUTF("{\"response\":\"OK\"}");
                            break;
                        case "get":
                            output.writeUTF("{\"response\":\"OK\",\"value\":\"" + Database.get(key) + "\"}");
                            break;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean elementDoesNotExist(String index) {
        return Database.get(index) == null;
    }

}
