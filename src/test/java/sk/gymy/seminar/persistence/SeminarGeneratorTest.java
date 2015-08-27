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
import org.mockito.Mockito;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.common.AbstractTest;
import sk.gymy.seminar.domain.GroupSolution;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SeminarGeneratorTest extends AbstractTest {

    /**
     * Shouldn't actually write anything to the filesystem.
     */
    @Test
    public void testGenerate() {
        SolutionDao daoMock = Mockito.mock(SeminarDao.class);
        Mockito.when(daoMock.getDataDir()).thenReturn(new File("/tmp/generateTestDir"));
        SeminarGenerator generator = new SeminarGenerator(daoMock);
        generator.generate();
        Mockito.verify(daoMock).writeSolution(Mockito.any(GroupSolution.class),
                Mockito.eq(new File("/tmp/generateTestDir/unsolved/G3Ch2St20Tea6Sem15-seminar.xml")));
    }

    @Test
    public void testCreateGroups() {
        SeminarGenerator generator = new SeminarGenerator();
        GroupSolution groupSolution = generator.createGroups(3, 3, 20, 6, 18);
        assertNotNull(groupSolution);
        assertEquals("G3Ch3St20Tea6Sem18", groupSolution.getName());
        assertEquals(3, groupSolution.getN());
        assertEquals(3, groupSolution.getChooseSeminars());
        assertEquals(20, groupSolution.getStudentList().size());
        assertEquals(6, groupSolution.getTeacherList().size());
        assertEquals(18, groupSolution.getSeminarList().size());
        assertNull(groupSolution.getScore());
    }
}
