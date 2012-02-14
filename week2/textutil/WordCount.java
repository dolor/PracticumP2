package week2.textutil;

import java.util.*;
import java.io.*;

/**
 * P2 prac wk2.
 * Programma WordCount voor het tellen van regels, woorden en karakters.
 * @author  Theo Ruys
 * @version 2005.02.08
 */
public class WordCount implements Analyzer {
    /** 
     * Telt het aantal regels, woorden en karakters in reader en
     * schrijft de totalen samen met fname naar de System.out.
     * Woorden worden gescheiden door de gebruikelijke whitespace,
     * d.w.z. spaties en tabs (leestekens worden niet meegenomen).
     * @throws IOException als er iets mis gaat bij het lezen
     */
    public void process(String fname, BufferedReader reader) throws IOException {
        System.out.println("WordCount.process: IK DOE NOG NIETS!");
    }

    public static void main(String[] args) {
        FilesProcessor fp = new FilesProcessor(new WordCount());
        fp.openAndProcess(args);
    }
}
