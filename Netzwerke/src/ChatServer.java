import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// TODO:
//  1. Socket und PrintWriter in einer Hilfsklasse verwalten (Konrad)
//  1a. Abmelden reparieren
//  1b. Ende-Befehl
//  2. SendToAll nicht an sich selbst (Jireh)
//  3. SendTo mit Empfänger (Florian, Michael)
//  5. Farben mit Terminalemulation (Veit) (#### fdsafsdfsf ####)
//  4. Usernames (Florian)
// 15. Meldung mit Uhrzeit
//  6. Beitreten und Abmelden wird an alle gesendet
//  8. Liste Mitglieder auf (Tobias)
//  9. MOTD Gruß (Michael)
// 12. Hilfe-Befehl (Tobias)
// 13. Status-Meldungen
//  7. Zitieren (Jan)
// 10. Sprach-Filter (Jan)
// 14. Timeout für unflätige Nutzer
// 11. Gruppen (Konrad)
// 16. Protokoll als TXT-Datei

public class ChatServer {
    private ServerSocket serverSocket;
    private ArrayList<ClientThread> users = new ArrayList<ClientThread>();

    public ChatServer(int port) {
        // TODO: Behandeln Sie das Problem, dass die Socket nicht geöffnet werden kann
        serverSocket = new ServerSocket(port);
        System.out.println("Listening on " + serverSocket + " auf Port " + port);

        listen();
    }

    private void listen() {
        while (true) {
            // TODO: Kümmern Sie sich um die Exception
            Socket s = serverSocket.accept();
            System.out.println("Connection from " + s);
            ClientThread user = new ClientThread(this, s);
            users.add(user);
            user.start();
        }
    }

    void sendToAll(ClientThread sender, String message) {
        for (ClientThread user: users) {
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
        new ChatServer(port);
    }
}

class ClientThread extends Thread {
    private ChatServer server;
    private Socket socket;
    private Scanner din;
    private PrintWriter dout;
    private String username;

    public ClientThread(ChatServer server, Socket socket) {
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

                if (message.matches("^\\/.+")) {
                    // Wir haben einen Befehl, d.h. message beginnt mit einem /
                } else {
                    // Wir haben eine normale Botschaft bekommen, die an alle (anderen)
                    // geschickt werden soll.
                    System.out.println("Sending " + message);
                    server.sendToAll(this, message);
                    send("Nachricht verschickt");
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
