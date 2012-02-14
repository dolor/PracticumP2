package week2.textutil;

import java.io.*;

/**
 * P2 prac wk2
 * FilesProcessor - klasse voor het openen en analyseren van tekstbestanden.
 * @author   Theo Ruys
 * @version  2006.02.05
 */
public class FilesProcessor {
    private Analyzer analyzer;

    /** Koppelt de te gebruiken Analyzer aan deze FilesProcessor */
    public FilesProcessor(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * De methode openAndProcess interpreteert de namen in args
     * als bestandsnamen. De methode probeert deze bestanden
     * een-voor-een als BufferedReader te openen en roept 
     * vervolgens de methode process van zijn Analyzer aan op 
     * de BufferedReader.
     * Als (args.length == 0) wordt er van de System.in gelezen.
     */
    public void openAndProcess(String[] args) {
        System.out.println("FilesProcessor.openAndProcess: IK DOE NOG NIETS!");
    }
}
