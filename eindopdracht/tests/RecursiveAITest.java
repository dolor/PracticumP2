package eindopdracht.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eindopdracht.ai.RecursiveAI;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;
import eindopdracht.model.Position;
import eindopdracht.util.ModelUtil;
import eindopdracht.util.PTLog;

public class RecursiveAITest {
	public static void main(String[] args) {
		boolean again = true;
		
		ArrayList<Integer> testPlayers = new ArrayList<Integer>();
		for (int i = 2; i <= 4; i++) {
			testPlayers.add(i);
		}
		
		while (again) {
			Board testBoard = new Board();
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					int block = ModelUtil.getBlockForPosition(x, y);
					int tile = ModelUtil.getTileForPosition(x, y);
					//Generate a random color 0-4
					int randomColor = (int) (Math.random()*4);
					testBoard.set(block, tile, randomColor);
				}
			}
			
			testBoard.drawBoard();
			
			
			RecursiveAI ai = new RecursiveAI(1, testBoard, testPlayers);
			Set testSet = new Set(new Player());
			ai.calculateSet(testSet);
			PTLog.log("RecursiveAITest", "Best move would be: " + testSet.getBlock() + "," + testSet.getTile());
			
			//Let the AI calculate
			if (RecursiveAITest.readString("Again?").equals("EXIT")) {
				again = false;
			}
		}
	}
	
	/** Leest een regel tekst van standaardinvoer. */
    public static String readString(String tekst) {
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
