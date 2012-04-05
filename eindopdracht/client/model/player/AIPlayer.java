package eindopdracht.client.model.player;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.ai.AI;
import eindopdracht.ai.IntelligentAI;
import eindopdracht.ai.RandomAI;
import eindopdracht.ai.RecursiveAI;
import eindopdracht.ai.RecursiveAI2;
import eindopdracht.client.model.*;
import eindopdracht.model.Command;
import eindopdracht.util.PTLog;
import eindopdracht.util.Protocol;

public class AIPlayer extends Player {

	private AI ai;
	private int aiType;

	public static int randomAI = 0;
	public static int intelligentAI = 1;
	public static int recursiveAI = 2;

	public static int chatChance = 10; // Chance in percentage of chatting

	public AIPlayer(int aiType) {
		this.setLocal(true);
		this.aiType = aiType;
	}

	public void initializeAI() {
		ArrayList<Player> players = this.getGame().getPlayers();
		ArrayList<Integer> playerColors = new ArrayList<Integer>();
		for (Player player : players) {
			playerColors.add(player.getColor());
		}
		if (aiType == randomAI)
			this.ai = new RandomAI(this.getColor(), this.getGame().getBoard(),
					playerColors);
		else if (aiType == intelligentAI)
			this.ai = new IntelligentAI(this.getColor(), this.getGame()
					.getBoard(), playerColors);
		else
			this.ai = new RecursiveAI2(this.getColor(), this.getGame()
					.getBoard(), playerColors);

	}

	/**
	 * Tries to chat. Chance is dependant on the static int chatChance in
	 * AIPlayer.
	 */
	public void tryChat() {
		// Maakt een random nummer 0-100 aan, als hij kleiner of gelijk aan
		// chatChance is chat hij als zijn AI weet hoe.
		if (Math.random() * 100 <= chatChance) {
			String chatLine =  ai.chat();
			if (chatLine != null)
			{
				String chatCommand = "sendchat " + chatLine;
				game.localBroadcast(new Command(chatCommand));
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass().equals(Command.class)) {
			Command command = (Command) arg;
			if (command.getCommand().equals(Protocol.YOUR_TURN)) {
				game.giveSet();
			}
		}

		if (arg.getClass().equals(Set.class)) {
			Set set = (Set) arg;
			if (set.getPlayer().equals(this) && !set.isExecuted()
					&& set.getValid() && !game.getBoard().GameOver()) {
				// The set is for this player and should still be executed
				ai.calculateSet(set);
				game.set(set);
			}
			this.tryChat();
		}

		else if (arg.getClass().equals(Turn.class)) {
			Turn turn = (Turn) arg;
			if (turn.getPlayer().equals(this) && !turn.isExecuted()
					&& turn.getValid() && !game.getBoard().GameOver()) {
				// The turn is for this player and should still be executed
				ai.calculateTurn(turn);
				game.turn(turn);
			}
			this.tryChat();
		}
	}
}
