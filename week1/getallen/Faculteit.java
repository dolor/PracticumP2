package week1.getallen;

public class Faculteit{
	public static void main(String[] args) {
		try {
    	    fac(Integer.parseInt(args[0]));	
    	} catch (NumberFormatException e) {
    		System.out.println("Value is not a valid number");
    	} catch (ArrayIndexOutOfBoundsException e) {
    		System.out.println("Please give an argument.");
    	} catch (FaculteitsException e) {
    		System.out.println(e.getMessage());
    	}
	}
	
	/**
	 * @require a valid number as an integer, 0<i<=12
	 * 
	 * @ensure prints the faculty 
	 * 
	 * @throws FaculteitsException if i < 0 or i > 12
	 */
	public static void fac(int num) throws FaculteitsException{
		if (num < 0) {
			throw new FaculteitsException("Factorial of a number < 0 not defined");
		}
		System.out.println("Faculty: " + (num==0?1:getFac(num)));
	}
	
	public static int getFac(int num) throws FaculteitsException{
		if (num == 1) {
			return num;
		} else {
			int fac = getFac(num-1);
			int result = num*fac;
			if (result/fac != num) {
				throw new FaculteitsException("Factorial too large for an int");
			}
			return result;
		}
	}
}
