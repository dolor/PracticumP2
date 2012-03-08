package week3.bke;

/**
 * Representeert een markering in het Boter-Kaas-Eieren spel.
 * Er zijn bij Boter-Kaas-Eieren maar drie mogelijke markeringen:
 * Mark.XX, Mark.OO en Mark.LEEG. Dit zijn constante objecten.
 * Er kunnen geen andere Mark-objecten gecreeerd worden omdat de
 * beide constructoren private zijn.
 * Practicumopdracht Programmeren 1.
 * @author  Theo Ruys
 * @version 2001.11.03
 */

public class Mark {

    // -- Constants --------------------------------------------------

    public static final Mark LEEG = new Mark(' ');
    public static final Mark XX   = new Mark('X');
    public static final Mark OO   = new Mark('O');

    // -- Instance variables -----------------------------------------

    private char rep;                   // char-representatie van de Mark

    // -- Constructors -----------------------------------------------

    /**
     * Creert een Mark object waarvan <code>c</code> de representatie is.<br>
     * De constructor is <code>private</code> zodat er buiten de 
     * constanten LEEG, XX en OO geen Mark-objecten gecreeerd 
     * kunnen worden.
     * @param c char-representatie van deze Mark
     */
    private Mark(char c) { 
        rep = c;
    }

    /**
     * Levert de String-representatie van deze Mark op.
     * @ensure als <code>this == XX</code> dan <code>result == "X"</code><br>
     *         als <code>this == OO</code> dan <code>result == "O"</code><br>
     *         als <code>this == LEEG<code> dan <code>result == " "</code>
     * @return de String-representatie van deze Mark
     */
    public String toString() {
        return rep+"";
    }

    /**
     * Levert de andere Mark.
     * @require <code>this == XX || this == OO</code>
     * @ensure  als <code>this == XX</code> dan <code>result == OO</code><br>
     *          als <code>this == OO</code> dan <code>result == XX</code>
     * @return  de andere markering
     */
    public Mark other() {
        if (this == XX) return OO;
        else if (this == OO) return XX;
        else return LEEG;
    }
} // end of class Mark
