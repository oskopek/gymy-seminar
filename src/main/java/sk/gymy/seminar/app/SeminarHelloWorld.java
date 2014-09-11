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
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.persistence.SeminarImporter;

import java.io.File;
import java.util.List;

public class SeminarHelloWorld {

    public static void main(String[] args) {
        System.out.println(solveHelloWorld());
    }

    public static String solveHelloWorld() {
        // Build the Solver
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(
                "sk/gymy/seminar/solver/seminarSolverConfig.xml");

        String unsolved5SeminarPath = "data/seminar/import/simple5.sem";
        Solver solver = solverFactory.buildSolver();

        // Load a problem with 5 seminars
        Groups unsolved5Seminars = (Groups) new SeminarImporter().readSolution(new File(unsolved5SeminarPath));

        // Solve the problem
        solver.solve(unsolved5Seminars);
        Groups solved5Seminars = (Groups) solver.getBestSolution();

        // Display the result
        return "\nSolved 5 Seminars:\n" + toDisplayString(solved5Seminars);
    }

    public static String toDisplayString(Groups groups) {
        StringBuilder displayString = new StringBuilder();
        List<Seminar> seminarList = groups.getSeminarList();
        List<Group> groupList = groups.getGroupList();

        for (Group group : groupList) {
            displayString.append("Group ").append(group.getIndex()).append(": ");
            for (Seminar seminar : seminarList) {
                if (seminar != null && seminar.getGroup() != null && seminar.getGroup() == group) {
                    displayString.append(seminar.getName()).append(", ");
                }
            }
            displayString.append("\n");
        }

        return displayString.toString();
    }
}
