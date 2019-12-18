import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {
    private PortScanner() {
        scanAllHosts();
    }

    public static void main(String[] args) {
        new PortScanner();
    }

    private boolean scanHost(String ip) {
        try {
            System.out.print("Checke Port " + ip + " ...");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, 80), 500);
            socket.close();
            System.out.println("✔");
            return true;
        } catch (IOException e) {
            System.out.println("❌");
            return false;
        }
    }

    private void scanAllHosts() {
        System.out.println("Starte Scan...");
        String subnetprefix = "10.2.0.";

        for (int i = 1; i < 20; i++) {
            String ip = subnetprefix + i;
            scanHost(ip);
        }

        System.out.println("Scan beendet.");
    }
}
