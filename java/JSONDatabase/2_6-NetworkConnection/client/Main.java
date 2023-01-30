package client;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF("5");

            System.out.println("Sent: Give me a record # " + "5");
            System.out.println("Received: A record # " + inputStream.readUTF() + " was sent!");

        } catch (IOException e) {
            System.out.println("Could not connect to server!");
        }
    }
}
