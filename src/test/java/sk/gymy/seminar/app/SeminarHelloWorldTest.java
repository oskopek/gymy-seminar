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

package sk.gymy.seminar.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeminarHelloWorldTest {

    @Test
    public void testHelloWorldMain() {
        SeminarHelloWorld.main(new String[0]);
    }

    @Test
    public void testHelloWorld() {
        final String expected = "\nSolved 5 Seminars:\n" +
                "Group 0: s04, \n" +
                "Group 1: s02, s03, \n" +
                "Group 2: s01, s05, \n";
        String result = SeminarHelloWorld.solveHelloWorld();
        assertEquals(expected, result);
    }
}

