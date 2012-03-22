package week4.chatbox;

import java.io.*;
import java.net.*;
import java.security.InvalidParameterException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ClientGUI extends JFrame 
                       implements ActionListener, MessageUI, DocumentListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -127977638163760634L;
	private JButton     connectButton;
    private JTextField  portTextField;
    private JTextField  hostnameTextField;
    private JTextField  nameTextField;
    private JTextField  chatTextField;
    private JTextArea   messageTextArea;
    private JLabel 		errorLabel;
    private Server      server;
    private Client 		client;

    /** Construeert een ClientGUI object. */
    public ClientGUI() {
    super("Client GUI");
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

    /** Bouwt de daadwerkelijke GUI. */
    private void buildGUI() {
    	setSize(600,400);

        JPanel p1 = new JPanel(new FlowLayout());
        JPanel pp = new JPanel(new GridLayout(3,3));

        JLabel hostnameLabel = new JLabel("Address: ");
        hostnameTextField = new JTextField("", 12);
        hostnameTextField.getDocument().addDocumentListener(this);
        hostnameTextField.addActionListener(this);

        JLabel portLabel = new JLabel("Port:");
        portTextField        = new JTextField("2727", 5);
        portTextField.getDocument().addDocumentListener(this);
        portTextField.addActionListener(this);
        
        JLabel nameLabel = new JLabel("Name:");
        try {
			nameTextField = new JTextField(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			nameTextField = new JTextField("name");
		}
        nameTextField.getDocument().addDocumentListener(this);
        nameTextField.addActionListener(this);

        pp.add(hostnameLabel);
        pp.add(hostnameTextField);
        pp.add(portLabel);
        pp.add(portTextField);
        pp.add(nameLabel);
        pp.add(nameTextField);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        connectButton.setEnabled(false);
        
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        p1.add(pp, BorderLayout.WEST);
        p1.add(connectButton, BorderLayout.EAST);
        p1.add(errorLabel, BorderLayout.EAST);

        JPanel p2c = new JPanel(); //For the chatting
        p2c.setLayout(new BorderLayout());

        JLabel chatLabel = new JLabel("My message:");
        chatTextField = new JTextField();
        chatTextField.addActionListener(this);
        chatTextField.setEnabled(false);

        p2c.add(chatLabel, BorderLayout.NORTH);
        p2c.add(chatTextField, BorderLayout.SOUTH);

        JPanel p2m = new JPanel(); //For displaying the sent messages
        p2m.setLayout(new BorderLayout());
        
        JLabel messagesLabel = new JLabel("Messages:");
        messageTextArea = new JTextArea("", 15, 50);
        messageTextArea.setEditable(false);
        p2m.add(messagesLabel);
        p2m.add(messageTextArea, BorderLayout.SOUTH);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        
        p2.add(p2c, BorderLayout.NORTH);
        p2.add(p2m, BorderLayout.SOUTH);
        
        Container cc = getContentPane();
        cc.setLayout(new FlowLayout());
        cc.add(p1); cc.add(p2);
    }

    /** Afhandeling van een actie van het GUI. */
    public void actionPerformed(ActionEvent ev) {
    	System.out.println("Action performed!");
    	Object s = ev.getSource();
    	boolean canTryConnect = true;
        if (s.equals(connectButton) || (s.getClass().equals(JTextField.class) && connectButton.isEnabled())) {
        	if (!isValidIP(hostnameTextField.getText())) {
        		errorLabel.setText("Invalid IP");
        		canTryConnect = false;
        	} else {
        		 errorLabel.setText("");
        	} if (!isValidPort(portTextField.getText())) {
        		errorLabel.setText("Invalid Port");
        		canTryConnect = false;
        	} else {
        		errorLabel.setText("");
        	}
        	if (canTryConnect) {
        		connect();
        	}
        }
        if (s.equals(chatTextField)) {
        	client.sendMessage(chatTextField.getText());
        	chatTextField.setText("");
        }
    }
    
    @Override
	public void changedUpdate(DocumentEvent ev) {
    	Object s = ev.getDocument();
    	if (s.equals(hostnameTextField.getDocument()) || s.equals(portTextField.getDocument()) || s.equals(nameTextField.getDocument())) {
        	if (!hostnameTextField.getText().isEmpty() && !portTextField.getText().isEmpty() && !nameTextField.getText().isEmpty()) {
        		connectButton.setEnabled(true);
        	} else {
        		connectButton.setEnabled(false);
        	}
        }
	}
    
    public boolean isValidIP(String ip) {
    	boolean certainlyFalse = false;
    	InetAddress addr = null;
    	if (ip.toLowerCase().equals("localhost")) {
        	try {
				addr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {return false;}
        } else {
        	String[] numStrings = ip.split("\\.");
        	byte[] addressBytes = new byte[4];
        	if (numStrings.length == 4) {
        		for (int i = 0; i < 4; i++) {
        			if (Integer.parseInt(numStrings[i]) < 0 || Integer.parseInt(numStrings[i]) > 255) {
                		return false;
        			} else {
        				addressBytes[i] = (byte) Integer.parseInt(numStrings[i]);
        			}
        		}
        	} else {
        		return false;
        	}
        	System.out.println("Valid address!");
        	if (!certainlyFalse) {
        		try {
    				addr = InetAddress.getByAddress(addressBytes);
    			} catch (UnknownHostException e) {return false;}
            	//TODO (?): Add support for ipv6
        	}
        }
    	if (addr != null)
    		return true;
    	else
    		return false;
    }
    
    public boolean isValidPort(String port) {
    	try {
        	int p = Integer.parseInt(port);
        	if (p < 0 || p > 65535) {
				System.out.println(port + ": Not a valid port number");
        		return false;    		
        	} else
        		return true;
        } catch (NumberFormatException e) {
    		return false;      	
        }
    }

    /**
     * Probeert een socket-verbinding op te zetten met de Server.
     * Als alle parametervelden geldig zijn, dan wordt getracht een
     * Client-object te construeren die de daadwerkelijke 
     * socketverbinding met de Server maakt. Als dit gelukt is 
     * wordt een aparte thread (van Client) opgestart, die de 
     * berichten van de Server leest.
     * Daarna worden alle parametervelden en de "Connect" Button
     * niet selecteerbaar gemaakt.
     */
    public void connect() {
    	boolean crashed = false;
		System.out.println("Now trying to connect!");
		String name = nameTextField.getText();
		String host = hostnameTextField.getText();
		int port = Integer.parseInt(portTextField.getText());
		InetAddress addr;
		try {
			addr = InetAddress.getByName(host);
			client = new Client(name, addr, port, this);
	        addMessage("Connected to server...");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			crashed = true;
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			crashed = true;
		} catch (IOException e) {
			System.out.println("Could not connect: " + e.getMessage());
			crashed = true;
		}
		
		if (!crashed) {
	        connectButton.setEnabled(false);
	        hostnameTextField.setEnabled(false);
	        portTextField.setEnabled(false);
	        nameTextField.setEnabled(false);
	        chatTextField.setEnabled(true);
		}
    }

    /** Voegt een bericht toe aan de TextArea op het frame. */
    public void addMessage(String msg) {
        messageTextArea.append("\n" + msg);
    }

    /** Start een ClientGUI applicatie op. */
    public static void main(String args[]) {
        ClientGUI gui = new ClientGUI();
    }

	
    @Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}
    
	

} // end of class ClientGUI
