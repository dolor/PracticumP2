package eindopdracht.client.model.player;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;

public class NetworkPlayer extends Player {
	public void performSet(Set set) {
		this.game.set(set);
	}
	
	public void performTurn(Turn turn) {
		this.game.turn(turn);
	}
}
