package week1.bke;

/**
 * Executeerbare klasse voor het Boter-Kaas-Eieren-spel, 
 * waarbij ook tegen de Computer gespeeld kan worden.
 * Practicumopdracht P2.
 * @author  Theo Ruys
 * @version 2005.01.30
 */
public class BoterKaasEieren {
    /**
     * Construeert een Speler op grond van de input-string. <ul>
     * <li> Als <code>s.equals("-D")</code> dan wordt er een 
     *      ComputerSpeler met DommeStrategie gecreeerd. 
     * <li> Als <code>s.equals("-S")</code> dan wordt er een
     *      ComputerSpeler met SlimmeStrategie gecreeerd.
     * <li> Als <code>s.equals("-P")</code> dan wordt er een
     *      ComputerSpeler met PerfecteStrategie gecreeerd.
     * <li> Anders wordt er een MensSpeler gecreeerd.</ul>
     * @param  s naam/type van de Speler
     * @param  m de markering van de Speler
     * @return Speler-object
     */
    private static Speler createSpeler(String s, Mark m) {
        if (s.equals("-D"))
            return new ComputerSpeler(m, new DommeStrategie());
        else if (s.equals("-S"))
            return new ComputerSpeler(m, new SlimmeStrategie());
        else if (s.equals("-P"))
        	return new ComputerSpeler(m, new PerfecteStrategie());
        else
            return new MensSpeler(s, m);
    }

    /** Construeert de top-level objecten en start het spel. */
    public static void main(String[] args) {
        if (args.length == Spel.AANTAL_SPELERS) {
            System.out.println("Boter-Kaas-Eieren");
            System.out.println("-----------------");

            Speler s1 = createSpeler(args[0], Mark.XX);
            Speler s2 = createSpeler(args[1], Mark.OO);            
            Spel spel = new Spel(s1, s2);
            spel.start();
        }
        else
            System.out.println("gebruik: BoterKaasEieren <naam1> <naam2>");
    }

} // end of class BoterKaasEieren 

