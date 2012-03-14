package week4.chatbox;

import java.net.*;
import java.security.InvalidParameterException;
import java.io.*;

import week4.cmdline.Peer;

/**
 * P2 prac wk4. <br>
 * Client. Een Thread-klasse voor het onderhouden van een 
 * Socket-verbinding met een Server. De Thread leest berichten uit
 * de socket en stuurt die door naar zijn MessageUI.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Client extends Thread {

    private String          clientName;
    private MessageUI       mui;
    private Socket          sock;
    private BufferedReader  in;
    private BufferedWriter  out;

    /**
     * Construeert een Client-object en probeert een socketverbinding
     * met een Server op te starten.
     */
    public Client(String name, InetAddress host, int port, MessageUI mui) 
                                                 throws IOException, InvalidParameterException {
        this.clientName = name;
        this.mui = mui;
        if (!isValidIP(host.getHostAddress()) || !isValidPort(port))
        	throw new InvalidParameterException("Invalid IP or port!");
        sock = new Socket(host, port);

    	in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		System.out.println("In is now defined");    
		
        Thread streamInputHandler = new Thread(this);
        streamInputHandler.start();
    }

    /**
     * Leest berichten uit de socketverbinding. Elk berichtje wordt
     * gestuurd naar de MessageUI van deze Client. Als er iets fout
     * gaat bij het lezen van berichten, sluit deze methode de
     * socketverbinding.
     */
    public void run() {
    	System.out.println("Client running!");
    	try {
			out.write(getClientName() + "\n");
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	System.out.println("Got this far");
    	/*while (true) {
    		try {
    			if (in.ready()) {
    				String next = in.readLine();
    				if (next.equals("[" + clientName + " has entered]"))
    					next = "[You entered the room]";
    				mui.addMessage(next);
    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}*/
    	try {
        	String receive;
        	
        	while ((receive = in.readLine()) != null) {
        		mui.addMessage(receive);
        	} 
        } catch (IOException e) {
        	this.shutdown();
        }
    }

    /** Stuurt een bericht over de socketverbinding naar de ClientHandler. */
    public void sendMessage(String msg) {
        try {
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /** Sluit de socketverbinding van deze client. */
    public void shutdown() {
        mui.addMessage("Closing socket connection...");
        try {
        	in.close();
        	out.close();
			sock.close();
		} catch (IOException e) {}
    }

    /** Levert de naam van de gebruiker van deze Client. */
    public String getClientName() {
        return clientName;
    }
    
    /**
     * Tests if the passed string is a valid IP or hostname
     * @param ip
     * @return
     */
    public boolean isValidIP(String ip) {
    	boolean certainlyFalse = false;
    	InetAddress addr = null;
    	if (ip.toLowerCase().equals("localhost")) {
        	try {
				addr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {return false;}
        } else {
        	String[] numStrings = ip.split("\\.");
        	byte[] addressBytes = new byte[4];
        	if (numStrings.length == 4) {
        		for (int i = 0; i < 4; i++) {
        			if (Integer.parseInt(numStrings[i]) < 0 || Integer.parseInt(numStrings[i]) > 255) {
                		return false;
        			} else {
        				addressBytes[i] = (byte) Integer.parseInt(numStrings[i]);
        			}
        		}
        	} else {
        		return false;
        	}
        	System.out.println("Valid address!");
        	if (!certainlyFalse) {
        		try {
    				addr = InetAddress.getByAddress(addressBytes);
    			} catch (UnknownHostException e) {return false;}
            	//TODO (?): Add support for ipv6
        	}
        }
    	if (addr != null)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Tests if the int is a valid port.
     * @param port
     * @return boolean whether or not the port is valid.
     */
    public boolean isValidPort(int port) {
    	try {
        	int p = port;
        	if (p < 0 || p > 65535) {
				System.out.println(port + ": Not a valid port number");
        		return false;    		
        	} else
        		return true;
        } catch (NumberFormatException e) {
    		return false;      	
        }
    }

} // end of class Client
