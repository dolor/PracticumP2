package eindopdracht.client.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

	private Socket socket;
	protected BufferedReader in;
    protected BufferedWriter out;
	Boolean stop;
	Network network;
	
	public ConnectionHandler(Socket socket, Network network) throws IOException{
		this.socket = socket;
		this.network = network;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	    Thread listenThread = new Thread(this);
	    listenThread.start();
	}
	
	@Override
	public void run() {
		this.listen();
	}
	
	/**
	 * sends a string through the socket
	 * @param string text to be sent
	 */
	public void sendString(String string) {
		System.out.println("Sending string: " + string);
		try {
			out.write(string + "\n");
			out.flush();
			System.out.println("Sent!");
		} catch (IOException e) {
			// TODO Check how an error for sending should be handled; Locally or throwing to the caller.
			e.printStackTrace();
		}
	}
	
	/**
	 * Keeps listening on the socket and passes the input to network.
	 * Should be run from a thread to prevent blocking.
	 */
	private void listen() {
		try {
        	String next;
        	in.ready();
        	next = in.readLine();
        	while (next != null) {
        		//If null, the connection was terminated
        		network.processNetworkInput(next);
        		next = in.readLine();
        	}
        } catch (IOException e) {
			System.out.println("Error occured while reading inputstream");
		} catch (NullPointerException e) {
			//TODO Check if this is really necessary
			System.exit(0);
		}
	}
	
	/*
	 * Can be called to stop listening
	 */
	public void stop() {
		this.stop = true;
	}
}
