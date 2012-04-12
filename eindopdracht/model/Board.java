package eindopdracht.model;

import java.util.ArrayList;
import java.util.TreeMap;

import eindopdracht.model.Tile;
import eindopdracht.util.PTLog;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Board {
	public static final int DIM = 3;
	protected String stringValue;
	TreeMap<String, ArrayList<Integer>> hashMap;

	Block[] blocks;

	public Board() {
		blocks = new Block[DIM * DIM];

		for (int i = 0; i <= Board.DIM * Board.DIM - 1; i++) {
			blocks[i] = new Block();
		}
		this.hashMap = new TreeMap<String, ArrayList<Integer>>();
		this.calculateHash();
	}

	/**
	 * Perform the set on this board
	 * @param block the block on which the tile is located
	 * @param tile the tile on which the set has to be preformed
	 * @param color the color which has to be set on the tile
	 * @force force the set
	 * @return true if succesfull, false if invalid
	 */
	public boolean set(int block, int tile, int color, boolean force) {
		// System.out.printf("Block:"+block+" Tile:"+tile+" Color:"+color+" \n");
		if (!(block >= 0 && block <= 8 && tile >= 0 && tile <= 8)) {
			return false;
		} else {

			boolean valid = blocks[block].getTile(tile).setColor(color, force);
			this.calculateHash();
			return valid;
		}
	}
	
	public boolean set(int block, int tile, int color)
	{
		boolean valid = set(block, tile, color, false);
		return valid;
	}
	/**
	 * Turn a block on the board
	 * @param block block that has to be returned
	 * @param rotation the rotation direction CW / CCW
	 * @return
	 */

	public boolean turn(int block, int rotation) {
		if (!(block >= 0 && block <= 8 && rotation >= 1 && rotation <= 2)) {
			return false;
		} else {
			boolean valid = blocks[block].Turn(rotation);
			this.calculateHash();
			return valid;
		}
	}

	public Block[] getBlocks() {
		return this.blocks;
	}

	/**
	 * Gives a tile based on X and Y positions not block and tile
	 * @param x
	 * @param y
	 * @return correct tile based on X and Y positions
	 */

	public Tile getTileXY(int x, int y) {
		Position pos = new Position(x, y);

		return blocks[pos.getBlock()].getTile(pos.getTile());

	}

	/**
	 * Gives a list of colors of the winners (if any).
	 * @return List of winners
	 */
	public ArrayList<Integer> getWinners() {
		if (!hashMap.containsKey(this.getHash())) {
			// ga alle rijen af
			ArrayList<Integer> winners = new ArrayList<Integer>();

			for (Row r : getRows()) {
				if (r.getLength() >= 5) {
					//PTLog.log("Board", "Found a winner: " + r.getColor());
					
					winners.add(r.getColor());
				}
			}
			hashMap.put(this.getHash(), winners);
			return winners;
		} else {
			return (hashMap.get(this.getHash()));
		}
	}
	/**
	 * Gives all the "rows" on the board. Rows are defined as a sequence of tiles with the same color
	 * @return al rows on the board
	 */
	public ArrayList<Row> getRows() {
		ArrayList<Row> rows = new ArrayList<Row>();

		// horizontaal
		for (int y = 0; y <= 8; y++) {
			int lastcolor = -1;
			Row r = new Row();

			for (int x = 0; x <= 8; x++) {
				int color = this.getTileXY(x, y).getColor();
				if (color != lastcolor && color != Color.EMPTY) {
					// niet de laaste kleur dus nieuwe rij
					r = new Row();
					rows.add(r);

					// voeg het toe aan de rij
					r.addPosition(new Position(x, y));
					r.setColor(color);
					
				} else if (lastcolor == color && color != Color.EMPTY) 
				{
					// zelfde kleur dus rij wordt een langer
					r.addPosition(new Position(x, y));
				} else if (color == Color.EMPTY ) 
				{
					// einde van een rij gevonden dus sluit hem flink af
					r = new Row();
				}
				lastcolor = color;
			}
		}
		
		// verticaal
		for (int x = 0; x <= 8; x++) {
			int lastcolor = -1;
			Row r = new Row();

			for (int y = 0; y <= 8; y++) {
				int color = this.getTileXY(x, y).getColor();
				if (color != lastcolor && color != Color.EMPTY) {
					// niet de laaste kleur dus nieuwe rij
					r = new Row();
					rows.add(r);

					// voeg het toe aan de rij
					r.addPosition(new Position(x, y));
					r.setColor(color);
					lastcolor = color;
				} else if (lastcolor == color && color != Color.EMPTY) {
					// zelfde kleur dus rij wordt een langer
					r.addPosition(new Position(x, y));
				} else if (color == Color.EMPTY && r.getLength() > Color.EMPTY) {
					//einde van een rij gevonden dus sluit hem voor de zekerheid af
					lastcolor = 0;
					r = new Row();
				}
				lastcolor = color;
			}
		}

		// check diagonal
		// ga alle diagonalen af
		// voor links boven naar rechts onder
		for (int dY = -7; dY <= 8; dY++) {
			int lastcolor = -1;
			Row r = new Row();
			for (int x = 0; x <= 8; x++) {
				int y = dY - x;

				if (0 <= y && y <= 8) // kijk of y binnen de range valt.
				{
					int color = this.getTileXY(x, y).getColor();
					if (color != lastcolor && color != Color.EMPTY) {
						// niet de laaste kleur dus nieuwe rij
						r = new Row();
						rows.add(r);

						// voeg het toe aan de rij
						r.addPosition(new Position(x, y));
						r.setColor(color);
						lastcolor = color;
					} else if (lastcolor == color && color != Color.EMPTY) {
						// zelfde kleur dus rij wordt een langer
						r.addPosition(new Position(x, y));
					} else if (color == Color.EMPTY && r.getLength() > Color.EMPTY) {
						//einde van een rij gevonden dus sluit hem voor de zekerheid af
						lastcolor = 0;
						r = new Row();
					}
					lastcolor = color;
				}

			}
		}

		// voor rechts boven naar links onder
		for (int dY = 0; dY <= 16; dY++) {
			int lastcolor = -1;
			Row r = new Row();
			for (int x = 0; x <= 8; x++) {
				int y = dY + x; // bereken de y behorend bij de x

				if (0 <= y && y <= 8) // kijk of y binnen de range valt.
				{
					int color = this.getTileXY(x, y).getColor();
					if (color != lastcolor && color != 0) {
						// niet de laaste kleur dus nieuwe rij
						r = new Row();
						rows.add(r);

						// voeg het toe aan de rij
						r.addPosition(new Position(x, y));
						r.setColor(color);
					} else if (lastcolor == color && color != Color.EMPTY) {
						// zelfde kleur dus rij wordt een langer
						r.addPosition(new Position(x, y));
					}
					else if (color == Color.EMPTY && r.getLength() > Color.EMPTY) {
						//einde van een rij gevonden dus sluit hem voor de zekerheid af
						lastcolor = 0;
						r = new Row();
					}
					lastcolor = color;
				}
				
			}
		}

		
		ArrayList<Row> returnRows = new ArrayList<Row>();
		
		for (Row r : rows)
		{
			if (r.getLength() > 1)
			{
				returnRows.add(r);
			}
		}
		
		return returnRows;

	}

	/**
	 * Set a block
	 * @param b the block
	 * @param block the position of the block
	 * @require b != null
	 * @ensure this.getBlock() == b
	 */
	public void setBlock(Block b, int block) {
		this.blocks[block] = b;
	}

	public Block getBlock(int block) {
		return this.blocks[block];
	}

	/**
	 * Is the game over.
	 * @ensure getWinners.size() > 0 => !gameOver()
	 * @return true if game is over, false if games is not yet over
	 */
	public boolean gameOver() {
		return !getWinners().isEmpty(); // als de lijst niet leeg is is er een
										// winner en dus game over
	}

	/**
	 * Prints the board to the commandline
	 */
	public void drawBoard() {
		for (int y = 0; y <= 8; y++) {
			if (y % 3 == 0) {
				System.out.println("-----------------------");
			}
			for (int x = 0; x <= 8; x++) {
				if (x % 3 == 0) {
					System.out.print("||");
				} else {
					System.out.print("|");
				}
				System.out.print(this.getTileXY(x, y).getColor());
			}

			System.out.print("||");
			System.out.println();

		}
	}

	/**
	 * Gives a copy of the board
	 * @ensure this.getTile(n) == deepCopy.getTile(n)
	 * @ensure this.getBlock(n) == deepCopy.getBlock(n)
	 * @return copy of this board
	 */
	public Board deepCopy() {
		Board b = new Board();
		for (int i = 0; i <= 8; i++) {
			b.setBlock(this.getBlock(i).deepCopy(), i);
		}

		return b;
	}
	
	/**
	 * Calculates an hash value for the board that can be used to cache it.
	 * @ensure an unique hash value is stored in stringValue.
	 */
	public void calculateHash() {
		Position pos = new Position(0, 0);
		StringBuffer newValue = new StringBuffer();
		for (int y = 0; y < DIM * DIM; y++) {
			for (int x = 0; x < DIM * DIM; x++) {
				pos.x = x;
				pos.y = y;
				
				newValue.append(blocks[pos.getBlock()].getTile(pos.getTile()).getColor());
			}
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {System.out.println("ERROR NO SUCH ALGORITHM");}
		byte[] hashBytes = md.digest(newValue.toString().getBytes());
		BigInteger bigInt = new BigInteger(1,hashBytes);
		String hashtext = bigInt.toString(16);
		stringValue = hashtext;
	}
	
	/**
	 * Returns a calculated hash of the boards state
	 * @return the hash code of the current board state
	 * @ensure the code is always unique for any given board state
	 */
	public String getHash() {
		return stringValue;
	}
	
	@Override
	public String toString() {
		System.out.println("StringValue: " + stringValue);
		return stringValue;
	}
}
