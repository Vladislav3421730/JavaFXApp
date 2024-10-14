package org.example;

import org.example.TCP.ClientThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int PORT = 8080;
    private static ServerSocket serverSocket;
    private static List<Socket> currentSockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен на порту " + PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключен: " + socket.getInetAddress());

            currentSockets.add(socket);
            ClientThread clientThread = new ClientThread(socket);
            Thread thread = new Thread(clientThread);
            thread.start();
        }
    }
}
