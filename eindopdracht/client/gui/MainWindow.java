package eindopdracht.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.*;

import eindopdracht.client.Game;
import eindopdracht.client.model.player.AIPlayer;
import eindopdracht.client.model.player.HumanPlayer;
import eindopdracht.client.model.player.NetworkPlayer;
import eindopdracht.client.model.player.Player;
import eindopdracht.client.network.Network;
import eindopdracht.model.Command;

public class MainWindow extends javax.swing.JFrame implements WindowListener, ActionListener, Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8729792019935360588L;
	JMenuItem connectMenuItem;
	JMenuItem startMenuItem;
	JMenuItem disconnectMenuItem;
	JFrame connectFrame;
	JFrame newGameFrame;
	JLabel connectedLabel;
	Network network;
	private Game game;
	Player localPlayer;
	
	public MainWindow() {
		super("Pentago XL");
        buildGUI();
        setVisible(true);
		addWindowListener(this);
		//new Connect();
	}
	
	public void buildGUI() {
		setSize(600,400);
		
		JMenuBar menuBar;
		JMenu gameMenu;
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		gameMenu.getAccessibleContext().setAccessibleDescription("Game Options");
		menuBar.add(gameMenu);
		
		connectMenuItem = new JMenuItem("Connect");
		connectMenuItem.addActionListener(this);
		gameMenu.add(connectMenuItem);
		
		startMenuItem = new JMenuItem("Join");
		startMenuItem.addActionListener(this);
		startMenuItem.setEnabled(false);
		gameMenu.add(startMenuItem);
		
		disconnectMenuItem = new JMenuItem("Disconnect");
		disconnectMenuItem.addActionListener(this);
		disconnectMenuItem.setEnabled(false);
		gameMenu.add(disconnectMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		gameMenu.add(exitMenuItem);
		
		connectedLabel = new JLabel("Not connected");
		this.add(connectedLabel);
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(connectMenuItem)) {
			if (connectFrame == null) 
				connectFrame = new Connect(this);
		} else if (e.getSource().equals(startMenuItem)) {
			if (newGameFrame == null) 
				newGameFrame = new NewGame(this);
		} else if (e.getSource().equals(disconnectMenuItem)) {
			disconnect();
		} else if (((JMenuItem)e.getSource()).getText().equals("Exit")) {
			if (network != null)
				network.quit();
			System.exit(0);
		}
	}

	public void connect(String host, int port) {
		connectFrame = null;
		System.out.println("Connecting with " + host + " on port " + port);
		network = new Network();
		network.addObserver(this);
		if (network.connect(host, port)) {
			connectMenuItem.setEnabled(false);
			startMenuItem.setEnabled(true);
			disconnectMenuItem.setEnabled(true);
			connectedLabel.setText("Connected!");
		}
	}
	
	public void connectFrameDismissed() {
		if (connectFrame != null)
			connectFrame = null;
	}
	
	public void newGameFrameDismissed() {
		if (newGameFrame != null)
			newGameFrame = null;
	}
	
	public void join(String name, int players, boolean humanPlayer) {
		System.out.println("Joining as " + name + " in a lobby with " + players + " players max");
		if (humanPlayer)
			localPlayer =  new HumanPlayer();
		else
			localPlayer = new AIPlayer();
		localPlayer.setName(name);
		
		if (network != null) {
			network.join(name, players);
		}
	}
	
	public void disconnect() {
		if (network != null) {
			network.quit();
			connectMenuItem.setEnabled(true);
			startMenuItem.setEnabled(false);
			disconnectMenuItem.setEnabled(false);
			connectedLabel.setText("Not connected!");
		}
	}
	
	@Override
	public void update(Observable sender, Object object) {
		if (object.getClass().equals(Game.class)) {
			this.game = game;
		} else if (object.getClass().equals(Command.class)){
			System.out.println("Received a command in mainwindow!");
			Command command = (Command)object;
			if (command.getCommand().equals("start")) {
				ArrayList<Player> players = new ArrayList<Player>();
				String[] p = command.getArgs();
				for (int i = 0; i<p.length; i++) {
					if (p[i].equals(localPlayer.getName())) {
						//was the local player
						players.add(localPlayer);
					} else {
						NetworkPlayer newPlayer = new NetworkPlayer();
						newPlayer.setName(p[i]);
					}
				}
				this.game = new Game(players);
				game.addObserver(network);
				game.start();
			} else if (command.getCommand().equals("connected")) {
				localPlayer.setName(command.getArg(0));
				System.out.println("Joined, now has the name " + command.getArg(0));
			}
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		e.getWindow().dispose();		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
