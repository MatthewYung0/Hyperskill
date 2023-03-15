package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        String message;

        try (ServerSocket server = new ServerSocket(port, 100, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            Socket socket = server.accept();

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            message = input.readUTF();
            System.out.println("Received: Give me a record # " + message);

            output.writeUTF(message);
            System.out.println("Sent: A record # " + message + " was sent!");

        } catch (IOException e) {
            System.out.println("Could not start sever!");
        }
    }


}
