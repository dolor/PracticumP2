package eindopdracht.tests;
import eindopdracht.model.*;

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
			
			for (int winner : b.GetWinners())
			{
				System.out.println("Winner : "+winner);
			}
			
			System.out.print("> ");
			Scanner in = new Scanner(System.in);

			// Reads a single line from the console 
			// and stores into name variable
			String input = in.nextLine();
			
			String[] argumenten =  input.split(" ");
			
			try
			{
				if (argumenten[0].equals("s"))
				{
					int x = Integer.parseInt(argumenten[1]);
					int y = Integer.parseInt(argumenten[2]);
					int color = Integer.parseInt(argumenten[3]);
					
					b.getTileXY(x, y).setColor(color, true);
				}
				else if (argumenten[0].equals("t"))
				{
					int block = Integer.parseInt(argumenten[1]);
					int rotation = Integer.parseInt(argumenten[2]);
					
					b.turn(block, rotation);
				}
				
			}
			catch (NumberFormatException e)
			{
				System.out.println("Error in converting to int");
			}		
			
			
			
		}
		
		
	}

}
