package week3.bke;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class BKEView extends JFrame implements Observer{
	
	JButton[] vakjes;
	JPanel speelbord;
	JLabel lblStatus;
	JButton btnNieuwSpel;
	
	public BKEView (Spel s)
	{
		super("Boter, Kaas en Eieren");
		
		BKEController controller = new BKEController(s);
		
		this.setLayout(new GridLayout(3,1));
		this.setSize(600, 400);
		
		speelbord = new JPanel();
		speelbord.setLayout(new GridLayout(0, 3));
		
		vakjes = new JButton[Bord.DIM*Bord.DIM];
		
		for (int i = 0; i < Bord.DIM*Bord.DIM; i++)
		{
			vakjes[i] = new JButton();
			speelbord.add(vakjes[i]);
			vakjes[i].addActionListener(controller);
 		}
		
		lblStatus = new JLabel("Status");
		
		
		this.add(speelbord);
		this.add(lblStatus);
		
		btnNieuwSpel = new JButton("Nog een keer!");
		btnNieuwSpel.setEnabled(false);
		btnNieuwSpel.addActionListener(controller);
		
		
		this.add(btnNieuwSpel);
		
		this.setVisible(true);
		
		
	}
	
	public static void main(String[] args)
	{
		Spel s = new Spel();
		BKEView view = new BKEView(s);
		s.addObserver(view);
		
		s.reset();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		Spel s = (Spel)o;
		Bord b = s.getBord();
		
		// update het speelveld
		if (arg == null)
		{
			// er is een reset gedaan;
			for (int i = 0; i < Bord.DIM*Bord.DIM; i++)
			{
				vakjes[i].setText("");
	 		}
			
			btnNieuwSpel.setEnabled(false);
		}
		else
		{
			Integer i = (Integer)arg;
			vakjes[i].setText(s.getHuidig().other().toString()); // het is de vorige die gezet heeft, de huidige speler is al omgezet;
			btnNieuwSpel.setEnabled(false);
		}
		
		// Update status
		
		if (b.heeftWinnaar())
		{
			lblStatus.setText(s.getHuidig().other().toString() + " is winnaar!");

			btnNieuwSpel.setEnabled(true);
		}
		else if (b.isVol())
		{
			lblStatus.setText("Remise");
			btnNieuwSpel.setEnabled(true);
		}
		else
		{
			lblStatus.setText(s.getHuidig().toString()+" is aan de beurt.");
		}
	}
	
	public class BKEController implements ActionListener
	{
		Spel s;
		public BKEController(Spel s)
		{
			this.s = s;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnNieuwSpel))
			{
				s.reset();
			}
			else if (!s.getBord().gameOver())
			{
				for (int i = 0; i < Bord.DIM*Bord.DIM; i++)
				{
					if (e.getSource().equals(vakjes[i]))
					{
						s.doeZet(i);
					}
				}
			}
					
			
		}
		
	}

}