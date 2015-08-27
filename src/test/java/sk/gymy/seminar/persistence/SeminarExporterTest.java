/*
 * Copyright 2014 Ondrej Skopek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sk.gymy.seminar.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sk.gymy.seminar.common.AbstractTest;
import sk.gymy.seminar.domain.Groups;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarExporterTest extends AbstractTest {

    private Path outputPath;
    private Groups groups;

    @Before
    public void setUp() throws IOException {
        outputPath = Files.createTempFile("seminarExportTest", "." + new SeminarExporter().getOutputFileSuffix());
        this.groups = (Groups) new SeminarImporter().readSolution(new File("data/seminar/import/simple5.sem"));
        assertNotNull(groups);
        assertEquals("Seminar-simple-5", groups.getName());
        assertEquals(3, groups.getN());
        assertEquals(20, groups.getStudentList().size());
        assertEquals(3, groups.getTeacherList().size());
        assertEquals(5, groups.getSeminarList().size());
        assertNull(groups.getScore());
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(outputPath);
    }

    @Test
    public void testSeminarExporterMain() {
        SeminarExporter.main(new String[0]);
    }

    @Test
    public void testWriteSolution() throws IOException {
        SeminarExporter seminarExporter = new SeminarExporter();
        seminarExporter.writeSolution(groups, outputPath.toFile());
        final String expected = "Name: Seminar-simple-5\n"
                + "Groups: 3\n"
                + "ChooseSeminars: 2\n"
                + "\n"
                + "Group 0: \n"
                + "Group 1: \n"
                + "Group 2: ";
        List<String> result = Files.readAllLines(outputPath, Charset.defaultCharset());
        String[] resultArr = new String[result.size()];
        assertArrayEquals(expected.split("\n"), result.toArray(resultArr));
    }

    @Test
    public void testOutputBuilder() throws IOException {
        SeminarExporter.SeminarOutputBuilder sob = new SeminarExporter.SeminarOutputBuilder();
        assertNotNull(sob);
        sob.setSolution(groups);
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        sob.setBufferedWriter(bw);
        sob.writeSolution();
        bw.close();
        assertNotEquals("Empty StringWriter", "", sw.toString());
        assertEquals(sw.getBuffer().substring(0), sw.toString());
        assertEquals("Name: Seminar-simple-5\nGroups: 3\nChooseSeminars: 2\n\nGroup 0: \nGroup 1: \nGroup 2: \n", sw.toString());
    }

}
