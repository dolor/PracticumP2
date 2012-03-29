package eindopdracht.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eindopdracht.util.NetworkUtil;

public class NewGame extends JFrame implements DocumentListener, ActionListener, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6273897856907897047L;
	private JTextField nameTextField;
	private JComboBox lobbySizeBox;
	private JCheckBox aiButton;
	private JButton cancelButton;
	private JButton okayButton;
	private PentagoXLWindow observer;
	
	public static String[] lobbySizes = {"2", "3", "4"};
	
	/**
	 * creates a window to start a new game
	 * @param pentagoXLWindow the main window
	 */
	public NewGame(PentagoXLWindow pentagoXLWindow) {
	    super("New Game");
	    this.observer = pentagoXLWindow;
	    buildGUI();
		this.setLocation(pentagoXLWindow.getLocationOnScreen().x + 15, pentagoXLWindow.getLocationOnScreen().y + 15);
	    setVisible(true);
	    this.addWindowListener(this);
	}
	
	/**
	 * Set up the GUI of this window
	 */
	public void buildGUI() {
		setSize(200, 200);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		
		this.add(new JLabel("Name"));
		nameTextField = new JTextField(15);
		nameTextField.getDocument().addDocumentListener(this);
		nameTextField.addActionListener(this);
		this.add(nameTextField);
		
		this.add(new JLabel("Lobby Size"));
		lobbySizeBox = new JComboBox(lobbySizes);
		lobbySizeBox.addActionListener(this);
		this.add(lobbySizeBox);
		
		aiButton = new JCheckBox("AI");
		this.add(aiButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		this.add(cancelButton);
		okayButton = new JButton("OK");
		okayButton.addActionListener(this);
		okayButton.setEnabled(false);
		this.add(okayButton);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource().equals(cancelButton)) {
			this.dispose();
			observer.newGameFrameDismissed();
		} else if (ev.getSource().equals(okayButton)) {
			String name = nameTextField.getText();
			name.replaceAll("\t", "_");
			name.replaceAll("\r", "_");
			name.replaceAll("\n", "_");
			int size = Integer.parseInt((String)lobbySizeBox.getSelectedItem());
			boolean useAI = !aiButton.isSelected();
			observer.join(name, size, useAI);
			//TODO implement an option to use an AI or not
			observer.newGameFrameDismissed();
			this.dispose();
		} else if (ev.getSource().equals(nameTextField)) {
			if (okayButton.isEnabled())
				okayButton.doClick();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		if (!nameTextField.getText().isEmpty()) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		if (!nameTextField.getText().isEmpty()) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
        observer.newGameFrameDismissed();
	}
	
	//Methods that have to be implemented for the interface but are not used
	
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void changedUpdate(DocumentEvent arg0) {}
	
}
