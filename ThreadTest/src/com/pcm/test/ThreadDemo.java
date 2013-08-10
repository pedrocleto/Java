/**
 * 
 */
package com.pcm.test;

/**
 * @author Pedro
 *
 **/



public class ThreadDemo{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new NewThread(); // create a new thread
	      try {
	         for(int i = 5; i > 0; i--) {
	           System.out.println("Main Thread: " + i);
	           Thread.sleep(1000);
	         }
	      } catch (InterruptedException e) {
	         System.out.println("Main thread interrupted.");
	      }
	      System.out.println("Main thread exiting.");
	}

}

class NewThread implements Runnable
{
	Thread t;
	NewThread() {
	      // Create a new, second thread
	      t = new Thread(this, "Demo Thread");
	      System.out.println("Child thread: " + t);
	      t.start(); // Start the thread
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	      try {
	         for(int i = 5; i > 0; i--) {
	            System.out.println("Child Thread: " + i);
	            // Let the thread sleep for a while.
	            Thread.sleep(500);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Child interrupted.");
	     }
	     System.out.println("Exiting child thread.");
	}
}
