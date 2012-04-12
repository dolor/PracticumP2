package eindopdracht.tests;

import java.util.ArrayList;

import eindopdracht.ai.RandomAI;
import eindopdracht.ai.RecursiveAI2;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.model.Color;

public class TestAI {
	public static final int RECURSION_DEPTH = 3;
	public static void main(String[] args)
	{
		int numberOfLoops = Integer.parseInt(args[0]);
		
		ArrayList<Long> zetDuur = new ArrayList<Long>();
		ArrayList<Long> spelDuur = new ArrayList<Long>();
		int recursiveWin = 0;
		int randomWin = 0;
		for (int i = 0; i <= numberOfLoops; i++)
		{
			long beginTijdSpel = System.currentTimeMillis();
			
			Board board = new Board();
			ArrayList<Integer> players = new ArrayList<Integer>();
			players.add(Color.RED);
			players.add(Color.BLUE);
			
			printLN(players.toString());
			
			
			RecursiveAI2 recursive = new RecursiveAI2(Color.RED, board, players, RECURSION_DEPTH);
			RandomAI random = new RandomAI(Color.BLUE, board, players);
			
			while(!board.gameOver())
			{
				long beginTijdZet = System.currentTimeMillis();
				Set s = new Set(null);
				Turn t = new Turn(null);
				
				// recursive set
				recursive.calculateSet(s);
				recursive.calculateTurn(t);
				
				board.set(s.getBlock(), s.getTile(), recursive.getColor());
				board.turn(t.getBlock(), t.getRotation());
				
				if (!board.gameOver())
				{
					random.calculateSet(s);
					random.calculateTurn(t);
					
					board.set(s.getBlock(), s.getTile(), recursive.getColor());
					board.turn(t.getBlock(), t.getRotation());
					
				}
				long eindTijdZet = System.currentTimeMillis();
				zetDuur.add(eindTijdZet - beginTijdZet);
			}
			
			printLN ("Spel gespeeld");
			
			if (board.getWinners().contains(recursive.getColor()))
			{
				recursiveWin++;
				printLN("Winnaar: recursiveAI");
			}
			else
			{
				randomWin++;
				printLN("Winnaar: randomAI");
			}
			
			long eindTijdSpel = System.currentTimeMillis();
			printLN("Spel duurde: "+(eindTijdSpel- beginTijdSpel)+" ms");
			System.out.println("");
			spelDuur.add(eindTijdSpel - beginTijdSpel);
			
		}
		
		long totaalTijd = 0;
		
		for (Long tijd : spelDuur)
		{
			totaalTijd += tijd;
		}
		
		printLN("Test afgelopen");
		printLN(numberOfLoops+" spellen gespeeld.");
		printLN("RecursiveAI heeft "+recursiveWin+" keer gewonnen.");
		printLN("RandomAI heeft "+randomWin+" keer gewonnen.");
		printLN("De gemiddelde spelduur was "+ (totaalTijd/numberOfLoops));
		printLN("De totale spelduur was "+totaalTijd);

	}
	public static void printLN(String line)
	{
		System.out.println(line);
	}

}
