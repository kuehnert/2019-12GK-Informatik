import java.io.*;
import java.net.*;
import java.util.*;

public class IBMChatServer {
    // The ServerSocket we'll use for accepting new connections
    private ServerSocket ss;

    // A mapping from sockets to DataOutputStreams. This will
    // help us avoid having to create a DataOutputStream each time
    // we want to write to a stream.
    private Hashtable outputStreams = new Hashtable();

    // Constructor and while-accept loop all in one.
    public IBMChatServer(int port) throws IOException {
        // All we have to do is listen
        listen(port);
    }

    private void listen(int port) throws IOException {
        // Create the ServerSocket
        ss = new ServerSocket(port);

        // Tell the world we're ready to go
        System.out.println("Listening on " + ss);

        // Keep accepting connections forever
        while (true) {
            // Grab the next incoming connection
            Socket s = ss.accept();

            // Tell the world we've got it
            System.out.println("Connection from " + s);

            // Create a DataOutputStream for writing data to the
            // other side
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // Save this stream so we don't need to make it again
            outputStreams.put(s, dout);

            // Create a new thread for this connection, and then forget
            // about it
            new ServerThread(this, s);
        }
    }

    // Get an enumeration of all the OutputStreams, one for each client
    // connected to us
    Enumeration getOutputStreams() {
        return outputStreams.elements();
    }

    // Send a message to all clients (utility routine)
    void sendToAll(String message) {
        // We synchronize on this because another thread might be
        // calling removeConnection() and this would screw us up
        // as we tried to walk through the list
        synchronized (outputStreams) {
            // For each client ...
            for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {
                // ... get the output stream ...
                DataOutputStream dout = (DataOutputStream) e.nextElement();
                // ... and send the message
                try {
                    dout.writeUTF(message);
                } catch (IOException ie) {
                    System.out.println(ie);
                }
            }
        }
    }

    // Remove a socket, and it's corresponding output stream, from our
    // list. This is usually called by a connection thread that has
    // discovered that the connection to the client is dead.
    void removeConnection(Socket s) {
        // Synchronize so we don't mess up sendToAll() while it walks
        // down the list of all output streams
        synchronized (outputStreams) {
            // Tell the world
            System.out.println("Removing connection to " + s);

            // Remove it from our hashtable/list
            outputStreams.remove(s);

            // Make sure it's closed
            try {
                s.close();
            } catch (IOException ie) {
                System.out.println("Error closing " + s);
                ie.printStackTrace();
            }
        }
    }

    // Main routine
    // Usage: java Server <port>
    static public void main(String args[]) throws Exception {
        // Get the port # from the command line
        int port = Integer.parseInt(args[0]);

        // Create a Server object, which will automatically begin
        // accepting connections.
        new IBMChatServer(port);
    }
}

class ServerThread extends Thread {
    // The Server that spawned us
    private IBMChatServer server;
    // The Socket connected to our client
    private Socket socket;

    // Constructor.
    public ServerThread(IBMChatServer server, Socket socket) {
        // Save the parameters
        this.server = server;
        this.socket = socket;

        // Start up the thread
        start();
    }

    // This runs in a separate thread when start() is called in the
    // constructor.
    public void run() {
        try {
            // Create a DataInputStream for communication; the client
            // is using a DataOutputStream to write to us
            DataInputStream din = new DataInputStream(socket.getInputStream());

            // Over and over, forever ...
            while (true) {
                // ... read the next message ...
                String message = din.readUTF();

                // ... tell the world ...
                System.out.println("Sending " + message);

                // ... and have the server send it to all clients
                server.sendToAll(message);
            }
        } catch (EOFException ie) {
            // This doesn't need an error message
        } catch (IOException ie) {
            // This does; tell the world!
            ie.printStackTrace();
        } finally {
            // The connection is closed for one reason or another,
            // so have the server dealing with it
            server.removeConnection(socket);
        }
    }
}
