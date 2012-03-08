package week4.threads;

import java.io.*;

/**
 * P2 prac wk4.
 * Hulpklasse voor gebruik van standaardin- en uitvoer.
 * @author  Arend Rensink en Theo Ruys.
 * @version 2005.02.21
 */
public class SyncConsole {
    /** teken voor een fout in de invoer */
    static public final char FOUT = '\u0004';

    static private BufferedReader in =
        new BufferedReader(new InputStreamReader(System.in));
    static private PrintStream out = System.out;

    private SyncConsole() {}

    /**
     * Schrijft een test op standaarduitvoer.
     * @param tekst de te schrijven tekst
     */
    static synchronized public void print(String tekst) {
        out.print(tekst);
    }

    /**
     * Schrijft een test op standaarduitvoer, afgesloten met een regelovergang.
     * @param tekst de te schrijven tekst
     */
    static synchronized public void println(String tekst) {
        out.println(tekst);
    }

    /**
     * Leest een regel tekst van standaardinvoer.
     * Bij wijze van vraag wordt eerst een tekst weergegeven.
     * @param  tekst vraagtekst
     * @return ingelezen tekst; is nooit null
     */
    static synchronized public String readString(String tekst) {
        print(tekst);
        String antw = null;
        try {
            antw = in.readLine();
        } catch (IOException e) {
        }

        if (antw == null)
            return ""+FOUT;
        else 
            return antw;
    }

    /**
     * Leest een geheel getal van standaardinvoer.
     * Bij wijze van vraag wordt eerst een tekst weergegeven.
     * Bij ongeldige invoer (geen geheel getal) volgt een foutboodschap,
     * en wordt de vraag herhaald tot een geldige antwoord is gegeven.
     * @param  tekst vraagtekst
     * @return ingevoerd getal
     */
    static synchronized public int readInt(String tekst) {
        return readInt(tekst, "Voer een geheel getal in");
    }
    
    /**
     * Leest een geheel getal van standaardinvoer.
     * Bij wijze van vraag wordt eerst een tekst weergegeven.
     * Bij ongeldige invoer (geen geheel getal) volgt een foutboodschap,
     * en wordt de vraag herhaald tot een geldige antwoord is gegeven.
     * @param tekst vraagtekst
     * @param foutboodschap tekst die gegeven wordt bij foutieve invoer
     * @return ingevoerd getal
     */
    static synchronized public int readInt(String tekst, String foutboodschap) {
        do {
            String antw = readString(tekst);
            try {
                return Integer.parseInt(antw);
            }
            catch (NumberFormatException e) {
                println(foutboodschap);
            }
        }
        while (true);
    }
    
}