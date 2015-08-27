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

import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SeminarAppTest {

    private SeminarApp seminarApp;

    @Before
    public void setUp() {
        seminarApp = new SeminarApp();
    }

    @Test
    @Ignore("No X11 DISPLAY variable was set, but this program performed an operation which requires it.")
    public void testSwingUI() throws IOException {
        SeminarApp.prepareSwingEnvironment();
        SeminarApp.prepareDataDirStructure(Files.createTempDir());
        JWindow window = new JWindow();
        seminarApp.init(window, true);
        assertNotNull(seminarApp);
        window.dispose();
    }

    @Test
    public void testCreateMethods() {
        assertNotNull(seminarApp.createSolutionDao());
        assertNotNull(seminarApp.createSolutionImporters()[0]);
        assertNotNull(seminarApp.createSolutionExporter());
        assertNotNull(seminarApp.createSolutionPanel());
        assertNotNull(seminarApp.createSolver());
        assertNotNull(seminarApp.createSolverByApi());
        assertNotNull(seminarApp.createSolverByXml());
        assertNotNull(seminarApp.createSolutionBusiness());
    }

    @Test
    public void testPrepareDataDirStructure() throws IOException {
        File baseDir = Files.createTempDir();
        assertTrue(baseDir.exists());
        assertTrue(baseDir.isDirectory());
        SeminarApp.prepareDataDirStructure(baseDir);
        File dataDir = new File(baseDir.getPath() + "/data/seminar");
        assertTrue(dataDir.exists());
        assertTrue(dataDir.isDirectory());
        String[] dirNames = {"export", "import", "solved", "unsolved"};
        String[] dataDirList = dataDir.list();
        Arrays.sort(dataDirList);
        assertArrayEquals(dirNames, dataDirList);
        for (String dirName : dirNames) {
            File dir = new File(dataDir.getPath() + "/" + dirName);
            assertTrue(dir.exists());
            assertTrue(dir.isDirectory());
            assertNotNull(dir.listFiles());
            assertEquals(0, dir.listFiles().length);
            assertTrue(dir.delete());
            assertFalse(dir.exists());
        }
        assertTrue(dataDir.delete());
        assertFalse(dataDir.exists());
        assertTrue(dataDir.getParentFile().delete());
        assertFalse(dataDir.getParentFile().exists());
        assertTrue(baseDir.delete());
        assertFalse(baseDir.exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepareDataDirStructureBaseDirNotDir() throws IOException {
        File baseDir = File.createTempFile("dataDir", "");
        assertTrue(baseDir.exists());
        assertFalse(baseDir.isDirectory());
        try {
            SeminarApp.prepareDataDirStructure(baseDir);
        } catch (IllegalArgumentException iae) {
            assertTrue(baseDir.delete());
            assertFalse(baseDir.exists());
            throw iae;
        }
    }

    @Test(expected = IOException.class)
    public void testPrepareDataDirStructureBaseDirIllegal() throws IOException {
        File baseDir = Files.createTempDir();
        assertTrue(baseDir.setReadOnly());
        assertTrue(baseDir.exists());
        assertTrue(baseDir.isDirectory());
        try {
            SeminarApp.prepareDataDirStructure(baseDir);
        } catch (IOException ioe) {
            assertTrue(baseDir.delete());
            assertFalse(baseDir.exists());
            throw ioe;
        }
    }

}
