import java.util.ArrayList;

/*
 * Aufgabe
 *
 * Schreibe ein einfaches Programm für eine To-Do-Liste
 * Der Benutzer soll können:
 *  1. Die Liste ausgeben
 *  2. Aufträge in die Liste aufnehmen
 *  3. Aufträge löschen
 */

public class ListenDemo {
    public static void main(String[] args) {
        ArrayList meineListe = new ArrayList<String>();

        System.out.println( meineListe.isEmpty()  );
        System.out.println(meineListe.size());

        meineListe.add("Felix");
        meineListe.add("RALF");
        meineListe.add("Karin");

        System.out.println( meineListe.isEmpty()  );
        System.out.println(meineListe.size());
        System.out.println(meineListe);

        System.out.println(meineListe.get(0));
        System.out.println(meineListe.get(1));

        meineListe.remove(0);
        System.out.println(meineListe);

        meineListe.remove("Ralf".toUpperCase());

        System.out.println( meineListe.isEmpty()  );
        System.out.println(meineListe.size());
        System.out.println(meineListe);

        Integer i = 10000000;
        System.out.println(i % 200 == 0);
    }
}
