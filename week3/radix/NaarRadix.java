package week3.radix;

public class NaarRadix {
	public static void main(String[] args) {
		try {	
			int toRadix = Integer.parseInt(args[0]);
			int number = Integer.parseInt(args[1]);
			if (toRadix > Character.MAX_RADIX)
				System.out.println("Please use a maximum value of " + Character.MAX_RADIX + " for the radix");
			else
				System.out.println(number + " to radix " + toRadix + " = " + naarRadix(toRadix, number).toUpperCase());	
	    } catch (NumberFormatException e) {
	    	System.out.println("Either one of the values is not a valid number");
	    } catch (ArrayIndexOutOfBoundsException e) {
	    	System.out.println("Please give 2 arguments, target radix and a number to convert.");
	    }		
	}
	
	/**
	 * Berekent recursief de waarde van decimaal naar de gegeven radix.
	 * 
	 * @param toRadix 1 < integer <= 36
	 * @param numberToConvert integer
	 * @return result as a String
	 */
	public static String naarRadix(int toRadix, int number) {
		StringBuffer result = new StringBuffer();
		if (number < toRadix) {
			result = result.append(Character.forDigit(number, toRadix));
		} else {
			result.append(naarRadix(toRadix, number / toRadix, ""));
			char character = Character.forDigit(number % toRadix, toRadix);
			result.append(character);
		}
		return result.toString();
	}
	
	private static String naarRadix(int toRadix, int number, String resultString) {
		if (number == 0) {
			return resultString;
		}
		
		StringBuffer result = new StringBuffer();
		result.append(resultString);
		if (number < toRadix) {
			result = result.append(Character.forDigit(number, toRadix));
		} else {
			result.append(naarRadix(toRadix, number / toRadix, resultString));
			char character = Character.forDigit(number % toRadix, toRadix);
			result.append(character);
		}
		return result.toString();
	}
}
