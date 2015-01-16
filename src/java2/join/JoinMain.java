package java2.join;

class JoinMain {
	public static void main(String args[]) {
		NewThread ob1 = new NewThread("One",1300);
		NewThread ob2 = new NewThread("Two", 666);
		NewThread ob3 = new NewThread("Three",200);
		System.out.println("Thread One is alive: " + ob1.t.isAlive());
		System.out.println("Thread Two is alive: " + ob2.t.isAlive());
		System.out.println("Thread Three is alive: " + ob3.t.isAlive());
		// wait for threads to finish
		try {
			System.out.println("Waiting for threads to finish.");
			ob1.t.join();
//			ob2.t.join();
//			ob3.t.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		System.out.println("Thread One is alive: " + ob1.t.isAlive());
		System.out.println("Thread Two is alive: " + ob2.t.isAlive());
		System.out.println("Thread Three is alive: " + ob3.t.isAlive());
		System.out.println("==========================Main thread exiting."+System.currentTimeMillis());
	}
}