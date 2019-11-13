import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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

public class IBMChatServerTCP {
    private ServerSocket ss;
    private ArrayList<PrintWriter> outputStreams = new ArrayList<PrintWriter>();

    public IBMChatServerTCP(int port) throws IOException {
        listen(port);
    }

    private void listen(int port) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Listening on " + ss);

        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection from " + s);
            PrintWriter dout = new PrintWriter(s.getOutputStream());
            outputStreams.add(dout);

            new ServerThreadTCP(this, s);
        }
    }

    void sendToAll(String message) {
        synchronized (outputStreams) {
            for (PrintWriter dout: outputStreams) {
                dout.println(message);
                dout.flush();
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

    static public void main(String args[]) throws Exception {
        int port = 3000;
        new IBMChatServerTCP(port);
    }
}

class ServerThreadTCP extends Thread {
    private IBMChatServerTCP server;
    private Socket socket;

    public ServerThreadTCP(IBMChatServerTCP server, Socket socket) {
        this.server = server;
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            Scanner din = new Scanner(socket.getInputStream());

            while (true) {
                String message = din.nextLine();
                System.out.println("Sending " + message);
                server.sendToAll(message);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            server.removeConnection(socket);
        }
    }
}
