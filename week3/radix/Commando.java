package week3.radix;

/**
 * P2 prac wk3 (gekopieerd van P1 2004/2005: week6.ui.Commando).
 * Abstractie van een commando in een tekstueel gebruikersinterface.
 * Het commando bestaat uit een enkele letter en 0 tot 2 parameters.
 * @author  Arend Rensink en Theo Ruys
 * @version 2005.02.13
 */
abstract public class Commando {
    /** Teken dat voor dit commando staat. */
    private final char teken;
    /** Korte beschrijving van parameters en functie. */
    private final String beschrijving;
    /** Aantal benodigde parameters. */
    private int aantalPars;

    /** 
     * Construeert een commando dat gegeven is door een bepaald teken
     * en een bepaald aantal parameters hoort te krijgen,
     * en met een gegeven beschrijving.
     * @param teken het teken dat voor dit commando staat
     * @param aantalPars het aantal parameters van dit commando
     * @param beschrijving korte beschrijving van parameters en commando 
     * @require <tt>0 <= aantalPars && aantalPars <= 2 && beschrijving != null</tt>
     * @ensure <tt>getTeken() == teken
     *      && getBeschrijving() == beschrijving
     *      && getAantalPars() == aantalPars</tt>
     */
    protected Commando(char teken, int aantalPars, String beschrijving) {
        this.teken = teken;
        this.aantalPars = aantalPars;
        this.beschrijving = beschrijving;
    }

    /**
     * Levert het karakteristieke teken van dit commando. 
     * Dit dient kenmerkend te zijn voor het commando;
     * d.w.z., twee verschillende commando's moeten niet
     * hetzelfde teken hebben.
     */
    public char getTeken() {
        return teken;
    }

    /**
     * Korte beschrijving van parameters en commando.
     * De beschrijving dient te bestaan uit een enkele regel,
     * waarop aantal en aard van de benodigde parameters worden
     * uitgelegd, en de bedoeling van het commando wordt beschreven.
     * @return beschrijving van het commando
     * @ensure <tt>result != null</tt>
     */
    public String getBeschrijving() {
        return beschrijving;
    }

    /** 
     * Levert het aantal parameters dat dit commando verwacht.
     * Hier is gekozen voor een parametertal tussen de 0 en 2.
     * @return aantal parameters dat dit commando verwacht
     * @ensure <tt>0 <= result <= 2</tt>
     */
    public int getAantalPars() {
        return aantalPars;
    }

    /**
     * Geeft aan of een gegeven combinatie van teken en parameters
     * een geldige aanroep van dit commando vormt.
     * Als het teken wel klopt maar het aantal parameters niet
     * dan wordt een foutboodschap geprint.
     * @param teken het teken van het uit te voeren commando
     * @param par1 de eerste parameter van de aanroep, of <tt>null</tt>
     * @param par2 de tweede parameter van de aanroep, of <tt>null</tt>
     * @return true als <tt>teken</tt> samen met <tt>par1</tt> en <tt>par2</tt>
     * een geldige aanroep van dit commando vormen
     * @require als <tt>par1 == null</tt> dan <tt>par2 == null</tt>
     * @ensure <tt>result == (teken == this.teken()) &&
     *         (  aantalPars == 0 && par1 == null
     *         || aantalPars == 1 && par1 != null && par2 == null
     *         || aantalPars == 2 && par2 != null)</tt>
     */
    public boolean kanUitvoeren(char teken, String par1, String par2) {
        if (teken == this.teken) {
            if (aantalPars == 0 && par1 == null
                || aantalPars == 1 && par1 != null && par2 == null
                || aantalPars == 2 && par2 != null) {
                return true;
            } else {
                System.out.println("Fout aantal parameters in commando "+teken);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Voert dit commando uit, met gegeven parameters.
     * @param par1 de eerste parameter van de methode, of <tt>null</tt>
     * @param par2 de tweede parameter van de methode, of <tt>null</tt>
     * @require <tt>this.kanUitvoeren(this.teken(), par1, par2)</tt>
     */
    abstract public void voerUit(String par1, String par2);
}
