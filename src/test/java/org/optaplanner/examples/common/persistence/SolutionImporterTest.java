/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.examples.common.persistence;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.optaplanner.examples.common.app.LoggingTest;
import org.optaplanner.examples.common.business.ProblemFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public abstract class SolutionImporterTest extends LoggingTest {

    protected static Collection<Object[]> getInputFilesAsParameters(AbstractSolutionImporter solutionImporter) {
        File importDir = solutionImporter.getInputDir();
        List<File> fileList = new ArrayList<File>(
                FileUtils.listFiles(importDir, new String[]{solutionImporter.getInputFileSuffix()}, true));
        Collections.sort(fileList, new ProblemFileComparator());
        List<Object[]> filesAsParameters = new ArrayList<Object[]>();
        for (File file : fileList) {
            filesAsParameters.add(new Object[]{file});
        }
        return filesAsParameters;
    }

    protected AbstractSolutionImporter solutionImporter;

    protected File importFile;

    protected SolutionImporterTest(File importFile) {
        this.importFile = importFile;
    }

    @Before
    public void setUp() {
        solutionImporter = createSolutionImporter();
    }

    protected abstract AbstractSolutionImporter createSolutionImporter();

    @Test
    public void readSolution() {
        solutionImporter.readSolution(importFile);
    }

}
