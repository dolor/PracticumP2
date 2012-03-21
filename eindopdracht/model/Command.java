package eindopdracht.model;

import java.util.Scanner;

public class Command {
	
	private String command;
	private String[] args;
	
	public Command(String command, String[] args) {
		this.command = command;
		this.args = args;
	}
	
	/**
	 * Parses a string into a command
	 * @param fullCommand
	 */
	public Command(String fullCommand) {
		Scanner scanner = new Scanner(fullCommand); 
		
		if (!scanner.hasNext())
		{
			System.out.println("Empty command received!");
			System.exit(0);
		}
		String comm = scanner.next();
		String[] args = new String[0];
		int i = 0;
		System.out.println(comm);
		//Add all the arguments
		while (scanner.hasNext()) {
			args = new String[args.length + 1];
			if (this.args != null)
				System.arraycopy(this.args, 0, args, 0, this.args.length);
			args[i] = scanner.next();
			this.args = args;
			i++;
		}
				
		this.command = comm;
		this.args = args;
		for (int a = 0; a < args.length; a++)
			System.out.println(args[a] + " | " + this.args[a]);
	}
	
	/**
	 * @return the command without the arguments
	 * 
	 */
	public String getCommand() {
		return this.command;
	}
	
	/**
	 * @return the arguments
	 */
	public String[] getArgs() {
		return this.args;
	}
	
	/**
	 * Returns the argument at position arg. Returns null if out of bounds.
	 * @param arg
	 * @return
	 */
	public String getArg(int arg) {
		if (arg < args.length)
			return args[arg];
		else
			return null;
	}
	
	/**
	 * Checks if the command is valid; A command is valid if it has a command string.
	 * @return
	 */
	public boolean isValid() {
		return (this.command != null && !this.command.isEmpty());
	}
	
	@Override
	public String toString() {
		String string = command;
		for (int i = 0; i < args.length; i++)
			string = string + " " + args[i];
		return string;
	}
}