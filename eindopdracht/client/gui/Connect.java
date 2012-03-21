package eindopdracht.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eindopdracht.util.NetworkUtil;

public class Connect extends JFrame implements ActionListener, DocumentListener {

	private JTextField hostnameTextField;
	private JTextField portTextField;
	private JButton cancelButton;
	private JButton okayButton;
	private MainWindow observer;

	public Connect(MainWindow observer) {
		super("Connect");
		this.observer = observer;
		buildGUI();
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}

			public void windowClosed(WindowEvent e) {
				//e.getWindow().dispose();
			}
		});
	}

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
	
	public void sendForm() {
		observer.connect(hostnameTextField.getText(), Integer.parseInt(portTextField.getText()));
		observer.connectFrameDismissed();
		this.dispose();
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource().equals(cancelButton)) {
			this.dispose();
		} else if (ev.getSource().equals(okayButton)) {
			String hostname = hostnameTextField.getText();
			String port = portTextField.getText();
			if (!hostname.isEmpty() && !port.isEmpty()) {
				if (!NetworkUtil.isValidHost(hostname))
					System.out.println("Invalid hostname");
				else if (!NetworkUtil.isValidPort(port))
					System.out.println("Invalid port");
				else
					sendForm();
			}
			System.out.println("Connecting to " + hostnameTextField.getText());
		} else if (ev.getSource().equals(hostnameTextField) || ev.getSource().equals(portTextField)) {
			if (okayButton.isEnabled())
				okayButton.doClick();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent sender) {
		//if (sender.getDocument().equals(hostnameTextField.getDocument())) {
			
		//}
	}

	@Override
	public void insertUpdate(DocumentEvent sender) {
		if (NetworkUtil.isValidHost(hostnameTextField.getText()) && NetworkUtil.isValidPort(portTextField.getText())) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent sender) {
		if (NetworkUtil.isValidHost(hostnameTextField.getText()) && NetworkUtil.isValidPort(portTextField.getText())) {
			okayButton.setEnabled(true);
		} else {
			okayButton.setEnabled(false);
		}
	}
}
