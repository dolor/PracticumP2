package eindopdracht.model;

import java.util.Scanner;

import eindopdracht.util.PTLog;

/**
 * @author Dolor
 * Used to store Commands
 */
public class Command {
	
	//TODO: Pre-Post conditions
	private String command;
	private String[] args;
	private String argString;
	
	/**
	 * Creates a new Command with the given command and arguments
	 * @require command != null
	 * @param command
	 * @param args
	 * @ensure Command contains the command and all the args
	 */
	public Command(String command, String[] args) {
		this.command = command;
		this.args = args;
	}
	
	/**
	 * Parses a string into a command
	 * @param fullCommand
	 * @require string != null
	 * @ensure Command contains the command and all following words as args
	 */
	public Command(String fullCommand) {
		Scanner scanner = new Scanner(fullCommand); 
		if (!scanner.hasNext())
		{
			PTLog.log("Command", "Empty command received!");
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
			this.argString = this.argString + args[i] + " ";
			i++;
		}
		if (argString != null)
			argString.trim();
				
		this.command = comm;
		this.args = args;
	}
	
	/**
	 * @return the command without the arguments
	 * @ensure return != null
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
	 * @ensure return = null if out of bounds
	 */
	public String getArg(int arg) {
		if (arg < args.length)
			return args[arg];
		else
			return null;
	}
	
	/**
	 * Returns all arguments as a string
	 * @return all arguments as a string
	 * @ensure return != null
	 */
	public String getArgString() {
		return argString;
	}
	
	/**
	 * Checks if the command is valid; A command is valid if it has a command string.
	 * @return if the command is valid
	 * @ensure true if this.getCommand()!=null && !this.getCommand().isEmpty()
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