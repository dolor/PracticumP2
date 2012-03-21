
public class OverflowGenerator extends Thread{
	public void run() {
		this.start();
	}
	
	public static void main(String[] args) {
		OverflowGenerator gen = new OverflowGenerator();
		gen.start();
	}
}
