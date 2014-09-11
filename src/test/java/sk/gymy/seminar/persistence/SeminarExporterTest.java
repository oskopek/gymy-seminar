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
import sk.gymy.seminar.domain.Groups;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarExporterTest {

    private Path outputPath;

    @Before
    public void setUp() throws IOException {
        outputPath = Files.createTempFile("seminarExportTest", "." + new SeminarExporter().getOutputFileSuffix());
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
        Groups groups = (Groups) new SeminarImporter().readSolution(new File("data/seminar/import/simple5.sem"));
        assertNotNull(groups);
        assertEquals("Seminar-simple-5", groups.getName());
        assertEquals(3, groups.getN());
        assertEquals(20, groups.getStudentList().size());
        assertEquals(3, groups.getTeacherList().size());
        assertEquals(5, groups.getSeminarList().size());
        assertNull(groups.getScore());

        SeminarExporter seminarExporter = new SeminarExporter();
        seminarExporter.writeSolution(groups, outputPath.toFile());
        final String expected = "Name: Seminar-simple-5\n" +
                                "Groups: 3\n" +
                                "\n" +
                                "Group 0: \n" +
                                "Group 1: \n" +
                                "Group 2: ";
        List<String> result = Files.readAllLines(outputPath, Charset.defaultCharset());
        String[] resultArr = new String[result.size()];
        assertArrayEquals(expected.split("\n"), result.toArray(resultArr));
    }

}
