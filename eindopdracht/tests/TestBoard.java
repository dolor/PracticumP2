package eindopdracht.tests;
import eindopdracht.model.*;
import eindopdracht.util.PTLog;

import java.util.Scanner;
public class TestBoard 
{
	
	public static void main (String[] args)
	{
		boolean doorgaan = true;
		
		Board b = new Board();

		while (doorgaan)
		{
			b.drawBoard();

			for (Row r : b.getRows())
			{
				System.out.println("Rij: " + r.getLength());
			}
			
			
			
			System.out.print("> ");
			Scanner in = new Scanner(System.in);

			// Reads a single line from the console 
			// and stores into name variable
			String input = in.nextLine();
			
			String[] argumenten =  input.split(" ");
			
			if (argumenten.length == 4) {
				try
				{
					if (argumenten[0].equals("s"))
					{
						int x = Integer.parseInt(argumenten[1]);
						int y = Integer.parseInt(argumenten[2]);
						int color = Integer.parseInt(argumenten[3]);
						
						b.getTileXY(x, y).setColor(color, true);
						b.calculateHash();
						System.out.println(b.getHash());
					}

					
				}
				catch (NumberFormatException e)
				{
					PTLog.log("TestBoard", "Error in converting to int");
				}	
				
			} 
			else if (argumenten.length == 3)
			{
				try
				{

					if (argumenten[0].equals("t"))
					{
						int block = Integer.parseInt(argumenten[1]);
						int rotation = Integer.parseInt(argumenten[2]);
						
						b.turn(block, rotation);
						b.calculateHash();
						System.out.println(b.toString());
					}
					
				}
				catch (NumberFormatException e)
				{
					PTLog.log("TestBoard", "Error in converting to int");
				}	
				
			}
			
			else{
				PTLog.log("BoardTest", "Usage: s <x> <y> <colo>");
				PTLog.log("BoardTest", "Usage: t <block> <rotation>");
			}
			
			
			if (b.getWinners().size() > 0) {
				for (int winner : b.getWinners())
				{
					PTLog.log("TestBoard", "Winner : "+winner);
				}
			}
		}
	}

}
