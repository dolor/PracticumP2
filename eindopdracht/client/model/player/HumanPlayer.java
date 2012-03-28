package eindopdracht.client.model.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.ai.AI;
import eindopdracht.ai.IntelligentAI;
import eindopdracht.ai.RandomAI;
import eindopdracht.client.gui.gameboard.BordPanel;
import eindopdracht.client.gui.gameboard.FancyBordPanel;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.model.Command;

public class HumanPlayer extends Player{
	
	private AI hintAI;
	private FancyBordPanel bordGUI;
	
	public HumanPlayer() {
		this.setLocal(true);
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
		this.hintAI = new IntelligentAI(this.getColor(), this.getGame().getBoard(), playerColors);
	}
	
	/**
	 * Give the human player a reference to the bord GUI, to request hints
	 * @param bord
	 */
	public void setBordPanel(FancyBordPanel bord) {
		this.bordGUI = bord;
	}
	
	public FancyBordPanel getBordPanel() {
		return this.bordGUI;
	}

	@Override
	public void update(Observable o, Object arg) {
		/*if (arg.getClass().equals(Set.class) && ((Set)arg).getPlayer().equals(this)) {
			this.makeSet((Set)arg);
		}
		
		else if (arg.getClass().equals(Turn.class) && ((Turn)arg).getPlayer().equals(this)) {
			this.makeTurn((Turn)arg);
		}*/
	}
	
	/**
	 * Ask his hint-AI to tell what he would do
	 */
	public void requestHint() {
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
