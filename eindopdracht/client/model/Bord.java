package eindopdracht.client.model;

public class Bord {

	public enum turnDirection {
		cw,
		ccw
	}
	
	boolean gameOver;
	Block[] blocks = new Block[9];
	
	/**
	 * 
	 * @return whether or not the game is over
	 */
	public boolean gameOver() {
		return gameOver;
	}
	
	/**
	 * 
	 * @return Which player won the game: 0 - remise, 1-4 - player
	 */
	public int winner() {
		return 0;
	}
	
	
	
	/**
	 * Model class that represents a 3x3 block on the Bord.
	 * @author Dolor
	 *
	 */
	private class Block {
		Tile[] tiles = new Tile[9];
		
		public int getTile(int tile) {
			return tiles[tile].getState();
		}
	}
	
	/**
	 * Model class that represents a single tile on the board.
	 * @author Dolor
	 *
	 */
	private class Tile {
		
		private int state;
		
		/**
		 * 
		 * @return the state of this tile: 0 - no mark, 1-4 - player
		 */
		public int getState() {
			return this.state;
		}
		
		/**
		 * Sets the state of this tile: 0 - no mark, 1-4 - player
		 */
		public void setState(int state) {
			this.state = state;
		}
	}
}
