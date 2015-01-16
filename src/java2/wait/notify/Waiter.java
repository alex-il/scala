/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

package java2.wait.notify;
import java.util.Date;

public class Waiter implements Runnable {

	Message message;

	public Waiter(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		synchronized (message) {
			try {
				System.out.println("Waiter== is waiting for the notifier at " + new Date());
				message.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("=======================================");
		System.out.println("Waiter is done waiting at " + new Date());
		System.out.println("Waiter got the message: " + message.getText());
		System.out.println("=======================================");
	}

}
