package junit.extensions;

import junit.framework.*;

/**
 * A TestSuite for active Tests. I waits until all
 * active tests have terminated.
 * -- Aarhus Radisson Scandinavian Center 11th floor
 */ 
public class ActiveTestSuite extends TestSuite {
	private volatile int fActiveTestDeathCount;
	
	public void run(TestResult result) {
		fActiveTestDeathCount= 0;
		super.run(result);
		waitUntilFinished();
	}
	
	public void runTest(final Test test, final TestResult result) {
		Thread t= new Thread() {
			public void run() {
				try {
					// inlined due to limitation in VA/Java 
					//ActiveTestSuite.super.runTest(test, result);
					test.run(result);
				} finally {
					ActiveTestSuite.this.runFinished(test);
				}
			}
		};
		t.start();
	}

	synchronized void waitUntilFinished() {
		while (fActiveTestDeathCount < testCount()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return; // TBD
			}
		}
	}
	
	synchronized public void runFinished(Test test) {
		fActiveTestDeathCount++;
		notifyAll();
	}
}