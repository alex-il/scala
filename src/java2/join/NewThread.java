package java2.join;

class NewThread implements Runnable {
	String name; // name of thread
	Thread t;
	private int timeSec;

	NewThread(String threadname, int time) {
		name = threadname;
		this.timeSec = time;
		t = new Thread(this, name);
		System.out.println("----New thread: " + t);
		t.start(); // Start the thread
	}

	// This is the entry point for thread.
	public void run() {
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println(name + ": " + i);
				Thread.sleep(timeSec);
			}
		} catch (InterruptedException e) {
			System.out.println(name + " interrupted.");
		}
		System.out.println(name + " exiting."+System.currentTimeMillis());
	}
}