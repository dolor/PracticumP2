package eindopdracht.model;

import java.util.ArrayList;
import eindopdracht.model.Tile;
import eindopdracht.util.PTLog;

public class Board {
	public static final int DIM = 3;

	Block[] blocks;

	public Board() {
		blocks = new Block[DIM * DIM];

		for (int i = 0; i <= Board.DIM * Board.DIM - 1; i++) {
			blocks[i] = new Block();
		}
	}

	/**
	 * Perform the set on this board
	 * 
	 * @return true if succesful, false if invalid
	 */
	public boolean set(int block, int tile, int color) {
		// System.out.printf("Block:"+block+" Tile:"+tile+" Color:"+color+" \n");
		if (!(block >= 0 && block <= 8 && tile >= 0 && tile <= 8)) {
			return false;
		} else {
			return blocks[block].getTile(tile).SetColor(color);
		}
	}

	public boolean turn(int block, int rotation) {
		if (!(block >= 0 && block <= 8 && rotation >= 1 && rotation <= 2)) {
			return false;
		} else {
			return blocks[block].Turn(rotation);
		}
	}

	public Block[] getBlocks() {
		return this.blocks;
	}

	/**
	 * Kijkt naar het board als geheel met een x van 0 8 en een y van 0 tot 8
	 * 0,0 is bovenin
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	public Tile getTileXY(int x, int y) {
		/*
		 * // kijk welk block het is int BlockCol = (int) Math.floor(x / 3); int
		 * BlockRow = (int) Math.floor(y / 3);
		 * 
		 * int TileCol = x % 3; int TileRow = y % 3; //TODO: Testen of dit klopt
		 * return blocks[BlockRow * 3 + BlockCol].getTile(TileRow*3 + TileCol);
		 */

		Position pos = new Position(x, y);

		return blocks[pos.getBlock()].getTile(pos.getTile());

	}

	/**
	 * board weet alleen of er 5 op een rij is of niet. Het aantal knikkers
	 * controlleert game
	 * 
	 * @return
	 */
	public ArrayList<Integer> GetWinners() {
		// ga alle rijen af
		ArrayList<Integer> winners = new ArrayList<Integer>();

		/*
		 * TODO: Als we klaar zijn weghalen: // check cols /*for (int y = 0; y
		 * <= 8; y++) { int count = 0; int lastcolor = 0;
		 * 
		 * for (int x = 0; x <= 8; x++) {
		 * 
		 * // haal de kleur van de tegel op int color =
		 * getTileXY(x,y).getColor();
		 * 
		 * // kijk of die dezelfde kleur is if (color == lastcolor) { count +=
		 * 1; // doe de count +1 } else { count = 1; }
		 * 
		 * if (count >= 5 && !winners.contains(color)) // 5 op een rij {
		 * winners.add(color); } lastcolor = color; }
		 * 
		 * } // check rows for (int x = 0; x <= 8; x++) { int count = 0; int
		 * lastcolor = 0;
		 * 
		 * for (int y = 0; y <= 8; y++) {
		 * 
		 * // haal de kleur van de tegel op int color =
		 * getTileXY(x,y).getColor();
		 * 
		 * // kijk of die dezelfde kleur is if (color == lastcolor) { count +=
		 * 1; // doe de count +1 } else { count = 1; }
		 * 
		 * if (count >= 5 && !winners.contains(color)) // 5 op een rij {
		 * winners.add(color); } lastcolor = color; } }
		 * 
		 * // check diagonal // ga alle diagonalen af // voor links boven naar
		 * rechts onder for (int dY = -7; dY <= 8; dY++) { int count = 0; int
		 * lastcolor = 0; for (int x = 0; x <= 8; x++) { int y = dY - x;
		 * 
		 * if (0 <= y && y <= 8) // kijk of y binnen de range valt. { // haal de
		 * kleur van de tegel op int color = getTileXY(x,y).getColor();
		 * 
		 * // kijk of die dezelfde kleur is if (color == lastcolor) { count +=
		 * 1; // doe de count +1 } else { count = 1; // het is de eerste keer
		 * dus = 1 }
		 * 
		 * if (count >= 5 && !winners.contains(color)) // 5 op een rij {
		 * winners.add(color); } lastcolor = color; }
		 * 
		 * } }
		 * 
		 * // voor rechts boven naar links onder for (int dY = 0; dY <= 16;
		 * dY++) { int count = 0; int lastcolor = 0; for (int x = 0; x <= 8;
		 * x++) { int y = dY + x; // bereken de y behorend bij de x
		 * 
		 * if (0 <= y && y <= 8) // kijk of y binnen de range valt. { // haal de
		 * kleur van de tegel op int color = getTileXY(x,y).getColor();
		 * 
		 * // kijk of die dezelfde kleur is if (color == lastcolor) { count +=
		 * 1; // doe de count +1 } else { count = 1; // het is de eerste keer
		 * dus = 1 }
		 * 
		 * if (count >= 5 && !winners.contains(color)) // 5 op een rij {
		 * winners.add(color); } lastcolor = color; }
		 * 
		 * } }
		 */

		for (Row r : getRows()) {
			if (r.getLength() >= 5) {
				PTLog.log("Board", "Found a winner: " + r.getColor());
				System.out.println(r.toString());
				winners.add(r.getColor());
			}
		}

		return winners;
	}

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
					lastcolor = color;
					
				} else if (lastcolor == color && color != Color.EMPTY) {
					// zelfde kleur dus rij wordt een langer
					r.addPosition(new Position(x, y));
				} else if (color == Color.EMPTY && r.getLength() > 0) {
					// einde van een rij gevonden dus sluit hem flink af
					lastcolor = 0;
					r = new Row();
				}
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
						lastcolor = color;
					} else if (lastcolor == color) {
						// zelfde kleur dus rij wordt een langer
						r.addPosition(new Position(x, y));
					}
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

	public void setBlock(Block b, int block) {
		this.blocks[block] = b;
	}

	public Block getBlock(int block) {
		return this.blocks[block];
	}

	public boolean GameOver() {
		return !GetWinners().isEmpty(); // als de lijst niet leeg is is er een
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

	public Board deepCopy() {
		Board b = new Board();
		for (int i = 0; i <= 8; i++) {
			b.setBlock(this.getBlock(i).deepCopy(), i);
		}

		return b;
	}
}
