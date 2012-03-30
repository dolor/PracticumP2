package eindopdracht.util;

/**
 * @author Dolor
 * PenTago XL log. Used for making log messages
 */
public class PTLog {
	
	/**
	 * Makes a log message in the console
	 * @param name of the instance sending this log
	 * @param message the message for the log
	 */
	public static void log(String name, String message) {
		System.out.printf("%-20s %s\n", "<" + name + "> " ,message);
	}
	
}
