package sk.gymy.seminar.common;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.optaplanner.examples.common.app.LoggingMain;

public class DisplayTestRule extends LoggingMain implements TestRule {

    private final boolean displayAvailable;

    public DisplayTestRule() {
        String displayEnv = System.getenv("DISPLAY");
        logger.trace("DISPLAY={}", displayEnv);
        displayAvailable = displayEnv != null;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        DisplayTest displayTestAnnotation = description.getAnnotation(DisplayTest.class);
        logger.trace("DisplayTestAnnotation={}", displayTestAnnotation);
        if (displayTestAnnotation == null || (displayTestAnnotation != null && displayAvailable)) {
            return base;
        } else {
            return new DisplayTestStatement(description);
        }
    }

    private static final class DisplayTestStatement extends Statement {

        private final Description description;

        public DisplayTestStatement(Description description) {
            this.description = description;
        }

        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue("Display not available, display tests aren't being run. Ignored test: "
                    + description.getTestClass().getName() + "." + description.getMethodName(), false);
        }
    }
}
