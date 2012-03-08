package week3.bke;

import java.util.Observable;


/**
 * P2 week5.
 * Spel. Klasse voor het onderhouden van het BoterKaasEieren-spel.
 * Practicumopdracht Programmeren 1.
 * @author  Theo Ruys en Arend Rensink
 * @version 2002.01.22
 */
public class Spel extends Observable {

    // -- Instance variables -----------------------------------------

    /**
     * Het speelbord.
     * @invariant bord != null
     */
    private Bord bord;

    /**
     * De huidige Mark.
     * @invariant huidig == Mark.XX || huidig == Mark.OO
     */
    private Mark huidig;

    // -- Constructors -----------------------------------------------

    /**
     * Construeert een nieuw Spel object.
     */
    public Spel() {
        bord   = new Bord();
        huidig = Mark.XX;
        this.setChanged();
        this.notifyObservers();
    }

    // -- Queries ----------------------------------------------------

    /**
     * Levert het bord op.
     */
    public Bord getBord() {
        return bord;
    }

    /**
     * Levert de Mark op die aan de beurt is.
     */
    public Mark getHuidig() {
        return huidig;
    }

    // -- Commands ---------------------------------------------------

    /**
     * Reset dit spel. <br>
     * Het speelbord wordt weer leeggemaakt en Mark.XX wordt de 
     * huidige Mark.
     */
    public void reset() {
        huidig = Mark.XX;
        bord.reset();
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Zet de huidige mark in het vakje i. 
     * Geef de beurt aan de andere Mark.
     * @require  0<=i && i <Bord.DIM*Bord.DIM && this.getBord().isLeegVakje(i)
     * @param    i de index waar de mark zal worden gezet.
     */
    public void doeZet(int i) {
        bord.setVakje(i, huidig);
        huidig = huidig.other();
        this.setChanged();
        this.notifyObservers(i);
        
    }
} 

