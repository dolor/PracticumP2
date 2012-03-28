package eindopdracht.client.model;

import eindopdracht.client.model.player.Player;

public class Set {
	private Player player;
	private int block;
	private int tile;
	private boolean executed;

	boolean valid;

	public Set(Player player) {
		this.player = player;
		this.block = -1;
		this.tile = -1;
		this.valid = false;
		this.executed = false;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getBlock() {
		return this.block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getTile() {
		return this.tile;
	}

	public void setTile(int tile) {
		this.tile = tile;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public boolean isExecuted() {
		return this.executed;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean getValid() {
		return this.valid;
	}

	@Override
	public String toString() {
		return "Player: " + getPlayer().getName() + ", Color:"
				+ getPlayer().getColor() + ", Block: " + getBlock()
				+ ", Tile: " + getTile() + ", Executed: " + isExecuted();
	}
}
