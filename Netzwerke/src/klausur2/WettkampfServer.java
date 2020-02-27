package klausur2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class WettkampfServer {
    private ServerSocket serverSocket;
    private ArrayList<WettkampfThread> users = new ArrayList<WettkampfThread>();

    public WettkampfServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("klausur2.WettkampfServer Listening on " + serverSocket + " auf Port " + port);
            listen();
        } catch (IOException e) {
            System.out.println("Konnte klausur2.WettkampfServer auf Port " + port +  " nicht starten.");
            System.exit(1);
        }
    }

    private void listen() {
        while (true) {
            Socket s = null;

            try {
                s = serverSocket.accept();
                System.out.println("Verbindung von " + s);
                WettkampfThread user = new WettkampfThread(this, s);
                users.add(user);
                user.start();
            } catch (IOException e) {
                System.out.println("Verbindung zu Client " + s + " konnte nicht aufgebaut werden.");
            }
        }
    }

    void sendToAll(WettkampfThread sender, String message) {
        for (WettkampfThread user: users) {
            if (user != sender) {
                user.send(sender.getUsername() + ": " + message);
            }
        }
    }

    void removeConnection(Socket s) {
        /*
        synchronized (outputStreams) {
            System.out.println("Removing connection to " + s);
            outputStreams.remove(s);

            try {
                s.close();
            } catch (IOException ie) {
                System.out.println("Error closing " + s);
                ie.printStackTrace();
            }
        }
         */
    }

    static public void main(String args[]) {
        int port = 3000;
        new WettkampfServer(port);
    }
}

class WettkampfThread extends Thread {
    private WettkampfServer server;
    private Socket socket;
    private Scanner din;
    private PrintWriter dout;
    private String username;

    public WettkampfThread(WettkampfServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.username = "Anonym" + (int) (Math.random() * 10000);

        try {
            din = new Scanner(socket.getInputStream());
            dout = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {};
    }

    public void send(String message) {
        dout.println(message);
        dout.flush();
    }

    public void run() {
        try {
            while (true) {
                // Hier ist der Ort für Ihre Logik
                String message = din.nextLine();
                String[] command = message.split(",");

                if (command[0].equals("S")) {

                }
            }
        } finally {
            server.removeConnection(socket);
        }
    }

    public String getUsername() {
        return username;
    }
}
