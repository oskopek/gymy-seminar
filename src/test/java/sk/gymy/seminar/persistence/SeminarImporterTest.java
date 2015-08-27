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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarImporterTest extends AbstractTest {

    private Path file;
    private Path dir;
    private Path fileEdited;

    @Before
    public void setUp() throws IOException {
        dir = Files.createTempDirectory("seminarImporterTest");
        file = Paths.get(dir.toString(), "simple5.sem");
        fileEdited = Paths.get(dir.toString(), "simple5_edit.sem");
        Files.copy(Paths.get("data/seminar/import/simple5.sem"), file);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(fileEdited);
        Files.deleteIfExists(file);
        Files.deleteIfExists(dir);
    }

    @Test
    public void testSeminarImporterMain() {
        SeminarImporter.main(new String[0]);
    }

    @Test
    public void testReadSolution() {
        SeminarImporter seminarImporter = new SeminarImporter();
        Groups groups = (Groups) seminarImporter.readSolution(new File("data/seminar/import/simple5.sem"));
        assertNotNull(groups);
        assertEquals("Seminar-simple-5", groups.getName());
        assertEquals(3, groups.getN());
        assertEquals(20, groups.getStudentList().size());
        assertEquals(3, groups.getTeacherList().size());
        assertEquals(5, groups.getSeminarList().size());
        assertNull(groups.getScore());
    }

    @Test(expected = IllegalStateException.class)
    public void testInputBuilderNonExistingTeacher() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileEdited.toFile()));
        String buffer;
        while ((buffer = br.readLine()) != null) {
            if (buffer.contains("s05 t02")) {
                buffer = buffer.replace("t02", "t04");
                assertEquals("s05 t04 2 5 7 8 12 15 16 19", buffer);
            }
            bw.write(buffer);
            bw.newLine();
            bw.flush();
        }
        br.close();
        bw.close();
        SeminarImporter seminarImporter = new SeminarImporter();
        seminarImporter.readSolution(fileEdited.toFile()); // IllegalStateException should be thrown here
    }

    @Test(expected = IllegalStateException.class)
    public void testInputBuilderNonExistingStudent() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileEdited.toFile()));
        String buffer;
        while ((buffer = br.readLine()) != null) {
            if (buffer.contains("s05 t02")) {
                buffer = buffer.replace("19", "21");
                assertEquals("s05 t02 2 5 7 8 12 15 16 21", buffer);
            }
            bw.write(buffer);
            bw.newLine();
            bw.flush();
        }
        br.close();
        bw.close();
        SeminarImporter seminarImporter = new SeminarImporter();
        seminarImporter.readSolution(fileEdited.toFile()); // IllegalStateException should be thrown here
    }

}
