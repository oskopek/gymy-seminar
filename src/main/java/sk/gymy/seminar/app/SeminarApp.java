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

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.localsearch.decider.acceptor.AcceptorConfig;
import org.optaplanner.core.config.phase.PhaseConfig;
import org.optaplanner.core.config.score.definition.ScoreDefinitionType;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.persistence.SeminarDao;
import sk.gymy.seminar.persistence.SeminarExporter;
import sk.gymy.seminar.persistence.SeminarImporter;
import sk.gymy.seminar.swingui.SeminarPanel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SeminarApp extends CommonApp {

    public static final String SOLVER_CONFIG
            = "sk/gymy/seminar/solver/seminarSolverConfig.xml";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        prepareDataDirStructure();
        new SeminarApp().init();
    }

    public SeminarApp() {
        super("Seminar",
                "Place seminars into groups.\n\n" +
                        "No 2 Students must have two seminars in one group.",
                SeminarPanel.LOGO_PATH
        );
    }

    @Override
    protected Solver createSolver() {
        return createSolverByXml();
    }

    /**
     * Normal way to create a {@link org.optaplanner.core.api.solver.Solver}.
     *
     * @return never null
     */
    protected Solver createSolverByXml() {
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        return solverFactory.buildSolver();
    }

    /**
     * Unused alternative. Abnormal way to create a {@link org.optaplanner.core.api.solver.Solver}.
     * <p/>
     * Not recommended! It is recommended to use {@link #createSolverByXml()} instead.
     *
     * @return never null
     */
    protected Solver createSolverByApi() {
        SolverConfig solverConfig = new SolverConfig();

        solverConfig.setSolutionClass(Groups.class);
        solverConfig.setEntityClassList(Collections.<Class<?>>singletonList(Seminar.class));

        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = new ScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setScoreDefinitionType(ScoreDefinitionType.SIMPLE);
        scoreDirectorFactoryConfig.setScoreDrlList(
                Arrays.asList("sk/gymy/seminar/solver/seminarScoreRules.drl"));
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);

        TerminationConfig terminationConfig = new TerminationConfig();
        terminationConfig.setBestScoreLimit("0");
        solverConfig.setTerminationConfig(terminationConfig);
        List<PhaseConfig> phaseConfigList = new ArrayList<>();
        ConstructionHeuristicPhaseConfig constructionHeuristicSolverPhaseConfig
                = new ConstructionHeuristicPhaseConfig();
        constructionHeuristicSolverPhaseConfig.setConstructionHeuristicType(
                ConstructionHeuristicType.FIRST_FIT);
        phaseConfigList.add(constructionHeuristicSolverPhaseConfig);
        LocalSearchPhaseConfig localSearchPhaseConfig = new LocalSearchPhaseConfig();
        ChangeMoveSelectorConfig changeMoveSelectorConfig = new ChangeMoveSelectorConfig();
        changeMoveSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        localSearchPhaseConfig.setMoveSelectorConfig(changeMoveSelectorConfig);
        AcceptorConfig acceptorConfig = new AcceptorConfig();
        acceptorConfig.setEntityTabuSize(5);
        localSearchPhaseConfig.setAcceptorConfig(acceptorConfig);
        phaseConfigList.add(localSearchPhaseConfig);
        solverConfig.setPhaseConfigList(phaseConfigList);
        return solverConfig.buildSolver();
    }

    @Override
    protected SolutionPanel createSolutionPanel() {
        return new SeminarPanel();
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new SeminarDao();
    }

    @Override
    protected AbstractSolutionImporter createSolutionImporter() {
        return new SeminarImporter();
    }

    @Override
    protected AbstractSolutionExporter createSolutionExporter() {
        return new SeminarExporter();
    }

    private static void prepareDataDirStructure() {
        String dataDirPath = "data/" + SeminarDao.dataDirName + "/";
        List<String> dataDirs = Arrays.asList("import", "export", "solved", "unsolved");
        File dir;

        for(String curDataDir : dataDirs) {
            dir = new File(dataDirPath + curDataDir);
            if (!dir.exists()) {
                logger.info("Data directory {} doesn't exist, creating it.", dir.toString());
                if (!dir.mkdirs()) {
                    throw new IllegalStateException("Failed to create dataDir folders: " + dir.toString());
                }
            }
        }
    }

}
