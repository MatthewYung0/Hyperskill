package server;

import common.HTTP_CODES;
import common.HTTP_COMMANDS;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;

    public static void main(String[] args) {

        createDirectory(path);

        String address = "127.0.0.1";
        int port = 23456;

        boolean endServer = false;

        try (ServerSocket server = new ServerSocket(port, 100, InetAddress.getByName(address))) {
            System.out.println("\nServer started!");
            do {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                String message = input.readUTF();
                if (message.equals("exit")) {
                    endServer = true;
                    closeInputAndOutput(input, output);
                    socket.close();
                } else {
                    String[] words = message.split(" ", 3);
                    boolean isSuccessfulAction = false;
                    HTTP_COMMANDS c = HTTP_COMMANDS.valueOf(words[0]);
                    switch (c) {
                        case PUT:
                            isSuccessfulAction = createFile(path, words[1], words[2]);
                            break;
                        case GET:
                            isSuccessfulAction = getFile(path, words[1]);
                            break;
                        case DELETE:
                            isSuccessfulAction = deleteFile(path, words[1]);
                    }
                    output.writeUTF(String.valueOf(returnHTTPCode(HTTP_COMMANDS.valueOf(words[0]), isSuccessfulAction)));
                }
                closeInputAndOutput(input, output);
            } while (!endServer);
        } catch (IOException e) {
            System.out.println("Could not start sever!");
        }
    }

    public static void closeInputAndOutput(DataInputStream input, DataOutputStream output) throws IOException {
        input.close();
        output.close();
    }

    public static boolean deleteFile(final String path, final String fileName) {
        File file = new File(path, fileName);
        return file.delete();
    }

    public static boolean getFile(final String path, final String fileName) {
        File file = new File(path, fileName);
        return file.exists();
    }

    public static boolean createFile(final String path, final String fileName, final String fileContent) {
        try {
            if (new File(path, fileName).exists()) {
                return false;
            } else {
                File newFile = new File(path + fileName);
                FileWriter writer = new FileWriter(newFile);
                writer.write(fileContent);
                writer.close();
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static int returnHTTPCode(final HTTP_COMMANDS request, final boolean actionSuccessful) {
        if (actionSuccessful) {
            return HTTP_CODES.SUCCESS.getCode();
        } else if (request.equals(HTTP_COMMANDS.PUT)) {
            return HTTP_CODES.FAIL_PUT.getCode();
        } else if (request.equals(HTTP_COMMANDS.GET)) {
            return HTTP_CODES.FAIL_GET.getCode();
        } else {
            return HTTP_CODES.FAIL_DELETE.getCode();
        }
    }

    public static void createDirectory(final String path) {
        File fileDirectory = new File(path);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
    }
}