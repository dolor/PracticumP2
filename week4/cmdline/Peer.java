package week4.cmdline;

import java.net.*;
import java.security.InvalidParameterException;
import java.io.*;

/**
 * P2 prac wk4. <br>
 * Peer. Een klasse voor een Client en Server die over een Socket 
 * verbinding met elkaar kunnen praten via Terminal vensters.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Peer implements Runnable {
    
    protected String            name;
    protected Socket            sock;
    protected BufferedReader    in;
    protected BufferedWriter    out;

    /**
     * Constructor. Construeert een Peer-object met de gegeven naam
     * en de meegegeven sock. Initialiseert de input- en outputstreams.
     * @require (name != null) && (sock != null)
     * @param   naam naam van dit Peer-proces
     * @param   sock Socket van dit Peer-proces
     */
    public Peer(String name, Socket sock) throws IOException 
    {
    	if (name == null || sock == null)
    		throw new InvalidParameterException("Requires name and sock to be non-null");
    	
        this.name = name;
        this.sock = sock;
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }

    /**
     * Leest Strings uit de stream van de socket-verbinding en 
     * schrijft deze karakters naar de standard output. 
     */
    public void run() {
        try {
        	String next;
        	in.ready();
        	next = in.readLine();
        	while (next != null) {
        		//String input = in.readLine();
        		if (next.contains("EXIT")) {
        			System.out.println("Connection terminated by other side");
        			shutDown();
        		}
        		System.out.println("\r" + next);
        		System.out.print(">");
        		in.ready();
        		next = in.readLine();
        	}
        } catch (IOException e) {
			System.out.println("Error occured while reading inputstream");
		} catch (NullPointerException e) {
			//TODO Find a more elegant solution
			System.exit(0);
		}
    }

    
    /**
     * Leest Strings van de Terminal en stuurt deze Strings over de
     * socket-verbinding naar het Peer proces. Als Peer.EXIT wordt
     * ingetypt eindigt te methode.
     */
    public void handleTerminalInput() {
    	boolean stop = false;
    	String userInput;
        while (!stop) {
        	try {
        		System.out.print(">");
        		userInput = readString("");
				/*while ((userInput = readString("")).equals("")) {
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}*/
        		//System.out.println("\r[" + getName() + "]" + userInput + "\n");
				out.write("[" + getName() + "]" + userInput + "\n");
				out.flush();
				if (userInput.equals("EXIT")) {
					shutDown();
				}
				//System.out.println("[" + getName() + "]" + userInput);
			} catch (IOException e) {
				System.out.println("Error occured while trying to read the user-entered text");
				e.printStackTrace();
			}
        }
    }

    /**
     * Verbreekt de verbinding. Beide streams worden afgesloten en
     * ook de Socket zelf wordt afgesloten.
     */
    public void shutDown() {
    	System.out.println("[System] Connection terminated");
    	try {
			out.close();
	    	in.close();
	    	sock.close();
	    	System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /** Levert de naam van dit Peer-object. */
    public String getName() {
        return name;
    }
    
    /** Leest een regel tekst van standaardinvoer. */
    static public String readString(String tekst) {
        System.out.print(tekst);
        String antw = null;
        try {
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(System.in));            
            antw = in.readLine();
        } catch (IOException e) {
        }

        return (antw == null) ? "" : antw;
    }
}
