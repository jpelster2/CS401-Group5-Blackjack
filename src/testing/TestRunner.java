package testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		Result testResults = JUnitCore.runClasses(TestSuite.class);
		
		for (Failure fail : testResults.getFailures()) {
			System.out.println(fail.toString());
		}
		
		System.out.println(testResults.wasSuccessful() ? "Test Success" : "Test Failure");
	}
}
