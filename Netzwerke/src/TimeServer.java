import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer {
    public TimeServer(int port) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);

            while (true) {
                Socket client = null;
                PrintWriter clientOut = null;

                try {
                    client = server.accept();
                    System.out.println("Neue Verbindung zu Client");
                    clientOut = new PrintWriter(client.getOutputStream());

                    // Date
                    Date date = new Date();
                    clientOut.println("Die aktuelle Uhrzeit ist " + date.toString());
                    clientOut.flush();
                } catch (IOException e) {
                    System.out.println("Ein-/Ausgabefehler mit Client");
                } finally {
                    // Verbindung zum Client trennen
                    System.out.println("Trenne Verbindung zum Client");
                    clientOut.close();

                    if (client != null) {
                        try {
                            client.close();
                        } catch (IOException e2) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Schwerer Fehler: Kann keinen Dienst auf Port " + port + " starten.");
            System.exit(1);
        } finally {
            try {
                server.close();
            } catch (Exception e2) {
            }
        }
    }

    public static void main(String[] args) {
        new TimeServer(1300); // 13 * 100
    }
}
