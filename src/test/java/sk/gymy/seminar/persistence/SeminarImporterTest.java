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

import org.junit.Test;
import sk.gymy.seminar.domain.Groups;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarImporterTest {

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

}
