package eindopdracht.client.gui.gameboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eindopdracht.client.Game;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;

public class FancyBordPanel extends JPanel implements Observer, ComponentListener{
	private BufferedImage backgroundImg;
	private ArrayList<FancyBlock> blocks;
	private Game game;
	private Turn currentTurn;
	private Set currentSet;
	
	public static int dimension = 3;
	private static int minimumSize = 300;
	
	public FancyBordPanel() {
		this.loadImages();
		this.buildGUI();
		this.setBackground(java.awt.Color.red);
	}
	
	public void loadImages() {
		try {
			backgroundImg = ImageIO.read(new File("eindopdracht/resources/Texture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void buildGUI() {
		this.setLayout(new GridLayout(dimension, dimension));
		this.setBounds(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
		
		blocks = new ArrayList<FancyBlock>();
		int width = this.getSize().width;
		int height = this.getSize().height;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				FancyBlock block = new FancyBlock(this, x*dimension + y);
				block.setBounds(width/3 * x, height/3 * y, width/3, height/3);
				this.addComponentListener(block);
				this.add(block);
				blocks.add(block);
			}
		}
		
		this.repaint();
	}
	
	/**
	 * Called when this component has to draw its contents
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, this.getSize().width, this.getSize().height,null);
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void tileSelected(int block, int tile) {
		
	}
	
	public void rotated(int block, int rotation) {
		
	}

	@Override
	public void update(Observable sender, Object object) {
		// Should only be updated by the broadcasted sets and turns. Bordpanel
		// should either be
		// enabled for setting/turning or update what it looks like

		if (object.getClass().equals(Set.class)) {
			//Disable the blocks
			this.setBlockStates(BlockPanel.DISABLED);

			Set set = (Set) object;
			Board board = game.getBoard();
			//Update the tiles

			if (!set.isExecuted()
					&& set.getPlayer().equals(game.getLocalPlayer())) {
				// Set has not yet been executed, so the blocks should be
				// settable
				this.currentSet = set;
				this.setBlockStates(BlockPanel.SETTING);
			} else {
				int updatedBlock = set.getBlock();
				System.out.println("Updating block " + updatedBlock);
				blocks.get(updatedBlock).updateTiles(board.getBlock(updatedBlock));
				this.updateTiles(board);
			}
		}

		else if (object.getClass().equals(Turn.class)) {
			//Disable the blocks
			this.setBlockStates(BlockPanel.DISABLED);
			
			Turn turn = (Turn) object;
			Board board = game.getBoard();
			//update the tiles
			
			// Turn was executed, so disable the tiles if it was set by the
			// player
			if (!turn.isExecuted()
					&& turn.getPlayer().equals(game.getLocalPlayer())) {
				// Set was executed, disable the buttons if it was done by local
				// player
				this.currentTurn = turn;
				this.setBlockStates(BlockPanel.TURNING);
			} else {
				int updatedBlock = turn.getBlock();
				System.out.println("Updating block " + updatedBlock);
				blocks.get(updatedBlock).updateTiles(board.getBlock(updatedBlock));
				this.updateTiles(board);
			}
		}
	}
	
	/**
	 * Called by a block when a turn has been ordered.
	 * 
	 * @param block
	 * @param direction
	 */
	public void turn(int block, int direction) {
		this.resetHints();
		if (this.currentTurn != null) {
			currentTurn.setBlock(block);
			currentTurn.setRotation(direction);
			System.out.println("    blockpanel performed: " + currentTurn.toString());
			game.turn(currentTurn);
			currentTurn = null;
		}
	}

	/**
	 * Called by a block when a set has been done
	 * 
	 * @param block
	 * @param tile
	 */
	public void set(int block, int tile) {
		this.resetHints();
		if (this.currentSet != null) {
			currentSet.setBlock(block);
			currentSet.setTile(tile);
			System.out.println("    blockpanel performed: " + currentSet.toString());
			game.set(currentSet);
			currentSet = null;
		}
	}
	
	/**
	 * Resets all shown hints
	 */
	public void resetHints() {
		
	}
	
	/**
	 * Sets the designated state to all blocks
	 * @param state
	 */
	public void setBlockStates(int state) {
		for (FancyBlock block:blocks) {
			block.setState(state);
		}
		this.repaint();
	}
	
	/**
	 * Updates all blocks so it shows the correct tiles
	 * @param board
	 */
	public void updateTiles(Board board) {
		for (int i = 0; i < blocks.size(); i++) {
			Block block = board.getBlock(i);
			blocks.get(i).updateTiles(block);
		}
		this.repaint();
	}

	/*public Dimension getPreferredSize() {
	    return new Dimension(minimumSize, minimumSize);
	}*/

	public Dimension getMinimumSize() {
	    return new Dimension(minimumSize, minimumSize);
	}
	
	/**
	 * Implemented to force this component to always be square
	 */
	/*@Override
	public void setBounds(int x, int y, int width, int height) {
		   int currentWidth = getWidth();
		   int currentHeight = getHeight();
		   if (currentWidth!=width || currentHeight!=height) {
		      // Size has changed
			  //System.out.println("Trying to set to size " + currentWidth + " x " + currentHeight);
		      width = height = Math.min(currentWidth, currentHeight);
		      width = height = Math.max(currentWidth, minimumSize);
		     // System.out.println("Setting to size " + width);
		   }
		   super.setBounds(x, y, width, height);
		}*/
	
	public void showSetHint(Set set) {
		
	}

	public void showRotateHint(Turn turn) {
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		JPanel parent = (JPanel) e.getSource();
		int size = Math.min(parent.getWidth(), parent.getHeight());
		size = Math.max(size, minimumSize);
		int x = parent.getSize().width / 2 - size / 2;
		int y = parent.getSize().height / 2 - size / 2;
		this.setBounds(x, y, size, size);
		
		GridLayout layout = new GridLayout(dimension, dimension);
		layout.setHgap(8);
		layout.setVgap(8);
		this.setLayout(layout);
		this.updateUI();
	}

	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
}
