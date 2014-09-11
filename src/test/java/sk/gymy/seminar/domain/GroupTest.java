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

public class GroupTest {

    @Test
    public void equalsAndHashCodeTest() {
        Group g1 = new Group();
        Group g2 = new Group();
        assertEquals(g1, g2);
        assertFalse(g1 == g2);
        assertEquals(g1.hashCode(), g2.hashCode());
        g1.setIndex(1);
        assertNotEquals(g1, g2);
        assertFalse(g1 == g2);
        assertNotEquals(g1.hashCode(), g2.hashCode());
        g2.setIndex(1);
        assertEquals(g1, g2);
        assertFalse(g1 == g2);
        assertEquals(g1.hashCode(), g2.hashCode());
        g1.setIndex(2);
        assertNotEquals(g1, g2);
        assertFalse(g1 == g2);
        assertNotEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    public void toStringTest() {
        Group g = new Group();
        assertEquals("G0", g.toString());
        g.setIndex(1234);
        assertEquals("G1234", g.toString());
        g.setIndex(-5);
        assertEquals("G-5", g.toString());
    }

}
