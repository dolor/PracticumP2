package eindopdracht.client.model.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.ai.AI;
import eindopdracht.ai.RecursiveAI2;
import eindopdracht.client.gui.gameboard.BoardPanel;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Command;
import eindopdracht.util.PTLog;
import eindopdracht.util.Protocol;

public class HumanPlayer extends Player{
	
	private AI hintAI;
	private BoardPanel bordGUI;
	private int aiDepth;
	
	public HumanPlayer(int aiDepth) {
		this.setLocal(true);
		this.aiDepth = aiDepth;
	}
	
	/**
	 * Should be called after the game has started, so the human player can create his hint AI
	 */
	public void createHintAI() {
		ArrayList<Player> players = this.getGame().getPlayers();
		ArrayList<Integer> playerColors = new ArrayList<Integer>();
		for (Player player:players) {
			playerColors.add(player.getColor());
		}
		this.hintAI = new RecursiveAI2(this.getColor(), this.getGame().getBoard(), playerColors, aiDepth);
	}
	
	/**
	 * Give the human player a reference to the bord GUI, to request hints
	 * @param bord
	 */
	public void setBordPanel(BoardPanel bord) {
		this.bordGUI = bord;
	}
	
	public BoardPanel getBordPanel() {
		return this.bordGUI;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass().equals(Command.class)) {
			Command command = (Command)arg;
			if (command.getCommand().equals(Protocol.YOUR_TURN)) {
				PTLog.log(name, "Now taking the TURN");
				game.giveSet();
			}
		}
	}
	
	/**
	 * Ask his hint-AI to tell what he would do
	 */
	public void requestHint() {
		PTLog.log(name, "Requesting a hint");
		if (this.getState() == Player.SETTING) {
			Set set = new Set(this);
			hintAI.calculateSet(set);
			bordGUI.showSetHint(set);
		} else if (this.getState() == Player.TURNING) {
			Turn turn = new Turn(this);
			hintAI.calculateTurn(turn);
			bordGUI.showRotateHint(turn);
		}
	}
	
	/**
	 * Make a set based on the given set and return it to its game
	 * @param set
	 */
	public void makeSet(Set set) {
		int block = -1;
		int tile = -1;
		while (!(block >= 0 && block <= 8)) {
			System.out.println("What block do you want to set? (0-8)");
			try {
				block = Integer.parseInt(this.readString(">"));
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!");
			}
		}
		
		while (!(tile >= 0 && tile <= 8)) {
			System.out.println("What tile do you want to set? (0-8)");
			try {
				tile = Integer.parseInt(this.readString(">"));
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!");
			}
		}
		set.setBlock(block);
		set.setTile(tile);
		game.set(set);
	}
	
	/**
	 * Make a turn based on the given turn and return it to its game
	 * @param turn
	 */
	public void makeTurn(Turn turn) {
		int block = -1;
		int direction = -1;
		while (!(block >= 0 && block <= 8)) {
			System.out.println("What block do you want to turn? (0-8)");
			try {
				block = Integer.parseInt(this.readString(">"));
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!");
			}
		}
		
		while (!(direction == 1 || direction == 2)) {
			System.out.println("Which direction do you want to turn? (1/2)");
			try {
				direction = Integer.parseInt(this.readString(">"));
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!");
			}
		}
		turn.setBlock(block);
		turn.setRotation(direction);
		game.turn(turn);
	}
	
	/** Leest een regel tekst van standaardinvoer. */
    public String readString(String tekst) {
        System.out.print(tekst);
        String antw = null;
        try {
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(System.in));            
            antw = in.readLine();
        } catch (IOException e) {}

        return (antw == null) ? "" : antw;
    }
}
