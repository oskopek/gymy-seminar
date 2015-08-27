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

package sk.gymy.seminar.swingui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import sk.gymy.seminar.app.SeminarApp;
import sk.gymy.seminar.common.AbstractTest;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.persistence.SeminarImporter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SeminarPanelTest extends AbstractTest {

    private SeminarPanel panel;

    private static Groups groups;
    private static Groups solvedGroups;

    @BeforeClass
    public static void setUpClass() {
        groups = (Groups) new SeminarImporter().readSolution(new File("data/seminar/import/simple5.sem"));
        assertNotNull(groups);
        assertEquals("Seminar-simple-5", groups.getName());
        assertEquals(3, groups.getN());
        assertEquals(20, groups.getStudentList().size());
        assertEquals(3, groups.getTeacherList().size());
        assertEquals(5, groups.getSeminarList().size());
        assertNull(groups.getScore());

        solvedGroups = groups;
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(SeminarApp.SOLVER_CONFIG);
        Solver solver = solverFactory.buildSolver();
        solver.solve(solvedGroups);
        solvedGroups = (Groups) solver.getBestSolution();
        assertNotEquals(solvedGroups, groups); // Asserts, that the groups stays uninitialized
        assertEquals(
                "Groups{name=Seminar-simple-5, score=-2hard/-20soft, seminarList=[s01: null, s02: null, s03: null, "
                        + "s04: null, s05: null]}", groups.toString());
        assertEquals(
                "Groups{name=Seminar-simple-5, score=0hard/0soft, seminarList=[s01: G2, s02: G0, s03: G0, s04: G1, "
                        + "s05: G2]}", solvedGroups.toString());
    }

    @Before
    public void setUp() {
        panel = new SeminarPanel();
        assertNotNull(panel);
        assertEquals(0, panel.getComponents().length);
    }

    @Test
    public void testPanelUninitialized() {
        panel.resetPanel(groups);
        assertEquals(3, panel.getComponents().length); // 3 group labels + 0 seminar buttons
    }

    @Test
    public void testPanelSolved() {
        panel.resetPanel(solvedGroups);
        assertEquals(8, panel.getComponents().length); // 3 group labels + 5 seminar buttons
    }

    @Test
    public void testLockIconDrawing() {
        solvedGroups.getSeminarList().get(0).setLocked(true);
        solvedGroups.getSeminarList().get(2).setLocked(true);
        panel.resetPanel(solvedGroups);
        assertEquals(8, panel.getComponents().length); // 3 group labels + 5 seminar buttons
        int buttonsWithLockIcons = 0;
        for (Component component : panel.getComponents()) {
            assertNotNull(component);
            JButton button;
            try {
                button = (JButton) component;
            } catch (ClassCastException cce) {
                continue; // if component isn't a button
            }
            if (button.getIcon() != null) {
                buttonsWithLockIcons++;
            }
        }
        assertEquals(2, buttonsWithLockIcons); // Two locked seminars
        solvedGroups.getSeminarList().get(0).setLocked(false);
        solvedGroups.getSeminarList().get(2).setLocked(false);
    }

}
