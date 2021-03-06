package eindopdracht.client.gui.gameboard;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Block;
import eindopdracht.model.Color;

public class BlockPanel extends JPanel implements MouseMotionListener,
		MouseListener, ComponentListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7377956939596338747L;
	private BufferedImage blockImage;
	private BufferedImage highlightImage;

	private BufferedImage redBall;
	private BufferedImage redBallHighlight;
	private BufferedImage blueBall;
	private BufferedImage blueBallHighlight;
	private BufferedImage greenBall;
	private BufferedImage greenBallHighlight;
	private BufferedImage yellowBall;
	private BufferedImage yellowBallHighlight;

	private Image cwImage;
	private Image cwImageHighlight;
	private Image ccwImage;
	private Image ccwImageHighlight;

	private JButton cwButton;
	private JButton ccwButton;

	private ArrayList<Point> ballPositions;
	private ArrayList<Integer> balls;
	private int highlightedBall; // -1 for none, 0-8 for ball
	private BoardPanel bord;

	private int blockIndex;
	private int state;

	public static int DISABLED = 0;
	public static int SETTING = 1;
	public static int TURNING = 2;

	private Dimension previousSize;
	
	private int settingHint = -1;
	private int turningHint = -1;

	/**
	 * Create a new BlockView
	 * 
	 * @param bord
	 *            the bord on which this blockview is
	 * @param blockIndex
	 *            the index on the board of this block
	 */
	public BlockPanel(BoardPanel bord, int blockIndex) {
		this.previousSize = new Dimension();
		this.loadImages();
		this.buildGUI();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addComponentListener(this);
		highlightedBall = -1;
		this.blockIndex = blockIndex;
		this.bord = bord;

		balls = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			balls.add(0);
		}
		
		ballPositions = new ArrayList<Point>();
	}

	/**
	 * Load all images into memory
	 * 
	 * @ensure all necessary images for paintComponents will be non-null
	 */
	private void loadImages() {
		try {
			blockImage = ImageIO.read(new File(
					"eindopdracht/resources/PentagoXL piece.png"));
			highlightImage = ImageIO.read(new File(
					"eindopdracht/resources/Hover.png"));
			redBall = ImageIO.read(new File(
					"eindopdracht/resources/Red Ball.png"));
			blueBall = ImageIO.read(new File(
					"eindopdracht/resources/Blue Ball.png"));
			greenBall = ImageIO.read(new File(
					"eindopdracht/resources/Green Ball.png"));
			yellowBall = ImageIO.read(new File(
					"eindopdracht/resources/Black Ball.png"));

			redBallHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Red Ball Highlight.png"));
			blueBallHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Blue Ball Highlight.png"));
			greenBallHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Green Ball Highlight.png"));
			yellowBallHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Black Ball Highlight.png"));

			cwImage = ImageIO.read(new File(
					"eindopdracht/resources/Turn Right.png"));
			cwImageHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Turn Right Highlight.png"));

			ccwImage = ImageIO.read(new File(
					"eindopdracht/resources/Turn Left.png"));
			ccwImageHighlight = ImageIO.read(new File(
					"eindopdracht/resources/Turn Left Highlight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set up the GUI
	 */
	public void buildGUI() {
		this.setLayout(null);
		
		this.setOpaque(false);

		cwButton = new JButton();
		cwButton.setIcon(new ImageIcon(cwImage));
		cwButton.setOpaque(false);
		cwButton.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 4));
		cwButton.setBorderPainted(false);
		cwButton.setContentAreaFilled(false);
		cwButton.addActionListener(this);
		cwButton.setRolloverEnabled(true);
		cwButton.setRolloverIcon(new ImageIcon(cwImageHighlight));

		ccwButton = new JButton();
		ccwButton.setIcon(new ImageIcon(ccwImage));
		ccwButton.setOpaque(false);
		ccwButton.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 4));
		ccwButton.setBorderPainted(false);
		ccwButton.setContentAreaFilled(false);
		ccwButton.addActionListener(this);
		ccwButton.setRolloverEnabled(true);
		ccwButton.setRolloverIcon(new ImageIcon(ccwImageHighlight));

		this.repaint();
	}

	/**
	 * Set this blocks tiles as the passed ArrayList of integers
	 * 
	 * @param balls
	 *            ArrayList with the colors of each tile as an integer
	 */
	public void setTiles(ArrayList<Integer> balls) {
		this.balls = balls;
		this.repaint();
	}

	/**
	 * Sets the tile to the given color
	 * 
	 * @param tile
	 * @param color
	 * @ensure the UI is updated
	 * @require the tile was empty, as this is not checked in the BlockPanel
	 */
	public void setTile(int tile, int color) {
		balls.set(tile, color);
		Point pos = this.getPositionForBall(tile);
		int size = this.getSizeOfBall();
		Rectangle repaintRectangle = new Rectangle(pos.x - size / 2, pos.y
				- size / 2, size, size);
		this.repaint(repaintRectangle);
	}

	/**
	 * 
	 * @return the arraylist with the colors of each tile as an integer
	 */
	public ArrayList<Integer> getTiles() {
		return this.balls;
	}

	/**
	 * Set the state of this block
	 * 
	 * @param state
	 *            to set this block to
	 */
	public void setState(int state) {
		if (state == TURNING) {
			this.showRotateButtons();
		} else if (this.state == TURNING) {
			this.hideRotateButtons();
		}
		this.state = state;
	}

	/**
	 * 
	 * @return the current state of this block
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Show the rotate buttons on this block
	 */
	public void showRotateButtons() {
		this.add(cwButton);
		this.add(ccwButton);

		if (!previousSize.equals(this.getSize())) {
			previousSize = this.getSize();
			// Update the rotate button icon sizes
			Rectangle cwBounds = new Rectangle(this.getWidth() / 2, 0,
					this.getWidth() / 2, this.getHeight());
			cwButton.setBounds(cwBounds);
			Image newImage = cwImage.getScaledInstance(this.getWidth() / 2,
					this.getHeight(), java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newImage);
			cwButton.setIcon(newIcon);
			newImage = cwImageHighlight.getScaledInstance(this.getWidth() / 2,
					this.getHeight(), java.awt.Image.SCALE_SMOOTH);
			newIcon = new ImageIcon(newImage);
			cwButton.setRolloverIcon(newIcon);

			Rectangle ccwBounds = new Rectangle(0, 0, this.getWidth() / 2,
					this.getHeight());
			ccwButton.setBounds(ccwBounds);

			newImage = ccwImage.getScaledInstance(this.getWidth() / 2,
					this.getHeight(), java.awt.Image.SCALE_SMOOTH);
			newIcon = new ImageIcon(newImage);
			ccwButton.setIcon(newIcon);

			newImage = ccwImageHighlight.getScaledInstance(this.getWidth() / 2,
					this.getHeight(), java.awt.Image.SCALE_SMOOTH);
			newIcon = new ImageIcon(newImage);
			ccwButton.setRolloverIcon(newIcon);
		}
	}

	/**
	 * Hide the rotate buttons on this block
	 */
	public void hideRotateButtons() {
		this.remove(cwButton);
		this.remove(ccwButton);
	}
	
	/**
	 * Shows a hint for a set on the board
	 * @param set with the hint
	 */
	public void showSetHint(Set set) {
		settingHint = set.getTile();
		this.repaint();
	}
	
	/**
	 * Shows a hint for a turn on the board
	 * @param turn with the hint
	 */
	public void showRotateHint(Turn turn) {
		turningHint = turn.getRotation();
		if (turningHint == Block.CW) {
			cwButton.setBorderPainted(true);
		} else if (turningHint == Block.CCW){
			ccwButton.setBorderPainted(true);
		}
	}
	
	public void resetHints() {
		if (settingHint != -1)
		{
			settingHint = -1;
			this.repaint();
		}
		else if (turningHint != -1)
		{
			if (turningHint == Block.CW) {
				cwButton.setBorderPainted(false);
			} else if (turningHint == Block.CCW){
				ccwButton.setBorderPainted(false);
			}
			turningHint = -1;
		}
	}

	/**
	 * 
	 * @param ball
	 *            0-8
	 * @return position within this component
	 */
	private Point getPositionForBall(int ball) {
		double width = this.getWidth();

		// In het originele plaatje gelden deze formaten: blok: 236*236 bal:
		// 52*52 ruimte bal-bal: 13 ruimte bal-rand: 27

		// Eerst berekenen we de verhouding schermgrootte:origineel
		double proportion = width / (double) 236;
		// Het balletje linksboven zal nu op positie (27+(52/2)) * positie
		// zitten, en alle andere balletjes steeds (52+13) * proportion er
		// vanaf.
		int first = (int) (54 * proportion);
		Point point = new Point(first, first);
		int ballCol = ball % 3;
		int ballRow = (int) Math.floor(ball / 3);

		point.x += ballCol * (65 * proportion);
		point.y += ballRow * (65 * proportion);

		return point;
	}

	/**
	 * 
	 * @return the size of one ball using the current UI scale
	 */
	private int getSizeOfBall() {
		double width = this.getWidth();
		double proportion = width / (double) 236;
		return (int) (52 * proportion);
	}

	/**
	 * Update the tiles with the tiles of the given block
	 * 
	 * @param block
	 *            model representation
	 */
	public void updateTiles(Block block) {
		ArrayList<Integer> newTiles = new ArrayList<Integer>();
		for (int t = 0; t < 9; t++) {
			newTiles.add(block.getTile(t).getColor());
		}
		this.setTiles(newTiles);
	}

	/**
	 * Called when this component has to draw its contents
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(blockImage, 0, 0, this.getSize().width,
				this.getSize().height, null);

		// Draw the highlights if setting
		if (highlightedBall >= 0 && state == SETTING) {
			Point highlightPos = this.getPositionForBall(highlightedBall);
			int hx = highlightPos.x;
			int hy = highlightPos.y;
			int size = getSizeOfBall();
			g.drawImage(highlightImage, hx - size / 2, hy - size / 2, size,
					size, null);
		}

		// Draw the rotation thingies if turning

		// Draw the balls
		for (int i = 0; i < balls.size(); i++) {
			if (balls.get(i) > 0) {
				// Tile was set
				Point p = this.getPositionForBall(i);
				int size = getSizeOfBall();
				BufferedImage ballImage;
				switch (balls.get(i)) {
				case Color.RED:
					if (highlightedBall != i)
						ballImage = redBall;
					else
						ballImage = redBallHighlight;
					break;
				case Color.BLUE:
					if (highlightedBall != i)
						ballImage = blueBall;
					else
						ballImage = blueBallHighlight;
					break;
				case Color.GREEN:
					if (highlightedBall != i)
						ballImage = greenBall;
					else
						ballImage = greenBallHighlight;
					break;
				case Color.YELLOW:
					if (highlightedBall != i)
						ballImage = yellowBall;
					else
						ballImage = yellowBallHighlight;
					break;
				default:
					ballImage = redBall;
					break;
				}
				g.drawImage(ballImage, p.x - size / 2, p.y - size / 2, size,
						size, null);
			}
		}
		
		//If necessary, paint the set hint
		if (settingHint != -1)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(java.awt.Color.RED);
			g2.setStroke(new BasicStroke(4));
			int bs = this.getSizeOfBall();
			g2.drawOval(this.getPositionForBall(settingHint).x - bs/2, this.getPositionForBall(settingHint).y - bs/2, bs, bs);
		}
	}

	@Override
	/**
	 * Mouse moved, so check if it is now highlighting a ball
	 */
	public void mouseMoved(MouseEvent arg0) {
		if (highlightedBall >= 0) {
			// A ball was already highlighted, check if this is still the case
			Point hp = this.getPositionForBall(highlightedBall);
			double d = hp.distance(arg0.getPoint());
			if (d > getSizeOfBall() / 2) {
				highlightedBall = -1;
				this.repaint();
			}
			// > getSizeOfBall()/2)?"":"NOT") + " within range");
		}

		if (highlightedBall < 0) {
			// No ball selected. Can't be an else statement because it might
			// have changed in the first if.
			for (Point p : ballPositions) {
				double d = p.distance(arg0.getPoint());
				if (d < getSizeOfBall() / 2) {
					highlightedBall = ballPositions.indexOf(p);
					this.repaint();
				}
			}
		}
	}

	@Override
	/**
	 * Should update all the values to the new size
	 */
	public void componentResized(ComponentEvent e) {
		if (e.getSource().equals(this)) {
			ballPositions = new ArrayList<Point>();
			for (int i = 0; i <= 8; i++) {
				ballPositions.add(this.getPositionForBall(i));
			}
		}
		this.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		previousSize = this.getSize();
		ballPositions = new ArrayList<Point>();
		for (int i = 0; i <= 8; i++) {
			ballPositions.add(this.getPositionForBall(i));
		}
	}

	@Override
	/**
	 * Manual mouse click registration is necessary for the tiles
	 */
	public void mouseClicked(MouseEvent arg0) {
		if (state == SETTING) {
			// Was setting
			if (highlightedBall >= 0 && balls.get(highlightedBall) == 0) {
				bord.set(blockIndex, highlightedBall);
			}
		}
	}

	@Override
	/**
	 * A turn button was clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cwButton)) {
			bord.turn(blockIndex, Block.CW);
		} else if (e.getSource().equals(ccwButton)) {
			bord.turn(blockIndex, Block.CCW);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
