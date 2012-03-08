package week3.stem;

import java.util.*;

/**
 * P2 prac wk3.
 * Uitslag. Model voor uitslag stemming.
 * @author  Arend Rensink en Theo Ruys
 * @version 2005.02.15
 */
public class Uitslag extends Observable {
    // ---- Instantievariabelen --------------------------------

    /** 
     * De uitgebrachte stemmen.
     * @invariant <code>stemmen != null</code>
     */
    private Map<String, Integer> stemmen = new HashMap<String, Integer>();

    // ---- Queries -----------------------------------------

    /**
     * Levert het aantal tot dusverre uitgebrachte stemmen op
     * de aangegeven partij op.
     * @require <code>this.getPartijen().contains(partij)</code>
     * @param   partij de partij waarvan het aantal stemmen bepaald wordt
     */
    public int getStemmenOp(String partij) {
        return stemmen.get(partij);
    }

    /** Levert de bestaande partijen op als verzameling String-objecten. */
    public Set<String> getPartijen() {
        return stemmen.keySet();
    }

    // ---- Commando's -----------------------------------------

    /**
     * Voegt een partij toe aan de bestaande partijen.
     * Het aantal stemmen is aanvankelijk 0.
     * Als de partij al bestond, gebeurt er niets.
     * @ensure <code>this.getPartijen().contains(partij)</code>
     */
    public void voegPartijToe(String partij) {
        if (!stemmen.containsKey(partij)) {
            stemmen.put(partij, 0);
            this.setChanged();
            this.notifyObservers(this.stemmen);
        }
    }

    /**
     * Telt 1 op bij het aantal stemmen van een partij.
     * Als de partij niet bestaat, gebeurt er niets.
     * @param partij de partij die een stem erbij krijgt
     * @ensure als <code>this.getPartijen().contains(partij)</code>
     *         dan <code>this.getStemmenOp(partij) == old.getStemmenOp(partij)+1</code>
     */
    public void stem(String partij) {
        if (stemmen.containsKey(partij)) {
            int aantal = stemmen.get(partij);
            stemmen.put(partij, aantal+1);
            this.setChanged();
            this.notifyObservers(this.stemmen);
        }
    }
}
