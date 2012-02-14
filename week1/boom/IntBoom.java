package week1.boom;

/**
 * IntBoom: binaire boom voor het opslaan van Integer-objecten.
 * Practicumopdracht P2.
 * @author  Theo Ruys
 * @version 2005.01.30
 */
public class IntBoom extends Boom<Integer> {
    /**
     * Construeert een IntBoom object.
     * @require value != null
     * @ensure  this.getValue() == value &&
     *          this.getLeft()  == left && this.getRight() == right
     */
    public IntBoom(Integer value, IntBoom left, IntBoom right) {
        super(value, left, right);
    }
    
    @Override
    public IntBoom getLeft()  { return (IntBoom) super.getLeft(); }

    @Override
    public IntBoom getRight() { return (IntBoom) super.getRight(); }

    /** Levert de som op van alle waarden in deze IntBoom. */
    public int sum() {
        return 0; // BODY NOG TOE TE VOEGEN
    }

    /** Bepaalt de maximum waarde in deze IntBoom. */
    public int max() {
        return 0; // BODY NOG TOE TE VOEGEN
    }
    
    public static void main(String[] args) {
        IntBoom bb 
            = new IntBoom(9, 
                          new IntBoom(16, new IntBoom(4, null, null), null),
                          new IntBoom(11, 
                                      new IntBoom(8, 
                                                  new IntBoom(3, null, null),
                                                  new IntBoom(7, null, null)),
                                      new IntBoom(5, 
                                                  null,
                                                  new IntBoom(6, null, null))
                                     )
                         );

        System.out.println(bb);
        System.out.println("size = " + bb.size());
        System.out.println("sum  = " + bb.sum());
        System.out.println("max  = " + bb.max());
    }
}
