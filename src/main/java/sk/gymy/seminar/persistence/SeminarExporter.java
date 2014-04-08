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

package sk.gymy.seminar.persistence;

import org.optaplanner.core.impl.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionExporter;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;

import java.io.IOException;

public class SeminarExporter extends AbstractTxtSolutionExporter {

    private static final String OUTPUT_FILE_SUFFIX = ".sem.txt";

    public static void main(String[] args) {
        new SeminarExporter().convertAll();
    }

    public SeminarExporter() {
        super(new SeminarDao());
    }

    @Override
    protected String getOutputFileSuffix() {
        return OUTPUT_FILE_SUFFIX;
    }

    public TxtOutputBuilder createTxtOutputBuilder() {
        return new SeminarOutputBuilder();
    }

    public static class SeminarOutputBuilder extends TxtOutputBuilder {

        private Groups groups;

        public void setSolution(Solution solution) {
            groups = (Groups) solution;
        }

        public void writeSolution() throws IOException {
            bufferedWriter.write("Name: " + groups.getName() + "\n");
            bufferedWriter.write("Groups: " + groups.getN() + "\n");
            bufferedWriter.newLine();
            for (Group group : groups.getGroupList()) {
                bufferedWriter.write("Group " + group.getIndex() + ": ");
                for (Seminar seminar : groups.getSeminarList()) {
                    if (seminar.getGroup() == group) {
                        bufferedWriter.write(seminar.getName() + ", ");
                    }
                }
                bufferedWriter.newLine();
            }
        }

    }

}
