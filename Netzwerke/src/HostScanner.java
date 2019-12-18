import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HostScanner {
    public HostScanner() {
        scanAllHosts();
    }

    private boolean scanHost(String ip) {
        try {
            System.out.print("Checke Host " + ip + " ...");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, 80), 1*1000);
            socket.close();
            System.out.println("✔");
            return true;
        } catch (UnknownHostException uhe) {
            System.out.println("❌");
            return false;
        } catch (IOException ioe) {
            System.out.println("❌");
            return false;
        }
    }

    public void scanAllHosts() {
        System.out.println("Starte Scan...");
        String subnetprefix = "10.2.0.";

        for (int i = 1; i < 20; i++) {
            String ip = subnetprefix + i;
            scanHost(ip);
        }

        System.out.println("Scan beendet.");
    }

    public static void main(String[] args) {
        new HostScanner();
    }
}
