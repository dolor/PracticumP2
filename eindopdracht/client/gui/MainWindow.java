package eindopdracht.client.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class MainWindow extends javax.swing.JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8729792019935360588L;

	public MainWindow() {
		super("Pentago XL");
        buildGUI();
        setVisible(true);
		addWindowListener(this);
		new Connect();
	}
	
	public void buildGUI() {
		setSize(600,400);
		
		JMenuBar menuBar = new JMenuBar();
	}
	
	public static void main(String[] args) {
		new MainWindow();
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
