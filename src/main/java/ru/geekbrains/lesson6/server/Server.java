package ru.geekbrains.lesson6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(8189);
        try {
            System.out.println("Сервер успешно запущен.");
            socket = serverSocket.accept();
            System.out.println("Клиент подключен.");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String strToServer;
            Scanner scanner = new Scanner(System.in);
            //сервер -> клиент
            new Thread(() -> {
                String strToClient;
                while (true) {
                    try {
                        strToClient = scanner.nextLine();
                        if (strToClient.equals("/end")) {
                            break;
                        }
                        out.writeUTF(strToClient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //клиент -> сервер
            while (true) {
                strToServer = in.readUTF();
                if (strToServer.equals("/end")) {
                    break;
                }
                System.out.println("Клиент: " + strToServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
            serverSocket.close();
        }
    }
}

