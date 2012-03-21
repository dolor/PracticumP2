package eindopdracht.server.network;

import eindopdracht.model.Command;

public abstract class PlayerListener {
/**
 * Abstract class which is implemented by all classes PlayerHandler can report to.
 */
	public abstract void handleCommand(Command command);
}
