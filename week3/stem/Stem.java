package week3.stem;

import java.util.Scanner;

/**
 * P2 prac wk3.
 * @author Arend Rensink en Theo Ruys
 * @version 2005.02.15
 */
public class Stem {
    public static void main(String[] args) {
        Uitslag uitslag = new Uitslag();
        StemFrame stemFrame = new StemFrame(uitslag);
        UitslagJFrame uitslagJFrame = new UitslagJFrame(uitslag);
        uitslagJFrame.setVisible(true);
        stemFrame.setVisible(true);
        String partij;
        
        
        
        do {
            partij = readString("Nieuwe partij? ");
            if (partij.length() != 0)
                   uitslag.voegPartijToe(partij);
        } while (partij.length() != 0);
        
        
    }
    
    public static String readString(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return null;
        }
    }
}
