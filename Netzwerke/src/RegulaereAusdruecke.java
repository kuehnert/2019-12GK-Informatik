import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// /[0-9a-z]/
// /a{3}/ => aaa
// /a{1,4}/ => a, aa, aaa, aaaa
// /https?:\/\//  => http://, https://
// /(dog|cat)/
// /d*/ => kein oder beliebig viele 'd'
// /d+/ => mind. 1 'd'
// /^hahn/ => 'hahn' kommt direkt am Anfang vor
// /henne$/ => 'henne' kommt am Ende "superhenne"
// /[^h]/ => alles außer 'h'
//
//
// /^\/pm ([A-Za-z]+) (.+)$/ => $1: name $2: message
//
// Kleine Aufgaben:
// 1. ein Passwort mit mind. 14 Zeichen
// 2. einfacher Vorname, zum Beispiel Max
// 3. ein Autokennzeichen ist "OP PA-5"
// 4. Vorname mit eventuellem Doppelnamen: Hans-Peter
// 5. ein Text endet mit einer zweistelligen Zahl
// 6. eine E-Mail-Adresse
// 7. eine IP-Adresse
// 8. eine Mac-Adresse

public class RegulaereAusdruecke {
    // Reguläre Ausdrücke
    // * mächtig
    // * kurz
    // * existieren in allen Sprachen

    public static void regExSimple() {
        // matches gibt nur ein Boolean zurück
        // ob ein String komplett der RegEx entspricht
        String text = "In dem Haus wohnt die Maus und kommt aus dem Haus heraus.";
        System.out.println(text.matches("^[A-Z].*"));
    }

    public static void regExMatches() {
        String text = "In dem Haus wohnt die Maus und kommt aus dem Haus heraus.";
        Pattern p = Pattern.compile("\\w+s");
        Matcher m = p.matcher(text);

        // Find all matches
        while (m.find()) {
            // Get the matching string
            String match = m.group();
            System.out.println("Match: " + match);
        }
    }

    public static void regExReplace() {
        String text = "In dem Haus wohnt die Maus und kommt aus dem Haus heraus.";
        System.out.println(text.replaceAll("aus", "AUAS"));
    }

    public static void regExGroups() {
        String text = "/pm bob Hey Bob, altes Haus, wie geht's?";
        Pattern p = Pattern.compile("^/(pm) ([a-z]+) (.+)$");
        Matcher m = p.matcher(text);
        if (m.find()) {
            System.out.println("Befehl : " + m.group(1));
            System.out.println("User   : " + m.group(2));
            System.out.println("Message: " + m.group(3));
        } else {
            System.out.println("Not a /pm command");
        }

    }


    public static void stringMethoden() {
        String text = "In dem Haus wohnt die Maus und kommt aus dem Haus heraus.";
        String suchText = "aus";

        text.equals(suchText);
        text.equalsIgnoreCase(suchText);

        text.contains(suchText);
    }

    public static void stringSplit() {
        String s = "S,65,5a,Brecht,Bertolt";
        String[] bestandteile = s.split(",");
        System.out.println(Arrays.toString(bestandteile));
    }

    public static void main(String[] args) {
        // regExSimple();
        // regExMatches();
        // regExReplace();
        // regExGroups();
        stringSplit();
    }
}
