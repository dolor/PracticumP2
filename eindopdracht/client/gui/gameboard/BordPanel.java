package eindopdracht.client.gui.gameboard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import eindopdracht.client.Game;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;
import eindopdracht.model.Color;
import eindopdracht.model.Tile;

/**
 * Contains 9 BlockPanels and renders them when necessary
 * 
 * @author Dolor
 * 
 */

public class BordPanel extends JPanel implements Observer {

	Game game;
	ArrayList<BlockPanel> blockList;
	// ArrayList<JButton> tileList;

	private static int blockGap = 12;
	private static int tileGap = 2;

	private Set currentSet;
	private Turn currentTurn;

	public BordPanel() {
		blockList = new ArrayList<BlockPanel>();
		this.buildGUI();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private void buildGUI() {
		GridLayout blockLayout = new GridLayout(Board.DIM, Board.DIM);
		blockLayout.setHgap(blockGap);
		blockLayout.setVgap(blockGap);
		this.setLayout(blockLayout);

		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		for (int b = 0; b < Board.DIM * Board.DIM; b++) {
			BlockPanel bp = new BlockPanel(this, b);
			blockList.add(bp);
			this.add(bp);
		}
	}

	/**
	 * Shows a hint at the given tile. Hint disappears when any action has been taken.
	 * @param set to show as a hint; only requires player, block and tile
	 */
	public void showSetHint(Set set) {
		for (BlockPanel block:blockList) {
			block.resetHints();
		}
		
		int block = set.getBlock();
		this.blockList.get(block - 1).showSetHint(set.getTile());
	}
	
	/**
	 * Shows a hint for a given rotation. Hint disappears when any action has been taken.
	 * @param turn to show as a hint; only requires player, block and direction
	 */
	public void showRotateHint(Turn turn) {
		for (BlockPanel block:blockList) {
			block.resetHints();
		}
		
		int block = turn.getBlock();
		this.blockList.get(block - 1).showRotateHint(turn.getRotation());
	}

	private void setTiles(Board board) {
		for (BlockPanel bp : blockList) {
			Block block = board.getBlocks()[bp.getIndex()];
			bp.setTiles(block);
		}
	}

	private void setBlockStates(int state) {
		for (BlockPanel block : blockList) {
			block.setState(state);
		}
	}

	@Override
	public void update(Observable sender, Object object) {
		// Should only be updated by the broadcasted sets and turns. Bordpanel
		// should either be
		// enabled for setting/turning or update what it looks like

		if (object.getClass().equals(Set.class)) {
			this.setBlockStates(BlockPanel.DISABLED);

			Set set = (Set) object;
			Board board = game.getBoard();
			this.setTiles(board);

			if (!set.isExecuted()
					&& set.getPlayer().equals(game.getLocalPlayer())) {
				// Set has not yet been executed, so the blocks should be
				// settable
				this.currentSet = set;
				this.setBlockStates(BlockPanel.SETTING);
			}
		}

		else if (object.getClass().equals(Turn.class)) {
			this.setBlockStates(BlockPanel.DISABLED);
			Turn turn = (Turn) object;
			this.setTiles(game.getBoard());
			// Turn was executed, so disable the tiles if it was set by the
			// player
			if (!turn.isExecuted()
					&& turn.getPlayer().equals(game.getLocalPlayer())) {
				// Set was executed, disable the buttons if it was done by local
				// player
				this.currentTurn = turn;
				this.setBlockStates(BlockPanel.TURNING);
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
		if (this.currentSet != null) {
			currentSet.setBlock(block);
			currentSet.setTile(tile);
			System.out.println("    blockpanel performed: " + currentSet.toString());
			game.set(currentSet);
			currentSet = null;
		}
	}
	
	

	private ArrayList<BlockPanel> blocks;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2232170303403827378L;
}
