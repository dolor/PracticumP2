package week2.textutil;

import java.util.*;
import java.io.*;

/**
 * P2 prac wk2.
 * WordFreq. Bepaalt de frequentie van woorden.
 * @author  Theo Ruys
 * @version 2005.02.08
 */
public class WordFreq implements Analyzer {
    public static final String DELIM = "[\\s\"\\-`',?.!();:]+";
    
    /**
     * Bepaalt de frequentie van de verschillende woorden r en
     * schrijft de gesorteerde lijst naar de System.out.
     * Woorden worden gescheiden door DELIM.
     * @throws IOException als er iets mis gaat bij het lezen
     */
    public void process(String fname, BufferedReader r) throws IOException {
        System.out.println("WordFreq.process: IK DOE NOG NIETS!");
    }

    public static void main(String[] args) {
        FilesProcessor fp = new FilesProcessor(new WordFreq());
        fp.openAndProcess(args);
    }
}
