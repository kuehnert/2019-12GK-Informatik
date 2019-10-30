import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public EchoServer(int port) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("Echo-Server gestartet auf Port " + port);

            while (true) {
                Socket client = server.accept();
                new EchoServerClient(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new EchoServer(1300); // 13 * 100
    }
}

class EchoServerClient extends Thread {
    private Socket client;

    public EchoServerClient(Socket client) {
        this.client = client;
    }

    public void run() {
        boolean running = true;
        System.out.println("Neue Verbindung zu Client");

        try {
            Scanner clientScanner = new Scanner(client.getInputStream());
            PrintWriter clientOut = new PrintWriter(client.getOutputStream());

            while (running) {
                // 1. Warte & empfange Botschaft vom Client
                String in = clientScanner.nextLine();

                // 2. Schicke die Botschaft unverändert wieder zurück
                clientOut.println(in);
                clientOut.flush();

                // 3. Eventuell: Wenn Client "quit" beende die Verbindung
                if (in.equals("quit")) {
                    running = false;
                }
            }

            // Verbindung zum Client trennen
            System.out.println("Trenne Verbindung zum Client");
            clientOut.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}