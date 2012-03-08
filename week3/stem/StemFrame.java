package week3.stem;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class StemFrame extends Frame implements Observer, ActionListener, ItemListener{
	Button btnKnop;
	Choice choPartij;
	Label lblKies;
	Uitslag uitslag;
	public StemFrame(Uitslag uitslag)
	{
		super("Stem");
		this.uitslag = uitslag;
		this.uitslag.addObserver(this);
		init();
	}
	
	private void init()
	{
	
		this.setLayout(new FlowLayout()); 
		
		choPartij = new Choice();
		choPartij.add("Maak een keuze");
		choPartij.addItemListener(this);

		btnKnop = new Button("Kies");
		btnKnop.setEnabled(false);
		btnKnop.addActionListener(this);
		
		lblKies = new Label("Kies een partij");
		
		this.add(lblKies);
		this.add(choPartij);
		this.add(btnKnop);
		
		
		this.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e)
			{ 
            	System.exit(0); 				// stop programma als op X wordt gedrukt
            }
		});
		
		this.setSize(200, 200);
		this.setVisible(true);
	}
	
	static public void main(String[] args) {
		new StemFrame(null);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		// TODO Auto-generated method stub
		if (choPartij.getSelectedIndex() == 0)
		{
			btnKnop.setEnabled(false);
		}
		else
		{
			btnKnop.setEnabled(true);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnKnop))
		{
			// stem uitvoeren
			this.uitslag.stem(choPartij.getSelectedItem());
			
			// reset
			choPartij.select(0);
			btnKnop.setEnabled(false);
			
			
		}
	}
	
	public void displayStemmen(Map<String, Integer> stemmen)
	{
		this.choPartij.removeAll();
		
		this.choPartij.addItem("Kies een partij");
		
		for (String naam : stemmen.keySet())
		{
			this.choPartij.add(naam);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// vul de choice box
		displayStemmen((Map<String, Integer>)arg);
		
		
	}

}
