package sk.gymy.seminar.common;

import org.junit.Rule;
import org.optaplanner.examples.common.app.LoggingMain;

public abstract class AbstractTest extends LoggingMain {

    @Rule
    public final TurtleTestRule turtleTestRule = new TurtleTestRule();

    @Rule
    public final DisplayTestRule displayTestRule = new DisplayTestRule();

}
