package eindopdracht.client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import eindopdracht.util.NetworkUtil;

public class Connect extends JFrame implements ActionListener {

	private JTextField hostnameTextField;
	private JTextField portTextField;
	private JButton cancelButton;
	private JButton okayButton;

	public Connect() {
		super("Connect");
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
		this.add(hostnameTextField);
		
		this.add(new JLabel("Port"));
		portTextField = new JTextField(15);
		this.add(portTextField);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		this.add(cancelButton);
		okayButton = new JButton("OK");
		okayButton.addActionListener(this);
		this.add(okayButton);
	}
	
	public void sendForm() {
		System.out.println("Connecting to " + hostnameTextField.getText() + "(" + portTextField.getText() + ")");
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
		}
	}
}
