package eindopdracht.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eindopdracht.util.NetworkUtil;
import eindopdracht.util.PTLog;

public class ConnectWindow extends JFrame implements ActionListener,
		DocumentListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7659397845880348482L;
	private JTextField hostnameTextField;
	private JTextField portTextField;
	private JButton cancelButton;
	private JButton okayButton;
	private PentagoXLWindow observer;

	/**
	 * Creates a new window to connect to a server
	 * 
	 * @param pentagoXLWindow
	 *            the main window
	 */
	public ConnectWindow(PentagoXLWindow pentagoXLWindow) {
		super("Connect");
		this.observer = pentagoXLWindow;
		buildGUI();
		this.setLocation(pentagoXLWindow.getLocationOnScreen().x + 15,
				pentagoXLWindow.getLocationOnScreen().y + 15);
		setVisible(true);
		hostnameTextField.setText("localhost");
		portTextField.setText("8888");
		addWindowListener(this);
	}

	/**
	 * Set up the GUI
	 */
	public void buildGUI() {
		setSize(200, 200);
		this.setResizable(false);
		this.setLayout(new FlowLayout());

		this.add(new JLabel("Host"));
		hostnameTextField = new JTextField(15);
		hostnameTextField.getDocument().addDocumentListener(this);
		hostnameTextField.addActionListener(this);
		this.add(hostnameTextField);

		this.add(new JLabel("Port"));
		portTextField = new JTextField(15);
		portTextField.getDocument().addDocumentListener(this);
		portTextField.addActionListener(this);
		this.add(portTextField);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		this.add(cancelButton);
		okayButton = new JButton("OK");
		okayButton.addActionListener(this);
		okayButton.setEnabled(false);
		this.add(okayButton);
	}

	/**
	 * Sends the filled in variables to the main window
	 */
	public void sendForm() {
		observer.connect(hostnameTextField.getText(),
				Integer.parseInt(portTextField.getText()));
		observer.connectFrameDismissed();
		this.dispose();
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource().equals(cancelButton)) {
			this.dispose();
			observer.connectFrameDismissed();
		} else if (ev.getSource().equals(okayButton)) {
			String hostname = hostnameTextField.getText();
			String port = portTextField.getText();
			if (!hostname.isEmpty() && !port.isEmpty()) {
				if (!NetworkUtil.isValidHost(hostname))
					PTLog.log("ConnectWindow", "Invalid hostname");
				else if (!NetworkUtil.isValidPort(port))
					System.out.println("Invalid port");
				else
					sendForm();
			}
			PTLog.log("ConnectWindow", "Connecting to " + hostnameTextField.getText());
		} else if (ev.getSource().equals(hostnameTextField)
				|| ev.getSource().equals(portTextField)) {
			if (okayButton.isEnabled())
				okayButton.doClick();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent sender) {
		if (NetworkUtil.isValidHost(hostnameTextField.getText())
				&& NetworkUtil.isValidPort(portTextField.getText())) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent sender) {
		if (NetworkUtil.isValidHost(hostnameTextField.getText())
				&& NetworkUtil.isValidPort(portTextField.getText())) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.dispose();
		observer.connectFrameDismissed();
	}

	// Not used but required methods

	@Override
	public void changedUpdate(DocumentEvent sender) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
