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

import org.junit.Ignore;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;

import javax.swing.*;

import static org.junit.Assert.assertNotNull;

public class SeminarAppTest {

    @Test
    public void testCreateSolverByApi() {
        SeminarApp seminarApp = new SeminarApp();
        Solver solver = seminarApp.createSolverByApi();
        assertNotNull(solver);
    }

    @Test
    public void testCreateSolver() {
        SeminarApp seminarApp = new SeminarApp();
        Solver solver = seminarApp.createSolver();
        assertNotNull(solver);
    }

    @Test
    @Ignore("No X11 DISPLAY variable was set, but this program performed an operation which requires it.")
    public void testSwingUI() {
        SeminarApp.prepareSwingEnvironment();
        SeminarApp.prepareDataDirStructure();
        SeminarApp app = new SeminarApp();
        JWindow window = new JWindow();
        app.init(window, true);
        assertNotNull(app);
        window.dispose();
    }

}
