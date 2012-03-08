package week3.stem;

public class Partij implements Comparable{
	String naam;
	int nr;
	int stemmen;
	
	public Partij(int nr, String naam)
	{
		this.naam = naam;
		this.nr = nr;
		this.stemmen = 0;
	}
	
	public void Stem()
	{
		this.stemmen++;
	}
	
	public String getNaam()
	{
		return this.naam;
	}
	public int getNr()
	{
		return this.nr;
	}
	public int getStemmen()
	{
		return this.stemmen;
	}
	
	public int compareTo(Object c)
	{
		if (this.getNr() < ((Partij)c).getNr())
		{
			return 1;
		}
		else
		{
			return -1;    			
		}
	}
}
