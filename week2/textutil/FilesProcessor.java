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
    	if (args.length == 0) {
    		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				this.process(in.readLine());
			} catch (IOException e) {
				System.out.println("Error opening default input");
			}
    	}
    	
    	for (String arg:args) {
    		System.out.println(arg);
			process(arg);
    	}
    }
    
    public void process(String file){
    	BufferedReader reader;
    	try {
    		reader = new BufferedReader(new FileReader(file));
        	this.analyzer.process(file, reader);
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
