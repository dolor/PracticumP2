package week3.radix;

import java.util.Observer;  
import java.util.Observable;

import java.util.Scanner;
import java.io.PrintStream;
import java.util.*;

/**
 * P2 prac wk3.
 * Zet een Getal op en creeert GetalObservers.
 * @author Rieks op den Akker, Arend Rensink en Theo Ruys
 * @version 2005.02.13
 */
public class GetalTUI implements Observer{
    
    /** Lijst met commando's van deze TUI. */
    private final List<Commando> commandos;
    
    /** Lokale definitie van standaardinvoer. */
    static private Scanner in = new Scanner(System.in);
    /** Lokale definitie van standaarduitvoer. */
    static private PrintStream out = System.out;
    /** Commando-prompt. */
    static private final String PROMPT = "Commando: ";
    
    private Getal getal;

    public GetalTUI() {
        getal = new Getal();
        getal.addObserver(this);
        
        commandos = new ArrayList<Commando>();
        commandos.add(new ObserverCommando());
        commandos.add(new WaardeCommando());
        commandos.add(new HulpCommando());
        commandos.add(new ExitCommando());

    }
    /** Zet een GetalTUI op en start deze. */
    public static void main(String args[]) {
        GetalTUI ui =  new GetalTUI();
        ui.start();
    }
        
    // ----- TUI gerelateerde commando's -----------------------------------
    
    /**
     * Start het getalconversie programma.
     * Vraagt telkens om een commando en voert dit uit.
     */
    public void start() {
        out.println("Getalconversie programma. ");
        boolean einde = false;
        printMenu();
        while (!einde) {
            Scanner commando = leesCommando();
            einde = (commando == null) ? true
                                       : voerCommandoUit(commando);
        }
    }
    
    public void printMenuRegel(Commando c) {
        out.println("  " + c.getTeken() + " " + c.getBeschrijving());
    }
    
    /** Print het menu met alle commando's. */
    public void printMenu() {
        out.println("Commando's:");
        for (Commando c: commandos) 
            printMenuRegel(c);
    }

    /**
     * Leest een commando van de standaard-invoer.
     * Gaat net zo lang door tot er een niet-leeg commando ingevoerd is.
     * Het resultaat is een scanner van een niet-lege regel, 
     * of <tt>null</tt> als er geen regels meer in de invoer zijn.
     */
    public Scanner leesCommando() {
        Scanner result;
        do {
            String regel = leesRegel(PROMPT);
            if (regel != null) {
                result = new Scanner(regel);
            } else {
                result = null;
            }
        } while (result != null && !result.hasNext());
        return result;
    }

    /**
     * Leest een regel tekst in van de standaard-invoer.
     * Het resultaat is de ingelezen regel als <tt>String</tt>,
     * of <tt>null</tt> als er geen regels meer in de invoer zijn.
     */
    public String leesRegel(String prompt) {
        String result;
        out.print(prompt);
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return null;
        }
    }
    
    /**
     * Voert een commando, bestaande uit een regel, uit.
     * Levert <tt>true</tt> op als het commando een <i>exit</i> was.
     * @require commandoRegel != null && commandoRegel.hasNext()
     */
    public boolean voerCommandoUit(Scanner commandoRegel) {
        String commandoString = commandoRegel.next();
        String par1 = commandoRegel.hasNext() ? commandoRegel.next() : null;
        String par2 = commandoRegel.hasNext() ? commandoRegel.next() : null;
        if (commandoString.length() == 1) {
            char commandoChar = commandoString.charAt(0);
            boolean uitgevoerd = false;
            boolean exit = false;
            int i = 0;
            while (!uitgevoerd && i < commandos.size()) {
                Commando commando = commandos.get(i);
                if (commando.kanUitvoeren(commandoChar, par1, par2)) {
                    if (commando instanceof ExitCommando) {
                        exit = true;
                    } else {
                        commando.voerUit(par1, par2);
                    }
                    uitgevoerd = true;
                }
                i++;
            }
            if (! uitgevoerd) {
                out.println("Onbekend commando: "+commandoString);
            }
            return exit;
        } else {
            out.println("Onbekend commando: "+commandoString);
            printMenu();
            return false;
        }
    }
        
    // ----- Commando's ----------------------------------------------------
    

    private class ObserverCommando extends Commando {
        ObserverCommando() {
            super('o', 1, "radix .............. maak nieuwe observer");
        }
        
        public void voerUit(String par1, String par2) {
        	new GetalObserver(getal, Integer.parseInt(par1));
        }
    }

    private class WaardeCommando extends Commando {
        WaardeCommando() {
            super('w', 1, "waarde ............. zet getal op nieuwe waarde");
        }
        
        public void voerUit(String par1, String par2) {
            try {
                int w = Integer.parseInt(par1);
                getal.setWaarde(w);
            } catch (NumberFormatException exp) {
                out.println("Error: " + par1 + " is geen getal");
            }
        }            
    }

    private class HulpCommando extends Commando {
        HulpCommando() {
            super('h', 0, ".................... hulp (dit menu)");
        }

        public void voerUit(String par1, String par2) {
            printMenu();
        }
    }
    
    private class ExitCommando extends Commando {
        ExitCommando() {
            super('x', 0, ".................... exit");
        }

        public void voerUit(String par1, String par2) {
            // doe niets - wordt afgehandeld in voerCommandoUit
        }
    }

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Waarde is veranderd in "+arg.toString());
	}
} 
