package week1.boom;

/**
 * Boom<Elem>: simpele binaire boom.
 * Practicumopdracht P2.
 * @author  Theo Ruys
 * @version 2005.01.30
 */
public class Boom <Elem> {
    private Elem        value;
    private Boom<Elem>  left, right;
    
    /**
     * Construeert een Boom object.
     * @require value != null
     * @ensure  this.getValue() == value &&
     *          this.getLeft()  == left && this.getRight() == right
     */
    public Boom(Elem value, Boom<Elem> left, Boom<Elem> right) {
        this.value = value;
        this.left  = left;
        this.right = right;
    }
    
    public Elem         getValue()  { return value; }
    public Boom<Elem>   getLeft()   { return left; }
    public Boom<Elem>   getRight()  { return right; }
    
    /** Levert het aantal Elem-waarden op in deze Boom. */

    public int size() {
        return 0; // BODY NOG TOE TE VOEGEN
    }    

    
    /**
     * Levert een String-representatie van deze Boom.
     * De String-representatie van een Boom bestaat uit
     * "(" gevolgd door de Elem-waarde van deze Boom
     *     gevolgd door een "," 
     *     gevolgd door de String-representatie van de linker Boom
     *     gevolgd door een ","
     *     gevolgd door de String-representatie van de rechter Boom
     *     gevolgd door 
     * ")" 
     * Een boom die leeg is (d.w.z. null) wordt weergegeven met "-".
     */
    @Override
    public String toString() {
        return ""; // BODY NOG TOE TE VOEGEN
    }
}
