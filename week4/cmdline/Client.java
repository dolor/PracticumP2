package week4.cmdline;

import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * P2 prac wk4. <br>
 * Client. Simpele Client-klasse die een Socket-connectie opzet met een 
 * Server, waarna beide objecten via de Terminal met elkaar kunnen praten.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Client {
    private static final String USAGE =
        "usage: java week4.cmdline.Client <name> <address> <port>" ;

    /** Start een Client-applicatie op. */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(USAGE);
            System.exit(0);
        }
    
        String      name = args[0];
        InetAddress addr = null;
        int         port = 0;
        Socket      sock = null;

        if (args[1].toLowerCase().equals("localhost")) {
        	try {
				addr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {e.printStackTrace();}
        } else {
        	String[] numStrings = args[1].split("\\.");
        	byte[] addressBytes = new byte[4];
        	if (numStrings.length == 4) {
        		for (int i = 0; i < 4; i++) {
        			if (Integer.parseInt(numStrings[i]) < 0 || Integer.parseInt(numStrings[i]) > 255) {
        				System.out.println(args[1] + ": Not a valid IP address");
                		System.out.println(USAGE);
                		System.exit(0);
        			} else {
        				addressBytes[i] = (byte) Integer.parseInt(numStrings[i]);
        			}
        		}
        	} else {
        		System.out.println(USAGE);
        		System.exit(0);
        	}
        	System.out.println("Valid address!");
        	try {
				addr = InetAddress.getByAddress(addressBytes);
			} catch (UnknownHostException e) {e.printStackTrace();}
        	//TODO (?): Add support for ipv6
        }
        
        try {
        	int p = Integer.parseInt(args[2]);
        	if (p < 0 || p > 65535) {
				System.out.println(args[1] + ": Not a valid port number");
        		System.out.println(USAGE);
        		System.exit(0);        		
        	}
        	port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
    		System.out.println(USAGE);
    		System.exit(0);        	
        }

        try {
        	System.out.println("Trying to make a socket at " + addr + "[" + port + "]");
            sock = new Socket(addr, port);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage() + " on port " + port);
            System.exit(0);
        }
 
        // create Peer object and start the two-way communication
        try {
            Peer client = new Peer(name, sock);
            Thread streamInputHandler = new Thread(client);
            streamInputHandler.start();
            client.handleTerminalInput();
            client.shutDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} // end of class Client

