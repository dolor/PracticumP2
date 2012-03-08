package week3.radix;

import java.util.Observable;
import java.util.Observer;
import java.awt.*;

import javax.swing.JFrame;

public class GetalObserver extends JFrame implements Observer{

	int radix;
	Label lblGetal;
	public GetalObserver(Getal o, int radix)
	{
		super("Getal Observer");
		o.addObserver(this);
		this.radix = radix;
		displayWaarde(o.getWaarde());
		this.add(lblGetal);
		this.setVisible(true);
		this.setSize(100, 100);
	}

	private void displayWaarde(int waarde)
	{
		lblGetal = new Label(NaarRadix.naarRadix(radix, waarde));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		displayWaarde(Integer.parseInt(arg.toString()));
	}
	
	

}
