package week2.textutil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * P2 prac wk2. 
 * Interface Analyzer voor het analyseren van een BufferedReader.
 * @author  Theo Ruys
 * @version 2005.02.08
 */
interface Analyzer {
    /**
     * De methode process analyseert de inhoud van de BufferedReader r
     * en schrijft de resultaten naar de System.out.
     * @param fname naam van de file van r (- als System.in)
     * @param r de invoer  
     */
    void process(String fname, BufferedReader r) throws IOException;
}
