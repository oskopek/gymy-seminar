package sk.gymy.seminar.common;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.optaplanner.examples.common.app.LoggingMain;

public class TurtleTestRule extends LoggingMain implements TestRule {

    private final boolean runTurtleTests;

    public TurtleTestRule() {
        logger.trace("runTurtleTests={}", System.getProperty("runTurtleTests"));
        runTurtleTests = System.getProperty("runTurtleTests") != null;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        TurtleTest turtleTestAnnotation = description.getAnnotation(TurtleTest.class);
        logger.trace("TurtleTestAnnotation={}", turtleTestAnnotation);
        if (turtleTestAnnotation == null || (turtleTestAnnotation != null && runTurtleTests)) {
            return base;
        } else {
            return new TurtleTestStatement(description);
        }
    }

    private static final class TurtleTestStatement extends Statement {

        private final Description description;

        public TurtleTestStatement(Description description) {
            this.description = description;
        }

        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue("Turtle tests aren't being run. Ignored test: " + description.getTestClass().getName()
                    + "." + description.getMethodName(), false);
        }
    }
}
