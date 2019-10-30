import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CoolClient2 {
    Socket socket;
    Scanner sockIn;
    PrintWriter sockOut;
    Scanner keyIn;
    private String text, message, expected, returned;

    private String menu =
            "Folgende Befehle gibt es:\n" +
                    "1. Text großschreiben lassen\n" +
                    "2. Text wiederholt ausgeben\n" +
                    "3. Quersumme einer Zahl berechnen\n" +
                    "Irgendwas anderes zum Beenden\n" +
                    "Möchtest Du 1, 2 oder 3? ";

    private void option1() {
        System.out.print("Text: ");
        message = text = keyIn.nextLine();
        expected = text.toUpperCase();
    }

    private void option2() {
        System.out.print("Text: ");
        text = keyIn.nextLine();
        System.out.print("Wie oft? ");
        String countString = keyIn.nextLine();
        int count = Integer.parseInt(countString);
        message = text + "|" + count;
        expected = "";
        for (int i = 0; i < count; i++) {
            expected += text;
        }
    }

    private void option3() {
        System.out.print("Zahl: ");
        text = message = keyIn.nextLine();
        int crossSum = 0;
        for (int i = 0; i < text.length(); i++) {
            crossSum += Integer.parseInt(text.substring(i, i+1));
        }
        expected = "" + crossSum;
    }

    public CoolClient2(String host, int port) {
        try {
            socket = new Socket(host, port);
            sockIn = new Scanner(socket.getInputStream());
            sockOut = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Fehler bei Verbindung.");
            e.printStackTrace();
            System.exit(1);
        }

        keyIn = new Scanner(System.in);
        System.out.println("Connected to " + host + " on " + port);
        System.out.println(sockIn.nextLine());

        while (true) {
            System.out.print(menu);
            String answer = keyIn.nextLine();

            switch (answer) {
                case "1":
                    option1();
                    break;
                case "2":
                    option2();
                    break;
                case "3":
                    option3();
                    break;
                default:
                    message = "quit";
                    break;
            }

            if (message.equals("quit")) {
                break;
            }

            try {
                sockOut.println(message);
                sockOut.flush();
                returned = sockIn.nextLine();
            } catch (Exception e) {
                System.out.println("Schwerer Fehler. Beende Programm.");
                e.printStackTrace();
                System.exit(1);
            }

            System.out.print("Erwartet: " + expected);
            System.out.print(expected.equals(returned) ? " == " : " != ");
            System.out.println("Empfangen: " + returned);
        }

        System.out.println("Beende Programm");
        try {
            sockIn.close();
            sockOut.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Fehler beim Beenden der Verbindung.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new CoolClient2("localhost", 3000);
    }
}
