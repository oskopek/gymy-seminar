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

import org.optaplanner.examples.common.app.CommonBenchmarkApp;

public class SeminarBenchmarkApp extends CommonBenchmarkApp {

    public static final String SOLVER_BENCHMARK_CONFIG = "sk/gymy/seminar/benchmark/seminarBenchmarkConfig.xml";

    public static void main(String[] args) {
        new SeminarBenchmarkApp().buildAndBenchmark(args);
    }

    public SeminarBenchmarkApp() {
        super(new ArgOption("default", SeminarBenchmarkApp.SOLVER_BENCHMARK_CONFIG));
//        super(new ArgOption("default", "sk/gymy/seminar/benchmark/seminarBenchmarkConfigCH.xml"));
//        super(new ArgOption("template", "sk/gymy/seminar/benchmark/seminarBenchmarkConfigLS.xml.ftl", true));
//        super(new ArgOption("template", "sk/gymy/seminar/benchmark/seminarBenchmarkConfigSA.xml.ftl", true));
    }

}
