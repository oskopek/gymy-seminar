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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SeminarTest {

    @Test
    public void equalsAndHashCodeTest() {
        Seminar s1 = new Seminar();
        Seminar s2 = new Seminar();
        assertEquals(s1, s2);
        assertFalse(s1 == s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        Teacher teacher = new Teacher(1, "Anna Harbour");
        Group group = new Group(1);
        s1 = new Seminar(1, "ANJ", true, teacher, null, group);
        s2 = new Seminar(1, "ANJ", true, teacher, null, group);
        assertEquals(s1, s2);
        assertFalse(s1 == s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        List<Student> studentList1 = new ArrayList<>();
        studentList1.add(new Student(1, "Johan"));
        studentList1.add(new Student(2, "John"));
        s1.setStudents(studentList1);
        List<Student> studentList2 = new ArrayList<>();
        studentList2.add(new Student(1, "Johan"));
        studentList2.add(new Student(2, "John"));
        studentList2.add(new Student(3, "Jan"));
        s2.setStudents(studentList2);
        assertNotEquals(s1, s2);
        assertFalse(s1 == s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());

        s2.getStudents().remove(2);
        assertEquals(s1, s2);
        assertFalse(s1 == s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        // Unequal groups produce equal seminars, because a seminar's group is a Planning Variable
        s1.setGroup(new Group(2));
        assertNotEquals(s1.getGroup(), s2.getGroup());
        assertEquals(s1, s2);
        assertFalse(s1 == s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    public void toStringTest() {
        Seminar seminar = new Seminar();
        assertEquals("null: null", seminar.toString());
        seminar.setIndex(1234);
        assertEquals("null: null", seminar.toString());
        seminar.setName("ANJ");
        assertEquals("ANJ: null", seminar.toString());
        seminar.setGroup(new Group(12345));
        assertEquals("ANJ: G12345", seminar.toString());
    }

}
