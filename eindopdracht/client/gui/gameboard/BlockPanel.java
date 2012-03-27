package eindopdracht.client.gui.gameboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Board;
import eindopdracht.model.Tile;
import eindopdracht.util.ModelUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class BlockPanel extends JPanel implements ActionListener, ComponentListener{

	private Block blockModel;
	
	private JPanel rotatePanel;
	private JButton rotateCCW;
	private JButton rotateCW;
	
	private JButton setHint;
	private JButton rotateHint;
	
	private JPanel setPanel;
	private ArrayList<JButton> setButtons;
	
	private BordPanel mainPanel;
	private int state;
	private int index;
	private int currentWidth;
	private int currentHeight;

	public static int DISABLED = 0;
	public static int SETTING = 1;
	public static int TURNING = 2;
	
	private static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 1);
	private static Border hintBorder = BorderFactory.createLineBorder(Color.magenta, 5);

	/**
	 * Used to show the rotation buttons
	 * 
	 * @param mainPanel
	 *            the superview that should be notified when things are clicked
	 *            etc.
	 * @param index
	 *            the index of this block in the board
	 */
	public BlockPanel(BordPanel mainPanel, int index) {
		this.mainPanel = mainPanel;
		this.index = index;
		this.addComponentListener(this);
		this.buildGUI();
	}

	private void buildGUI() {
		this.setLayout(new BorderLayout());
		
		this.currentWidth = this.getWidth();
		this.currentHeight = this.getHeight();
		
		rotatePanel = new JPanel();
		rotatePanel.setOpaque(false);
		GridLayout rotateLayout = new GridLayout(1, 2);
		rotatePanel.setLayout(rotateLayout);
		
		rotateCW = new JButton("R");
		rotateCW.addActionListener(this);
		rotateCW.setOpaque(false);
		rotateCW.setContentAreaFilled(false);
		rotateCW.setBorder(defaultBorder);
		rotatePanel.add(rotateCW);

		rotateCCW = new JButton("L");
		rotateCCW.addActionListener(this);
		rotateCCW.setOpaque(false);
		rotateCCW.setContentAreaFilled(false);
		rotateCCW.setBorder(defaultBorder);
		rotatePanel.add(rotateCCW);
		
		setPanel = new JPanel();
		setPanel.setOpaque(false);
		GridLayout setLayout = new GridLayout(Board.DIM, Board.DIM);
		setPanel.setLayout(setLayout);
		
		setButtons = new ArrayList<JButton>();
		for (int t = 0; t < Board.DIM * Board.DIM; t++) {
			JButton tile = new JButton("x");
			tile.setOpaque(false);
			tile.setContentAreaFilled(false);
			tile.setRolloverEnabled(false);
			tile.setBorder(defaultBorder);
			tile.setFocusPainted(false);
			
			tile.addActionListener(this);
			setButtons.add(tile);
			setPanel.add(tile);
		}
	}

	public void setState(int state) {
		this.state = state;
		if (state == DISABLED) {
			this.setDisabled();
		} else if (state == SETTING) {
			this.setSetting();
		} else if (state == TURNING) {
			this.setTurning();
		}
	}

	public int getState() {
		return this.state;
	}

	public int getIndex() {
		return this.index;
	}

	/**
	 * Disable all buttons
	 */
	public void setDisabled() {
		this.remove(setPanel);
		this.remove(rotatePanel);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Enable the set buttons
	 */
	public void setSetting() {
		this.add(setPanel);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Enable the turn buttons
	 */
	public void setTurning() {
		this.add(rotatePanel);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.getState() == SETTING) {
			int block = getIndex();
			int tile = 0;
			for (int t = 0; t < setButtons.size(); t++) {
				if (e.getSource().equals(setButtons.get(t))) {
					tile = t;
				}
			}
			System.out.println("Clicked tile " + tile);
			mainPanel.set(block, tile);
		}
		
		else if (this.getState() == TURNING) {
			int block = getIndex();
			int direction;
			if (e.getSource().equals(rotateCCW)) {
				direction = ModelUtil.directionToInt("CCW");
			} else {
				direction = ModelUtil.directionToInt("CW");
			}
			System.out.println("Clicked turn " + direction);
			mainPanel.turn(block, direction);
		}
	}

	public void setTiles(Block blockModel) {
		this.blockModel = blockModel;
	}
	
	public void resetHints() {
		if (rotateHint != null) {
			rotateHint.setBorder(defaultBorder);
			rotateHint = null;
		}
	}

	/**
	 * Shows a hint at the given tile. Hint disappears when any action has been taken.
	 * @param tile
	 */
	public void showSetHint(int tile) {
		setHint = setButtons.get(tile);
		setHint.setBorder(hintBorder);
	}
	
	/**
	 * Shows a hint for a given rotation. Hint disappears when any action has been taken.
	 * @param rotation
	 */
	public void showRotateHint(int rotation) {
		if (rotation == Block.CW) {
			rotateHint = rotateCW;
			rotateCW.setBorder(hintBorder);
		} else if (rotation == Block.CCW){
			rotateHint = rotateCCW;
			rotateCCW.setBorder(hintBorder);
		}
	}
	
	/**
	 * Called when this component has to draw its contents
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Rectangle drawingRect = new Rectangle(currentWidth, currentHeight);
		drawingRect.grow(-1, -1);
		
		//Draw the outlines
		int thirdWidth = drawingRect.width/3;
		int thirdHeight = drawingRect.height/3;
		g.setColor(Color.black);
		for (int x = 0; x < Board.DIM; x++) {
			//Draw the columns
			for (int y = 0; y < Board.DIM; y++) {
				//Draw the rows
				g.drawRect(thirdWidth * x, thirdHeight * y, thirdWidth, thirdHeight);
			}
		}
		
		//Set the actual colors
		if (blockModel != null) {
			for (int x = 0; x < Board.DIM; x++) {
				//Draw the columns
				for (int y = 0; y < Board.DIM; y++) {
					//Draw the rows
					int tile = y * 3 + x;
					int tileColor = blockModel.GetTile(tile).getColor();
					Color color = eindopdracht.model.Color.getAwtColor(tileColor);
					g.setColor(color);
					g.fillRect(thirdWidth * x, thirdHeight * y, thirdWidth, thirdHeight);
				}
			}
		}
		
		
		//g.setColor(Color.BLACK);
		//g.drawRect(0, 0, drawingRect.width, drawingRect.height);
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	//Save the actual size when resizing so we always know the accurate size of this object
	@Override
	public void componentResized(ComponentEvent e) {
		this.currentWidth = e.getComponent().getWidth();
		this.currentHeight = e.getComponent().getHeight();
	}

	@Override
	public void componentShown(ComponentEvent e) {}
}
