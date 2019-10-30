// Explorer öffnen mit WIN+E
// Oben in Adresszeile:
// \\watson\Groups

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TimeClient {
    public TimeClient(String host) {
        try {
            // 1. Socket öffnen
            Socket socket = new Socket(host, 13);
            InputStream socketInputStream = socket.getInputStream();
            Scanner scanner = new Scanner(socketInputStream);

            // 2. Anfrage an den Zeit-Server schicken (fällt weg)
            // 3. Antwort empfangen und ausgeben
            String antwort = scanner.nextLine();
            System.out.println(antwort);

            // 4. Socket und alle Ströme schließen
            socketInputStream.close();
            socket.close();
        } catch (Exception e) {
            // hat nicht geklappt
            System.out.println("Verbindung konnte nicht augebaut werden");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // Lies aus args den Servernamen aus,
        // übergib ihn an TimeClient

        String host = args[0];
        System.out.println("Frage an: " + host);
        new TimeClient(host);
    }
}
