package week3.stem;

public class Partij implements Comparable{
	private String naam;
	private int stemmen;
	private int partijNummer;
	
	/**
	 * Creates a new Partij object.
	 * @requires partijnummer, partijnaam
	 */
	public Partij(int nummer, String naam) {
		
	}
	
	public void addStem() {
		stemmen++;
	}
	
	public int getStemmen() {
		return stemmen;
	}
	
	public String getNaam() {
		return naam;
	}
	
	public int getPartijNummber() {
		return partijNummer;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return ((Partij)o).naam.compareTo(naam);
	}
}
