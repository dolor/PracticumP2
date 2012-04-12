package eindopdracht.client.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import eindopdracht.util.PTLog;

public class ConnectionHandler implements Runnable {

	protected BufferedReader in;
	protected BufferedWriter out;
	Boolean stop;
	Network network;

	/**
	 * Create a new connection handler for the client
	 * 
	 * @param socket
	 *            the socket to listen to
	 * @param network
	 *            network class to report to
	 * @throws IOException
	 *             thrown if the socket could not be opened
	 */
	public ConnectionHandler(Socket socket, Network network) throws IOException {
		this.network = network;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		Thread listenThread = new Thread(this);
		listenThread.start();
	}

	@Override
	public void run() {
		this.listen();
	}

	/**
	 * sends a string through the socket
	 * 
	 * @param string
	 *            text to be sent
	 */
	public synchronized void sendString(String string) {
		try {
			out.write(string + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Keeps listening on the socket and passes the input to network. Should be
	 * run from a thread to prevent blocking.
	 */
	private void listen() {
		try {
			if (network == null)
				PTLog.log("ConnectionHandler", "NETWORK WAS NULL");
			else if (in == null)
				PTLog.log("ConnectionHandler", "IN WAS NULL");
			String next;
			in.ready();
			next = in.readLine();
			while (next != null) {
				// If null, the connection was terminated
				synchronized (this) {
					network.processNetworkInput(next);
				}
				next = in.readLine();
			}
		} catch (IOException e) {
			network.disconnected();
			PTLog.log("ConnectionHandler",
					"Error occured while reading inputstream");
		}
	}

	/**
	 * Can be called to stop listening
	 */
	public void stop() {
		this.stop = true;
	}
}
