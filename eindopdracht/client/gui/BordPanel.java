package eindopdracht.client.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import eindopdracht.client.Game;
import eindopdracht.model.Board;

/**
 * Contains 9 BlockPanels and renders them when necessary
 * @author Dolor
 *
 */

public class BordPanel extends JPanel implements ActionListener{
	
	Game game;
	ArrayList<ArrayList<JButton>> blockList;
	ArrayList<JButton> tileList;
	
	private static int blockGap = 12;
	private static int tileGap = 2;
	
	public BordPanel(Game game) {
		this.game = game;
		blockList = new ArrayList<ArrayList<JButton>>();
		tileList = new ArrayList<JButton>();
		this.buildGUI();
	}
	
	private void buildGUI() {
		GridLayout blockLayout = new GridLayout(Board.DIM, Board.DIM);
		blockLayout.setHgap(blockGap);
		blockLayout.setVgap(blockGap);
		this.setLayout(blockLayout);
		
		//Create a Panel for every block, to easier make the layout and indexes
		for (int b = 0; b < Board.DIM; b++) {
			JPanel blockPanel = new JPanel();
			GridLayout tileLayout = new GridLayout(Board.DIM, Board.DIM);
			tileLayout.setHgap(tileGap);
			tileLayout.setVgap(tileGap);
			blockPanel.setLayout(tileLayout);
			
			JButton tileButton = new JButton();
			blockPanel.add(tileButton);
		}
	}
	
	/**
	 * Get the block number of the button
	 * @param index
	 * @return the block the button is in, or -1 if the button is not in any block
	 */
	private int getBlockForButton(JButton button) {
		for (ArrayList<JButton> list:blockList) {
			if (list.contains(button))
				return blockList.indexOf(list);
		}
		return -1;
	}
	
	/**
	 * Get the tile that the button represents within the block
	 * @param button
	 * @return the tile that the block represents or null if the button is in the lists
	 */
	private int getTileForButton(JButton button) {
		for (ArrayList<JButton> list:blockList) {
			for (JButton b:list) {
				if (list.contains(button))
					return list.indexOf(button);
			}
		}
		return -1;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int block = getBlockForButton((JButton)e.getSource());
		int tile = getTileForButton((JButton)e.getSource());
	}
	
	private ArrayList<BlockPanel> blocks;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2232170303403827378L;


}
