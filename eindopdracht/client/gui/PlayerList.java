package eindopdracht.client.gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.*;

import eindopdracht.client.model.player.Player;

public class PlayerList extends JTextPane {
	public void addPlayer(Player player) { // better implementation--uses StyleContext
		Color c = eindopdracht.model.Color.getAwtColor(player.getColor());
		String s = player.getName() + "\n";
	    StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
	                                        StyleConstants.Foreground, c);
	  
	    int len = getDocument().getLength(); // same value as getText().length();
	    setCaretPosition(len);  // place caret at the end (with no selection)
	    setCharacterAttributes(aset, false);
	    replaceSelection(s); // there is no selection, so inserts at caret
	  }
}
