package eindopdracht.model.player;

import eindopdracht.model.Set;
import eindopdracht.model.Turn;

public class NetworkPlayer extends Player {
	public void performSet(Set set) {
		this.game.set(set);
	}
	
	public void performTurn(Turn turn) {
		this.game.turn(turn);
	}
}
