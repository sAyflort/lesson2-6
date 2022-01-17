package ru.geekbrains.lesson6.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8189;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public Client() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDRESS ,SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        String strToServer;
        //Сервер -> клиент
        new Thread(() -> {
            String strToClient;
            try {
                while (true) {
                    strToClient = in.readUTF();
                    if (strToClient.equals("/end")) {
                        break;
                    }
                    System.out.println("Сервер: " + strToClient);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //Клиент -> сервер
        while (true) {
            strToServer = scanner.nextLine();
            if (strToServer.equals("/end")) {
                break;
            }
            out.writeUTF(strToServer);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
