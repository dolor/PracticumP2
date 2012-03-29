package eindopdracht.ai.intelligent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eindopdracht.model.Board;
import eindopdracht.util.ModelUtil;

public class RecursiveAITest {
	public static void main(String[] args) {
		boolean again = true;
		
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
