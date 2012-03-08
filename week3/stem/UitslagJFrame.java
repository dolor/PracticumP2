package week3.stem;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class UitslagJFrame extends JFrame implements Observer {
	JTextArea jtaUitslag;
	public UitslagJFrame(Uitslag u )
	{
		super("Verkiezings uitslag");
		u.addObserver(this);
		init();

	}
	
	public void init()
	{
		jtaUitslag = new JTextArea();
		
		this.add(jtaUitslag);
		
		this.setVisible(true);
		this.setSize(100,300);
		
	}

	private void displayUitslag(Map<String, Integer> stemmen)
	{
		String text = "";
		 for (Map.Entry<String,Integer> e: stemmen.entrySet()) 
		 {
			  String partij = e.getKey();
			  Integer stem = e.getValue();
			  
			  text = text + partij + " "+stem+"\n";
		 }
		 jtaUitslag.setText(text);
	}
	@Override
	public void update(Observable o, Object arg) {
		displayUitslag((Map<String, Integer>)arg);
	}

}
