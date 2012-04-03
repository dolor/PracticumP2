package eindopdracht.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtil {
	/**
	 * Whether or not the host is a valid hostname or IP address.
	 * 
	 * @param host
	 * @ensure returns true if host is valid, false if it is not
	 */
	public static boolean isValidHost(String host) {
		if (host.toLowerCase().equals("localhost")) {
			try {
				// If the local host is not a valid address, it will throw an
				// Exception and thus not return true.
				InetAddress.getLocalHost();
				return true;
			} catch (UnknownHostException e) {
				return false;
			}
		} else {
			String[] numStrings = host.split("\\.");
			byte[] addressBytes = new byte[4];
			// Loop through the bytes
			if (numStrings.length == 4) {
				for (int i = 0; i < 4; i++) {
					if (Integer.parseInt(numStrings[i]) < 0
							|| Integer.parseInt(numStrings[i]) > 255)
						return false;
				}
			} else {
				return false;
			}
			return true;
		}
	}

	/**
	 * Check if the given port is valid
	 * 
	 * @param port
	 *            to validate
	 * @ensure true if 0 <= port <= 65535, else false
	 */
	public static boolean isValidPort(int port) {
		return (port >= 0 && port <= 65535);
	}

	/**
	 * Check if the given port is valid
	 * 
	 * @param port
	 *            to validate
	 * @ensure true if 0 <= port <= 65535 and string was a valid integer, else
	 *         false
	 */
	public static boolean isValidPort(String port) {
		try {
			return isValidPort(Integer.parseInt(port));
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
