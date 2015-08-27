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
import org.junit.runners.Parameterized;
import org.optaplanner.examples.common.app.UnsolvedDirSolveAllTurtleTest;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.common.DisplayTestRule;
import sk.gymy.seminar.common.TurtleTest;
import sk.gymy.seminar.common.TurtleTestRule;
import sk.gymy.seminar.persistence.SeminarDao;

import java.io.File;
import java.util.Collection;

@TurtleTest
public class SeminarSolveAllTurtleTest extends UnsolvedDirSolveAllTurtleTest {

    // TODO figure out how to inherit from AbstractTest
    @Rule
    public final TurtleTestRule turtleTestRule = new TurtleTestRule();

    @Rule
    public final DisplayTestRule displayTestRule = new DisplayTestRule();

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getSolutionFilesAsParameters() {
        return getUnsolvedDirFilesAsParameters(new SeminarDao());
    }

    public SeminarSolveAllTurtleTest(File unsolvedDataFile) {
        super(unsolvedDataFile);
    }

    @Override
    protected String createSolverConfigResource() {
        return SeminarApp.SOLVER_CONFIG;
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new SeminarDao();
    }

}
