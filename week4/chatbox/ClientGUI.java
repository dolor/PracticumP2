package week4.chatbox;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame 
                       implements ActionListener, MessageUI {
    // NOG TOE TE VOEGEN

    /** Construeert een ClientGUI object. */
    public ClientGUI() {
        super("Client GUI");
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
    private void buildGUI() {
        // BODY NOG TOE TE VOEGEN
    }

    /** Afhandeling van een actie van het GUI. */
    public void actionPerformed(ActionEvent ev) {
        // BODY NOG TOE TE VOEGEN
    }

    /**
     * Probeert een socket-verbinding op te zetten met de Server.
     * Als alle parametervelden geldig zijn, dan wordt getracht een
     * Client-object te construeren die de daadwerkelijke 
     * socketverbinding met de Server maakt. Als dit gelukt is 
     * wordt een aparte thread (van Client) opgestart, die de 
     * berichten van de Server leest.
     * Daarna worden alle parametervelden en de "Connect" Button
     * niet selecteerbaar gemaakt.
     */
    public void connect() {
        // BODY NOG TOE TE VOEGEN
        addMessage("Connected to server...");
    }

    /** Voegt een bericht toe aan de TextArea op het frame. */
    public void addMessage(String msg) {
        // BODY NOG TOE TE VOEGEN
    }

    /** Start een ClientGUI applicatie op. */
    public static void main(String args[]) {
        ClientGUI gui = new ClientGUI();
    }

} // end of class ClientGUI
