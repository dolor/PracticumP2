package week4.threads;

public class TestConsole extends Thread{
	public static void main(String[] args) {
		Thread t1 = new TestConsole("Thread A");
		Thread t2 = new TestConsole("Thread B");
		System.out.println("Going to start");
		t1.start();
		System.out.println("A started");
		t2.start();
		System.out.println("B started");
	}
	
	public TestConsole(String threadName) {
		this.setName(threadName);
	}
	
	public void start() {
		this.Som();
	}
	
	private void Som() {
		int g1 = Console.readInt(this.getName() + ": getal 1? ");
		int g2 = Console.readInt(this.getName() + ": getal 2? ");
		Console.println(this.getName() + ": " + g1 + " + " + g2 + " = " + (g1+g2));
	}
}
