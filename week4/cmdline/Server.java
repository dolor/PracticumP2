package week4.cmdline;

import java.net.*;
import java.io.*;

/**
 * P2 prac wk4. <br>
 * Server. Simpele Server-klasse die een Socket-connectie opzet met een 
 * Client, waarna beide objecten via de Terminal met elkaar kunnen praten.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server {
    private static final String USAGE =
        "usage: java week4.cmdline.Server <name> <port>" ;

    /** Start een Server-applicatie op. */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(USAGE);
            System.exit(0);
        }

        String name = args[0];
        int   port  = 0;

        try {
        	int p = Integer.parseInt(args[1]);
        	if (p < 0 || p > 65535) {
				System.out.println(args[1] + ": Not a valid port number");
        		System.out.println(USAGE);
        		System.exit(0);        		
        	}
        	port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
    		System.out.println(USAGE);
    		System.exit(0);        	
        }
        
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
	            Peer client = new Peer(name, sock);
	            Thread streamInputHandler = new Thread(client);
	            streamInputHandler.start();
	            client.handleTerminalInput();
	            client.shutDown();
			} catch (IOException e) {
				System.out.println("Connection on port " + port + " failed: " + e.getMessage());
				e.printStackTrace();
			}
        }
        // Nog toe te voegen: controle en parsen van de 
        // .. argumentlijst args. Daarna hebben name en port 
        // .. een gedefinieerde waarde. 
        // .. Daarna wachten tot een Client zich aanmeldt.
        // .. Vervolgens Peer-object creeeren en zorgen dat
        // .. de input op de Terminal goed wordt afgehandeld.
      }

} // end of class Server
