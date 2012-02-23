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
		//System.out.print("File:                         Lines:        Words:        Chars:");
		System.out.println("File: " + fname);
		int lines = 0;
		int words = 0;
		int characters = 0;
		Scanner scan = null;
		boolean cont = true;
		while (cont) {
			try {
				String l = reader.readLine();
				if (l != null) {
					characters += l.length() + 1;
					scan = new Scanner(l);
					lines++;
					while (scan.hasNext()) {
						words++;
						scan.next();
					}
				} else {
					throw new EOFException("End of file");
				}
			} catch (EOFException e) {
				cont = false;
			}
		}
		System.out.printf("%30s%14d%14d%14d\n", fname, lines, words, characters);
		System.out.println("    Lines: " + lines);
		System.out.println("    Words: " + words);
		System.out.println("    Characters: " + characters);
	}

	public static void main(String[] args) {
		FilesProcessor fp = new FilesProcessor(new WordCount());
		fp.openAndProcess(args);
	}
}
