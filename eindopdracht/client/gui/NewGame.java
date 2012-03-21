package eindopdracht.client.gui;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class NewGame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6273897856907897047L;
	private JTextField nameTextField;

	public NewGame() {
	    super("New Game");
	    buildGUI();
	    setVisible(true);

	        addWindowListener(new WindowAdapter() {
	                public void windowClosing(WindowEvent e) {
	                    e.getWindow().dispose();
	                }
	                public void windowClosed(WindowEvent e) {
	                    System.exit(0);
	                }
	            }
	        );
	    }
	
	public void buildGUI() {
		setSize(400, 200);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		
		
	}
	
}
