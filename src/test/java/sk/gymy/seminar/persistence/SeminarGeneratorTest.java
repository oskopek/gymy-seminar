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

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.domain.Groups;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarGeneratorTest {

    /**
     * Shouldn't actually write anything to the filesystem.
     */
    @Test
    public void testGenerate() {
        SolutionDao daoMock = Mockito.mock(SeminarDao.class);
        Mockito.when(daoMock.getDataDir()).thenReturn(new File("/tmp/generateTestDir"));
        SeminarGenerator generator = new SeminarGenerator(daoMock);
        generator.generate();
        Mockito.verify(daoMock).writeSolution(Mockito.any(Groups.class),
                Mockito.eq(new File("/tmp/generateTestDir/unsolved/G3St20Tea6Sem18-seminar.xml")));
    }

    @Test
    public void testCreateGroups() {
        SeminarGenerator generator = new SeminarGenerator();
        Groups groups = generator.createGroups(3, 20, 6, 18);
        assertNotNull(groups);
        assertEquals("G3St20Tea6Sem18", groups.getName());
        assertEquals(3, groups.getN());
        assertEquals(20, groups.getStudentList().size());
        assertEquals(6, groups.getTeacherList().size());
        assertEquals(18, groups.getSeminarList().size());
        assertNull(groups.getScore());
    }

    @Test
    @Ignore("Test pollutes the data/seminar/unsolved directory")
    public void testSeminarGeneratorMain() {
        SeminarGenerator.main(new String[0]);
    }

}
