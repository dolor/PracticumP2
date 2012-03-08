package week4.chatbox;

import java.io.*;
import java.net.*;
import java.util.*;

import week4.cmdline.Peer;

/**
 * P2 prac wk5. <br>
 * Server. Een Thread-klasse die luistert naar socketverbindingen op
 * gespecificeerde port. Voor elke socketverbinding met een Client wordt
 * een nieuwe ClientHandler thread opgestart.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server extends Thread {
    private int                        port;
    private MessageUI                  mui;
    private Collection<ClientHandler>  threads;

    /** Construeert een nieuw Server-object. */
    public Server(int port, MessageUI mui) {
        // BODY NOG TOE TE VOEGEN
    }

    /**
     * Luistert op de port van deze Server of er Clients zijn die
     * een verbinding willen maken. Voor elke nieuwe socketverbinding
     * wordt een nieuw ClientHandler thread opgestart die de verdere
     * communicatie met de Client afhandelt.
     */
    public void run() {
    	ServerSocket socket = null;
    	
        try {
        	socket = new ServerSocket(port);
        } catch (IOException e) {
        	System.out.println("Could not listen on port " + port + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
        
        System.out.println("Port opened, awaiting connection...");
        
        boolean tryConnection = true;
        while (tryConnection) {
        	try {
				Socket sock = socket.accept();
	            ClientHandler handler = new ClientHandler(this, sock);
	            handler.start();
			} catch (IOException e) {
				System.out.println("Connection on port " + port + " failed: " + e.getMessage());
				e.printStackTrace();
			}
        }
    }

    /**
     * Stuurt een bericht via de collectie van aangesloten ClientHandlers
     * naar alle aangesloten Clients.
     * @param msg bericht dat verstuurd wordt
     */
    public void broadcast(String msg) {
        // BODY NOG TOE TE VOEGEN
    }

    /**
     * Voegt een ClientHandler aan de collectie van ClientHandlers toe.
     * @param handler ClientHandler die wordt toegevoegd
     */
    public void addHandler(ClientHandler handler) {
        // BODY NOG TOE TE VOEGEN
    }

    /**
     * Verwijdert een ClientHandler uit de collectie van ClientHandlers.
     * @param handler ClientHandler die verwijderd wordt
     */
    public void removeHandler(ClientHandler handler) {
        // BODY NOG TOE TE VOEGEN
    }

} // end of class Server
