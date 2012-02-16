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
    	HashMap<String, Integer> map = new HashMap<String, Integer>();
        //TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        Scanner scanner = new Scanner(r).useDelimiter(DELIM);
        
        while(scanner.hasNext()) {
        	String word = scanner.next().toLowerCase();
        	if (map.containsKey(word)) {
        		int v = (map.get(word) + 1);
        		map.put(word, v);
        	} else {
        		map.put(word, 1);
        	}
        }
        
        SortedSet<KeyValueSet> sorted = new TreeSet<KeyValueSet>();
        for (String key: map.keySet()) {
        	sorted.add(new KeyValueSet(key, map.get(key)));
        }
        
    	System.out.println("Analyzing file " + fname);
    	System.out.printf("%-19s%s\n", "Woord:", "Aantal keer:");
        for (KeyValueSet word:sorted) {
        	System.out.printf("%-19s%d\n", word.key + ":", word.value);
        }
    }

    public static void main(String[] args) {
        FilesProcessor fp = new FilesProcessor(new WordFreq());
        fp.openAndProcess(args);
    }
    
    
    class KeyValueSet implements Comparable<KeyValueSet>{
    	public String key;
    	public Integer value;
    	
    	public KeyValueSet(String key, int value) {
    		this.key = key;
    		this.value = value;
    	}
    	
		@Override
		public int compareTo(KeyValueSet o) {
			// TODO Auto-generated method stub
    		if(this.value <= o.value) {
    			return 1;
    		} else {
    			return -1;
    		}
		}
    }
}
