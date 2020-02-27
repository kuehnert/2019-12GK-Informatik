import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class PortScannerThreaded {
    private int currentlyTestedPorts = 0;
    private int threadCount = 10;
    private ArrayList<Integer> openPorts = new ArrayList<>();
    private ArrayList<Integer> portsToTest = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();

    public PortScannerThreaded(String ip) {
        for (int i = 1; i <= 1024; i++) {
            portsToTest.add(i);
        }

        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(ip));
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("\nFertig!");
            String out = ip + ": Ports ";
            for (int port : openPorts) {
                out += port + ", ";
            }
            System.out.println(out.substring(0, out.length() - 2));
        } catch (InterruptedException e) {
            System.out.println("Unbekannter Fehler.");
        }
    }

    public static void main(String[] args) {
        new PortScannerThreaded(args[0]);

    }

    class Thread extends java.lang.Thread {
        String ip;

        private Thread(String ip) {
            this.ip = ip;
            start();
        }

        public void run() {
            while (!portsToTest.isEmpty()) {
                currentlyTestedPorts += 1;
                int port = portsToTest.remove(0);

                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 300);
                    System.out.println("Port " + port + " ist offen!");
                    openPorts.add(port);
                    socket.close();
                } catch (Exception e) {
                    System.out.print(".");
                }
                currentlyTestedPorts -= 1;
            }

            System.out.println("Thread fertig.");
        }
    }
}
