/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

package java2.wait.notify;
public class WaitNotifyExample {

	public static void main(String[] args) {

		Message message = new Message("message init");

		Waiter waiter = new Waiter(message);
		Thread waiterThread = new Thread(waiter, "waiterThread---");
		waiterThread.start();

		Notifier notifier = new Notifier(message);
		Thread notifierThread = new Thread(notifier, "notifierThread+++");
		notifierThread.start();

	}

}
