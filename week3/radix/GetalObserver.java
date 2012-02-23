package week3.radix;

import java.awt.Container;
import java.util.Observable;
import javax.swing.*;
import java.util.Observer;
import week1.getallen.NaarRadix;

public class GetalObserver extends javax.swing.JFrame implements Observer {

	JLabel textLabel;
	int radix;
	
	public GetalObserver(Getal getalToObserve, int radix) {
		getalToObserve.addObserver(this);
		this.radix = radix;
		init();
		int i = getalToObserve.getWaarde();
		if (i != 0)
			setLabel(i);
	}
	
	public void init() {
		this.setSize(150, 100);
		Container c = getContentPane();
		textLabel = new JLabel();
		c.add(textLabel);
		this.setVisible(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setLabel(Integer.parseInt(arg.toString()));
	}

	public void setLabel(int waarde) {
		textLabel.setText("" + NaarRadix.naarRadix(radix, waarde));
	}
}
