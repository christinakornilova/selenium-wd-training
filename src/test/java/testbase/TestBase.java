package testbase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class TestBase {

    @Rule
    public TestName testName = new TestName();

    public Logger log;

    @Before
    public void showStartedTestName() {
        log = LogManager.getLogger(testName.getMethodName());
        log.info(testName.getMethodName() + " test is started" );
    }


}
