package eindopdracht.client.model.player;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.ai.AI;
import eindopdracht.ai.IntelligentAI;
import eindopdracht.ai.RandomAI;
import eindopdracht.ai.RecursiveAI;
import eindopdracht.client.model.*;

public class AIPlayer extends Player{
	
	private AI ai;
	private int aiType;
	
	public static int randomAI = 0;
	public static int intelligentAI = 1;
	public static int recursiveAI = 2;
	
	public AIPlayer(int aiType) {
		this.setLocal(true);
		this.aiType = aiType;
	}
	
	public void initializeAI() {
		ArrayList<Player> players = this.getGame().getPlayers();
		ArrayList<Integer> playerColors = new ArrayList<Integer>();
		for (Player player:players) {
			playerColors.add(player.getColor());
		}
		if (aiType == randomAI)
			this.ai = new RandomAI(this.getColor(), this.getGame().getBoard(), playerColors);
		else if (aiType == intelligentAI)
			this.ai = new IntelligentAI(this.getColor(), this.getGame().getBoard(), playerColors);
		else
			this.ai = new RecursiveAI(this.getColor(), this.getGame().getBoard(), playerColors);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass().equals(Set.class)) {
			Set set = (Set)arg;
			if (set.getPlayer().equals(this) && !set.isExecuted() && set.getValid()) {
				//The set is for this player and should still be executed
				ai.calculateSet(set);
				game.set(set);
			}
		}
		
		else if (arg.getClass().equals(Turn.class)) {
			Turn turn = (Turn)arg;
			if (turn.getPlayer().equals(this) && !turn.isExecuted() && turn.getValid()) {
				//The turn is for this player and should still be executed
				ai.calculateTurn(turn);
				game.turn(turn);
			}
		}
	}
}
