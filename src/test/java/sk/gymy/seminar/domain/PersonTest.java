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

public class PersonTest {

    @Test
    public void equalsAndHashCodeTest() {
        Person p1 = new Person();
        Person p2 = new Person();
        assertEquals(p1, p2);
        assertFalse(p1 == p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        p1.setIndex(1);
        assertNotEquals(p1, p2);
        assertFalse(p1 == p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
        p2.setIndex(1);
        assertEquals(p1, p2);
        assertFalse(p1 == p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        p1.setIndex(2);
        assertNotEquals(p1, p2);
        assertFalse(p1 == p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
        p2.setIndex(p1.getIndex());
        p1.setName("");
        p2.setName("");
        assertEquals(p1, p2);
        assertFalse(p1 == p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        p1.setName("Anna Harbour");
        assertNotEquals(p1, p2);
        assertFalse(p1 == p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
        p2.setName("Anna Harbour");
        assertEquals(p1, p2);
        assertFalse(p1 == p2);
        assertEquals(p1.hashCode(), p2.hashCode());

    }

    @Test
    public void toStringTest() {
        Person person = new Person();
        assertEquals(null, person.toString());
        person.setIndex(1234);
        assertEquals(null, person.toString());
        person.setName("John Stuart");
        assertEquals("John Stuart", person.toString());
    }

}
