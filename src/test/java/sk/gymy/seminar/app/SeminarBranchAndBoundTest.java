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

import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchPhaseConfig;
import org.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchType;
import org.optaplanner.core.config.phase.PhaseConfig;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.common.app.SolverPerformanceTest;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.persistence.SeminarDao;

import java.io.File;
import java.util.Collections;

public class SeminarBranchAndBoundTest extends SolverPerformanceTest {

    @Override
    protected String createSolverConfigResource() {
        return "sk/gymy/seminar/solver/seminarSolverConfig.xml";
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new SeminarDao();
    }

    @Override
    protected SolverFactory buildSolverFactory(String bestScoreLimitString, EnvironmentMode environmentMode) {
        SolverFactory solverFactory = super.buildSolverFactory(bestScoreLimitString, environmentMode);
        ExhaustiveSearchPhaseConfig phaseConfig = new ExhaustiveSearchPhaseConfig();
        phaseConfig.setExhaustiveSearchType(
                ExhaustiveSearchType.BRANCH_AND_BOUND);
        solverFactory.getSolverConfig().setPhaseConfigList(
                Collections.<PhaseConfig>singletonList(phaseConfig)
        );
        return solverFactory;
    }

    // ************************************************************************
    // Tests
    // ************************************************************************

    @Test(timeout = 600000)
    public void solveModel_simple5() {
        runSpeedTest(new File("data/seminar/unsolved/simple5.xml"), "0hard/0soft", EnvironmentMode.REPRODUCIBLE);
    }

    @Test(timeout = 600000)
    public void solveModel_unsolvable5() {
        runSpeedTest(new File("data/seminar/unsolved/unsolvable5.xml"), "0hard/-1soft", EnvironmentMode.REPRODUCIBLE);
    }

    @Test(timeout = 600000)
    public void solveModel_gymy2014_2() {
        runSpeedTest(new File("data/seminar/unsolved/gymy2014-2.xml"), "-22hard/-17soft", EnvironmentMode.REPRODUCIBLE);
    }

    @Test(timeout = 600000)
    public void solveModel_gymy2014_4() {
        runSpeedTest(new File("data/seminar/unsolved/gymy2014-4.xml"), "-8hard/-4soft", EnvironmentMode.REPRODUCIBLE);
    }

}