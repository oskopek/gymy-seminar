/*
 * Copyright 2014 Ondrej Skopek
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package sk.gymy.seminar.app;

import org.junit.Rule;
import org.junit.Test;
import org.optaplanner.examples.common.app.PlannerBenchmarkTest;
import sk.gymy.seminar.common.DisplayTestRule;
import sk.gymy.seminar.common.TurtleTestRule;

import java.io.File;

public class SeminarBenchmarkTest extends PlannerBenchmarkTest {

    // TODO figure out how to inherit from AbstractTest
    @Rule
    public final TurtleTestRule turtleTestRule = new TurtleTestRule();

    @Rule
    public final DisplayTestRule displayTestRule = new DisplayTestRule();

    @Override
    protected String createBenchmarkConfigResource() {
        return SeminarBenchmarkApp.SOLVER_BENCHMARK_CONFIG;
    }

    // ************************************************************************
    // Tests
    // ************************************************************************

    @Test(timeout = 60000)
    public void benchmarkSimple5() {
        runBenchmarkTest(new File("data/seminar/unsolved/simple5.xml"));
    }

}
