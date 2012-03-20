package eindopdracht.client.model;

public class Command {
	
	private String command;
	private String[] args;
	
	public Command(String command, String[] args) {
		this.command = command;
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
	
	@Override
	public String toString() {
		String string = command;
		for (int i = 0; i < args.length; i++)
			string = string + " " + args[i];
		return string;
	}
}