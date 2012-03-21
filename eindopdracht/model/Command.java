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
		//Add all the arguments
		while (scanner.hasNext()) {
			args = new String[args.length + 1];
			args[i] = scanner.next();
			i++;
		}
		
		this.command = comm;
		this.args = args;
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