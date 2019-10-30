// Explorer öffnen mit WIN+E
// Oben in Adresszeile:
// "\\watson\groups\Stufe 2020\GK12 Informatik\Netzwerke"

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public EchoClient() {
        try {
            // 1. Socket öffnen
            Socket socket = new Socket("localhost", 7000);
            InputStream socketInputStream = socket.getInputStream();
            PrintWriter socketOutputStream = new PrintWriter(socket.getOutputStream());

            Scanner socketScanner = new Scanner(socketInputStream);
            Scanner tastaturScanner = new Scanner(System.in);
            System.out.println("Verbunden mit localhost:7000. Schreib etwas: ");

            // 1. Schleife: so lange, bis Benutzer beendet
            while (true) {
                // 2. Lies vom Benutzer eine Eingabe und schicke sie an den Server
                String eingabe = tastaturScanner.nextLine();
                socketOutputStream.println(eingabe);
                socketOutputStream.flush();

                // 3. Warte auf Antwort von Server und drucke sie auf den Bildschirm
                String answer = socketScanner.nextLine();
                System.out.println(answer);

                // 4. Wenn Benutzer "quit" schreibt, beende Schleife
                if ( eingabe.equalsIgnoreCase("quit") ) {
                    break;
                }
            }

            // 5. Socket und alle Ströme schließen
            socketInputStream.close();
            socketOutputStream.close();
            socket.close();
        } catch (Exception e) {
            // hat nicht geklappt
            System.out.println("Netzwerkfehler");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new EchoClient();
    }
}
