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

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import sk.gymy.seminar.common.AbstractTest;
import sk.gymy.seminar.common.TurtleTest;

public class SeminarBenchmarkAppTest extends AbstractTest {

    @Test
    public void testSeminarBenchmarkApp() {
        SeminarBenchmarkApp sba = new SeminarBenchmarkApp();
        assertNotNull(sba);
    }

    @Test
    @TurtleTest
    @Ignore("Ign")
    public void testBuildAndBenchmark() {
        SeminarBenchmarkApp sba = new SeminarBenchmarkApp();
        assertNotNull(sba);
        sba.buildAndBenchmark(new String[0]);
    }
}
