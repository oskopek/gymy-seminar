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

package sk.gymy.seminar.domain.solver;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;

public class SeminarDifficultyWeightFactory implements SelectionSorterWeightFactory<Groups, Seminar> {

    public Comparable createSorterWeight(Groups groups, Seminar seminar) {
        return new SeminarDifficultyWeight(seminar, seminar.getStudents().size());
    }

    public static class SeminarDifficultyWeight implements Comparable<SeminarDifficultyWeight> {

        private final Seminar seminar;
        private final int studentsCount;

        public SeminarDifficultyWeight(Seminar seminar, int studentsCount) {
            this.seminar = seminar;
            this.studentsCount = studentsCount;
        }

        public int compareTo(SeminarDifficultyWeight other) { // TODO: non null
            return new CompareToBuilder() // Decreasing order
                    // The more difficult seminars have a higher number of students
                    .append(other.studentsCount, studentsCount).append(other.seminar.getIndex(),
                            seminar.getIndex()) // Tie-breaker
                    .toComparison();
        }

    }

}
