package com.example.frontend.TCP;

import com.example.frontend.models.entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private static final ClientSocket INSTANCE= new ClientSocket();

    private User user;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private ClientSocket() {
        try {
            socket=new Socket("localhost",8080);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static ClientSocket getInstance(){
        return INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public  void setSocket(Socket socket) {
        ClientSocket.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}
