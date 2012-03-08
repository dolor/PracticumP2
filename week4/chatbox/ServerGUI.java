package week4.chatbox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

/**
 * P2 prac wk4.
 * ServerGui. Een GUI voor de Server.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class ServerGUI extends JFrame 
                       implements ActionListener, MessageUI {

    private JButton     bConnect;
    private JTextField  tfPort;
    private JTextArea   taMessages;
    private Server      server;

    /** Construeert een ServerGUI object. */
    public ServerGUI() {
        super("ServerGUI");

        buildGUI();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
    }

    /** Bouwt de daadwerkelijke GUI. */
    public void buildGUI() {
        setSize(600,400);

        // Panel p1 - Listen

        JPanel p1 = new JPanel(new FlowLayout());
        JPanel pp = new JPanel(new GridLayout(2,2));

        JLabel lbAddress = new JLabel("Address: ");
        JTextField tfAddress = new JTextField(getHostAddress(), 12);
        tfAddress.setEditable(false);

        JLabel lbPort = new JLabel("Port:");
        tfPort        = new JTextField("2727", 5);

        pp.add(lbAddress);
        pp.add(tfAddress);
        pp.add(lbPort);
        pp.add(tfPort);

        bConnect = new JButton("Start Listening");
        bConnect.addActionListener(this);

        p1.add(pp, BorderLayout.WEST);
        p1.add(bConnect, BorderLayout.EAST);

        // Panel p2 - Messages

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        JLabel lbMessages = new JLabel("Messages:");
        taMessages = new JTextArea("", 15, 50);
        taMessages.setEditable(false);
        p2.add(lbMessages);
        p2.add(taMessages, BorderLayout.SOUTH);

        Container cc = getContentPane();
        cc.setLayout(new FlowLayout());
        cc.add(p1); cc.add(p2);
    }

    /** Levert het Internetadres van deze computer op. */
    private String getHostAddress() {
        try {
            InetAddress iaddr = InetAddress.getLocalHost();
            return iaddr.getHostAddress();
        } catch (UnknownHostException e) {
            return "?unknown?";
        }
    }

    /**
     * Als de "Start Listening" knop wordt ingedrukt wordt de 
     * methode startListening() aangeroepen.
     */
    public void actionPerformed(ActionEvent ev) {
        Object src = ev.getSource();
        if (src == bConnect) {
            startListening();
        }
    }

    /**
     * Als de port-veld van de GUI geldig is, zal deze methode
     * een Server-object construeren, die daadwerkelijk op de 
     * gespecificeerde port zal gaan wachten op Clients.
     * Dit gebeurt in een aparte thread van de Server.
     * Het port TextField en de "Start Listening" knop worden
     * niet selecteerbaar gemaakt.
     */
    private void startListening() {
        int port = 0;
        int max  = 0;

        try {
            port = Integer.parseInt(tfPort.getText());
        } catch (NumberFormatException e) {
            addMessage("ERROR: geen geldig portnummer!");
            return;
        }

        tfPort.setEditable(false);
        bConnect.setEnabled(false);

        server = new Server(port, this);
        server.start();

        addMessage("Started listening on port " + port + "...");
    }

    /** Voegt een bericht toe aan de TextArea op het frame. */
    public void addMessage(String msg) {
        taMessages.append(msg + "\n");
    }

    /** Start een ServerGUI applicatie op. */
    public static void main(String[] args) {
        ServerGUI gui = new ServerGUI();
    }

}
