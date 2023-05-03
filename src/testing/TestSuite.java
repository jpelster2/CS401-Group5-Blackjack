package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	CardTest.class, DealerTest.class, GameTest.class, MessageTest.class, PlayerTest.class
})

public class TestSuite {

}
