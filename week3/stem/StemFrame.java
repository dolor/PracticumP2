package week3.stem;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class StemFrame extends Frame implements ItemListener, WindowListener, ActionListener, Observer{

	java.awt.Label tekstLabel;
	Button keuzeButton;
	Choice partijKeuzeLijst;
	Uitslag uitslag;

	public StemFrame(Uitslag uitslag) {
		super("Stem");
		this.uitslag = uitslag;
		uitslag.addObserver(this);
		init();
	}

	private void init() {
		this.setBounds(20, 20, 250, 200);
		setLayout(new FlowLayout());
		Panel tekstPanel = new Panel();
		tekstLabel = new Label();
		tekstLabel.setText("Kies een partij");
		tekstLabel.setSize(200, 15);
		this.add(tekstLabel);
		this.addWindowListener(this);

		partijKeuzeLijst = new Choice();
		partijKeuzeLijst.setSize(50, 10);
		partijKeuzeLijst.addItem("Maak een keuze");
		partijKeuzeLijst.addItemListener(this);
		this.add(partijKeuzeLijst);

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
			System.out.println("Gestemd voor " + partijKeuzeLijst.getSelectedItem());
			uitslag.stem(partijKeuzeLijst.getSelectedItem());
			partijKeuzeLijst.select(0);
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

	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass().equals(Uitslag.class)) {
			Set<String> partijLijst = ((Uitslag)o).getPartijen();
			partijKeuzeLijst.removeAll();
			partijKeuzeLijst.addItem("Maak een keuze");
			for (String p:partijLijst)
				partijKeuzeLijst.add(p);
		}
	}

}
