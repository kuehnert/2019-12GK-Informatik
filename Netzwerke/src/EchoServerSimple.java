import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServerSimple {
    public EchoServerSimple(int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);

            while (true) {
                Socket client = server.accept();
                handleClient(client);
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

    private void handleClient(Socket client) {
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


    public static void main(String[] args) {
        new EchoServerSimple(1300); // 13 * 100
    }
}
