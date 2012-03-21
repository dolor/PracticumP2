package eindopdracht.server.network;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import eindopdracht.model.Command;
import eindopdracht.server.Player;

public class PlayerHandler implements Runnable{
	private Player player;
	private Socket socket;
	protected BufferedReader in;
    protected BufferedWriter out;
    PlayerListener listener;
	
    /**
     * 
     * @param socket Socket to listen to
     * @param numberOfPlayers Number of players this player wants to play with
     * @throws IOException if the socket's in and out can't be accessed
     */
	public PlayerHandler(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		Thread handlerThread = new Thread(this);
		handlerThread.start();
	}

	@Override
	public void run() {
		this.listen();
	}
	
	public void listen() {
		try {
        	String next;
        	in.ready();
        	next = in.readLine();
        	while (next != null) {
        		//If null, the connection was terminated
        		this.handleInput(next);
        	}
        } catch (IOException e) {
			System.out.println("Error occured while reading inputstream");
		} catch (NullPointerException e) {
			//TODO Check if this is really necessary
			System.exit(0);
		}
	}
	
	private void handleInput(String input) {
		Command command = new Command(input);
		String c = command.getCommand();
		
		if (c.equals("join")) {
			player.setName(command.getArgs()[0]);
			player.setNumberOfPlayers(Integer.parseInt(command.getArgs()[1]));
			player.join();
		}
	}
	
	public void sendMessage(String msg) {
		try {
			out.write(msg);
		} catch (IOException e) {
			System.out.println("[Error] error thrown in PlayerHandler sendMessage");
			e.printStackTrace();
		}
	}
}
