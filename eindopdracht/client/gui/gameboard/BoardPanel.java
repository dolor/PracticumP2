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

import eindopdracht.client.GameController;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;

public class BoardPanel extends JPanel implements Observer, ComponentListener{
	private BufferedImage backgroundImg;
	private ArrayList<BlockPanel> blocks;
	private GameController game;
	private Turn currentTurn;
	private Set currentSet;
	
	public static int dimension = 3;
	private static int minimumSize = 300;
	
	/**
	 * Create a new bord view
	 */
	public BoardPanel() {
		this.loadImages();
		this.buildGUI();
		this.setBackground(java.awt.Color.red);
	}
	
	/**
	 * load all necessary images in memory
	 */
	private void loadImages() {
		try {
			backgroundImg = ImageIO.read(new File("eindopdracht/resources/Texture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set up the GUI
	 */
	public void buildGUI() {
		this.setLayout(new GridLayout(dimension, dimension));
		this.setBounds(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
		
		blocks = new ArrayList<BlockPanel>();
		int width = this.getSize().width;
		int height = this.getSize().height;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				BlockPanel block = new BlockPanel(this, x*dimension + y);
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

	/**
	 * 
	 * @param game the game that this view represents
	 * @require game != null
	 */
	public void setGame(GameController game) {
		this.game = game;
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
			} else if (set.getBlock() >= 0 && set.getTile() >= 0){
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
			} else if (turn.getBlock() >= 0 && turn.getRotation() >= 1 && turn.getRotation() <= 2){
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
	 * @require block 0<=block<=8, direction == 1|2
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
	 * @require 0<=block<=8, 0<=tile<=8
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
	 * @require 0 <= state <= 2
	 */
	public void setBlockStates(int state) {
		for (BlockPanel block:blocks) {
			block.setState(state);
		}
		this.repaint();
	}
	
	/**
	 * Updates all blocks so it shows the correct tiles
	 * @param board
	 * @require board != null
	 */
	public void updateTiles(Board board) {
		for (int i = 0; i < blocks.size(); i++) {
			Block block = board.getBlock(i);
			blocks.get(i).updateTiles(block);
		}
		this.repaint();
	}

	@Override
	public Dimension getMinimumSize() {
	    return new Dimension(minimumSize, minimumSize);
	}
	
	/**
	 * Shows the given set as a hint
	 * @param set to display as a hint
	 * @require set != null and valid
	 * @ensure will be shown on the view
	 */
	public void showSetHint(Set set) {
		
	}

	/**
	 * Shows the given turn as a hint
	 * @param turn to display as a hint
	 * @require turn != null and valid
	 * @ensure will be shown on the view
	 */
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
