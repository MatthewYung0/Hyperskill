package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static String serverDataPath = System.getProperty("user.dir") + "\\JSON Database\\task\\src\\server\\data\\db.json";
    public static boolean isExit = false;

    public static void main(String[] args) {

        String address = "127.0.0.1";
        int port = 23456;

        try (ServerSocket server = new ServerSocket(port, 100, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            while (!isExit) {
                Session session = new Session(server.accept());
                executor.submit(session).get();
            }
            executor.shutdown();
        } catch (Exception e) {
            System.out.println("Could not start sever!");
        }
    }

    public static void setExit(boolean isExit) {
        Main.isExit = isExit;
    }

}
