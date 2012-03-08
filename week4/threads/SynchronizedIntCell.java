package week4.threads;

public class SynchronizedIntCell implements IntCell {
    private int value = 0;

	boolean containsReadVariable;
	
	@Override
	public synchronized void setValue(int val) {
		while (!containsReadVariable) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		containsReadVariable = false;
		notifyAll();
		this.value = val;
	}

	@Override
	public synchronized int getValue() {
		while (containsReadVariable) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		containsReadVariable = true;
		notifyAll();
		return value;
	}

}
