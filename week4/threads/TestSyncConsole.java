
package week4.threads;

public class TestSyncConsole extends Thread {	
	
	
	public TestSyncConsole(String name) {
		super(name);
	}
	
	public synchronized void run() {
		som();
	}
	
	private synchronized void som() {
		int getal1 = SyncConsole.readInt(getName() + ": getal 1?");
		int getal2 = SyncConsole.readInt(getName() + ": getal 2?");
		int result = getal1+getal2;
		SyncConsole.println(getName() + ": " + getal1 + " + " + getal2 + " = " + result);
	}
	
	 public static void main(String[] args) {
		Thread t1 = new TestSyncConsole("Thread A");
		Thread t2 = new TestSyncConsole("Thread B");
		
		t1.start();
		try {
			t1.join();
		} catch(InterruptedException e) {}
		t2.start();
	}
	
}
/*package week4.threads;

public class TestSyncConsole extends Thread{
	public static void main(String[] args) {
		Thread t1 = new TestSyncConsole("Thread A");
		Thread t2 = new TestSyncConsole("Thread B");
		t1.start();
		t2.start();
	}
	
	private String threadName;
	
	public TestSyncConsole(String threadName) {
		this.setName(threadName);
	}
	
	public void start() {
		this.Som();
	}
	
	private void Som() {
		int g1 = SyncConsole.readInt(this.getName() + ": getal 1? ");
		int g2 = SyncConsole.readInt(this.getName() + ": getal 2? ");
		SyncConsole.println(this.getName() + ": " + g1 + " + " + g2 + " = " + (g1+g2));
	}
}
*/