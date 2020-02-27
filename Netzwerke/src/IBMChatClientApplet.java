import java.applet.*;
import java.awt.*;

public class IBMChatClientApplet extends Applet {
    public void init() {
        String host = getParameter("host");
        int port = Integer.parseInt(getParameter("port"));
        setLayout(new BorderLayout());
        add("Center", new IBMChatClient(host, port));
    }
}
