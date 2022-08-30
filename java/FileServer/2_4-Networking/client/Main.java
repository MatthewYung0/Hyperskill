package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String outputMessage = "Give me everything you have!";
            output.writeUTF(outputMessage);
            System.out.println("Sent: " + outputMessage);

            DataInputStream input = new DataInputStream(socket.getInputStream());
            String receivedMessage = input.readUTF();
            System.out.println("Received: " + receivedMessage);

            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
