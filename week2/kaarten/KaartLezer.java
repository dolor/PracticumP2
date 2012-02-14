package week2.kaarten;

import java.io.*; 

/**
 * Testklasse voor in- en uitvoermethoden van Kaart.
 * Practicom P2, week 2.
 * @author  Arend Rensink en Theo Ruys
 * @version 2006.02.05
 */
public class KaartLezer {
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static DataInputStream dataIn;
    private static DataOutputStream dataUit;
    private static ObjectInputStream objectIn;
    private static ObjectOutputStream objectUit;

    /** Leest een Kaart van een reader of een stream. */
    private static Kaart lees() throws EOFException {
        if      (reader != null)        return Kaart.lees(reader);
        else if (dataIn != null)        return Kaart.lees(dataIn);
        else                            return Kaart.lees(objectIn);
    }

    /** Schrijft een Kaart naar een writer of een stream. */
    private static void schrijf(Kaart k) throws IOException {
        if      (writer  != null)       k.schrijf(writer);
        else if (dataUit != null)       k.schrijf(dataUit);
        else                            k.schrijf(objectUit);
    }

    /** Sluit de reader/inputstream en de writer/outputstream. */ 
    private static void close() {
        try { 
            if      (reader != null)    reader.close();
            else if (dataIn != null)    dataIn.close(); 
            else                        objectIn.close(); 
        } catch (IOException exc) {}
        try { 
            if      (writer  != null)   writer.close();
            else if (dataUit != null)   dataUit.close(); 
            else                        objectUit.close();
        } catch (IOException exc) {}
    }
    
    /** 
     * Programma dat Kaarten leest uit een Reader of InputStream en
     * de gelezen Kaarten vervolgens wegschrijft naar een Writer of
     * OutputStream. 
     * 
     *
	 * Kaartlezer: Aanroepen met 'java KaartLezer [file1] [file2]'. 
	 * file1 is de file waar de kaarten uit gelezen worden.
	 * file2 is waar de kaarten vervolgens naar weggeschreven worden.
	 * Beide filenamen zijn te vervangen door een '-' om de respectievelijke operatie niet uit te voeren.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Gebruik: java Kaartlezer " +
                               "[<filenaam>|-] [<filenaam>|-]");
            return;
        }

        try {
            if (args[0].equals("-"))
                reader = new BufferedReader(new InputStreamReader(System.in));
            else if (args[0].endsWith(".txt"))
                reader = new BufferedReader(new FileReader(args[0]));
            else if (args[0].endsWith(".dat"))
                dataIn = new DataInputStream(new FileInputStream(args[0]));
            else if (args[0].endsWith(".obj"))
                objectIn = new ObjectInputStream(new FileInputStream(args[0]));
            else {
                System.err.println("Formaat van "+args[0]+" niet herkend");
                return;
            }
        } catch (IOException exc) {
            System.err.println("File "+args[0]+" kan niet worden geopend");
        }

        try {
            if (args[1].equals("-"))
                writer = new PrintWriter(System.out);
            else if (args[1].endsWith(".txt"))
                writer = new PrintWriter(new FileWriter(args[1]));
            else if (args[1].endsWith(".dat"))
                dataUit = new DataOutputStream(new FileOutputStream(args[1]));
            else if (args[1].endsWith(".obj"))
                objectUit = new ObjectOutputStream(new FileOutputStream(args[1]));
            else {
                System.err.println("Formaat van "+args[1]+" niet herkend");
                return;
            }
        } catch (IOException exc) {
            System.err.println("File "+args[1]+" kan niet worden geopend");
        }

        boolean doorgaan = true;
        while (doorgaan)
            try {
                Kaart k = lees();
                if (k == null)
                    System.err.println("Fout in invoer");
                else 
                    schrijf(k);
            } catch (EOFException exc) {
                doorgaan = false;
            } catch (IOException exc) {
                System.err.println(exc.getMessage());
            }

        close();
    }
}
