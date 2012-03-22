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
	private JButton cancelButton;
	private JButton okayButton;
	private MainWindow observer;
	
	public static String[] lobbySizes = {"2", "3", "4"};
	
	public NewGame(MainWindow observer) {
	    super("New Game");
	    this.observer = observer;
	    buildGUI();
		this.setLocation(observer.getLocationOnScreen().x + 15, observer.getLocationOnScreen().y + 15);
	    setVisible(true);
	    this.addWindowListener(this);
	}
	
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
			int size = Integer.parseInt((String)lobbySizeBox.getSelectedItem());
			observer.join(name, size, true);
			//TODO implement an option to use an AI or not
			observer.newGameFrameDismissed();
			this.dispose();
		} else if (ev.getSource().equals(nameTextField)) {
			if (okayButton.isEnabled())
				okayButton.doClick();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
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
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
        observer.newGameFrameDismissed();
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
