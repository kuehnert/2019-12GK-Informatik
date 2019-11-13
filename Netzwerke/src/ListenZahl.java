import java.util.ArrayList;
import java.util.Scanner;

public class ListenZahl {
    public ListenZahl() {
        ArrayList<String> list = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        String s = "";

        while (true) {
            System.out.println("Was wollen sie tun? 'H' hinzufügen, 'L' löschen, 'A' Ausgeben, 'quit' beenden");
            s = scanner.nextLine();

            if (s.equals("H")) {
                System.out.println("Was wollen sie hinzufügen?");
                s = scanner.nextLine();
                list.add(s);
                System.out.println("'" + s + "' hinzugefügt.");
            } else if (s.equals("L")) {
                System.out.println("Welchen Part wollen sie löschen? Geben sie das Objekt ein");
                s = scanner.nextLine();
                list.remove(s);
                System.out.println("'" + s + "' gelöscht.");
            } else if (s.equals("A")) {
                System.out.println(list);
            } else {
                System.out.println("Ende");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new ListenZahl();
    }
}