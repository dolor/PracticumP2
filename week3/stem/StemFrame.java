package week3.stem;

import java.awt.*;
import java.awt.event.*;

public class StemFrame extends Frame implements ItemListener, WindowListener, ActionListener{

	java.awt.Label tekstLabel;
	Button keuzeButton;
	Choice keuze1;

	public static void main(String args[]) {
		new StemFrame();
	}

	public StemFrame() {
		super("Stem");
		init();
	}

	private void init() {
		this.setBounds(20, 20, 250, 200);
		setLayout(new FlowLayout()); // verander naar FlowLayout
		// default is BorderLayout
		Panel tekstPanel = new Panel();
		//tekstPanel.setBounds(0, 0, 250, 40);
		tekstLabel = new Label();
		tekstLabel.setText("Kies een partij");
		tekstLabel.setSize(200, 15);
		//tekstPanel.add(tekstLabel);
		this.add(tekstLabel);
		this.addWindowListener(this);

		keuze1 = new Choice();
		keuze1.setSize(50, 10);
		keuze1.addItem("Maak een keuze");
		keuze1.addItem("Partij 1");
		keuze1.addItem("Partij 2");
		keuze1.addItemListener(this);
		this.add(keuze1);

		keuzeButton = new Button("Confirm");
		keuzeButton.setEnabled(false);
		keuzeButton.addActionListener(this);
		this.add(keuzeButton, FlowLayout.CENTER);
		setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7025890815865666840L;

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem().equals("Maak een keuze")) {
			tekstLabel.setText("Kies een partij");
			keuzeButton.setEnabled(false);
		} else {
			tekstLabel.setText("Verander keuze of druk op Confirm");
			keuzeButton.setEnabled(true);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(keuzeButton)) {
			System.out.println("Gestemd voor " + keuze1.getSelectedItem());
			keuze1.select(0);
			keuzeButton.setEnabled(false);
			tekstLabel.setText("Kies een partij");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
