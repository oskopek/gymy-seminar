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

package sk.gymy.seminar.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class TeacherTest {

    @Test
    public void equalsAndHashCodeTest() {
        Teacher t1 = new Teacher();
        Teacher t2 = new Teacher();
        assertEquals(t1, t2);
        assertFalse(t1 == t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        t1.setIndex(1);
        assertNotEquals(t1, t2);
        assertFalse(t1 == t2);
        assertNotEquals(t1.hashCode(), t2.hashCode());
        t2.setIndex(1);
        assertEquals(t1, t2);
        assertFalse(t1 == t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        t1.setIndex(2);
        assertNotEquals(t1, t2);
        assertFalse(t1 == t2);
        assertNotEquals(t1.hashCode(), t2.hashCode());
        t2.setIndex(t1.getIndex());
        t1.setName("");
        t2.setName("");
        assertEquals(t1, t2);
        assertFalse(t1 == t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        t1.setName("Anna Harbour");
        assertNotEquals(t1, t2);
        assertFalse(t1 == t2);
        assertNotEquals(t1.hashCode(), t2.hashCode());
        t2.setName("Anna Harbour");
        assertEquals(t1, t2);
        assertFalse(t1 == t2);
        assertEquals(t1.hashCode(), t2.hashCode());

    }

    @Test
    public void toStringTest() {
        Teacher g = new Teacher();
        assertEquals(null, g.toString());
        g.setIndex(1234);
        assertEquals(null, g.toString());
        g.setName("John Stuart");
        assertEquals("John Stuart", g.toString());
    }

}
