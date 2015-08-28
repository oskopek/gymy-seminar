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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.GroupSolution;
import sk.gymy.seminar.domain.SeminarAssignment;
import sk.gymy.seminar.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class GroupStrengthWeightFactory implements SelectionSorterWeightFactory<GroupSolution, Group> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupStrengthWeightFactory.class);

    public Comparable createSorterWeight(GroupSolution groupSolution, Group group) {
        int seminarCount = countSeminars(group, groupSolution.getSeminarAssignmentList());
        return new GroupStrengthWeight(group, seminarCount);
    }

    private static int countSeminars(Group group, List<SeminarAssignment> seminarAssignmentList) {
        if (group == null) {
            LOGGER.debug("Group to count seminars is null, returning 0 seminar count.");
            return 0;
        }
        int counter = 0;
        List<Student> studentsInGroupList = new ArrayList<>();
        for (SeminarAssignment seminarAssignment : seminarAssignmentList) {
            Group otherGroup = seminarAssignment.getGroup();
            Student otherStudent = seminarAssignment.getStudentAssignment().getStudent();
            if (otherGroup != null && otherGroup.equals(group)
                    && otherStudent != null && !studentsInGroupList.contains(otherStudent)) {
                counter++;
                studentsInGroupList.add(otherStudent);
            }
        }
        return counter;
    }

    public static class GroupStrengthWeight implements Comparable<GroupStrengthWeight> {

        private final Group group;
        private final int seminarCount;

        public GroupStrengthWeight(Group group, int seminarCount) {
            this.group = group;
            this.seminarCount = seminarCount;
        }

        public Group getGroup() {
            return group;
        }

        public int getSeminarCount() {
            return seminarCount;
        }

        public int compareTo(GroupStrengthWeight other) { // TODO: non null
            return new CompareToBuilder()
                    // The stronger groups are those with more seminars
                    .append(seminarCount, other.seminarCount).append(group.getIndex(), other.group.getIndex()) //
                    // Tie-breaker
                    .toComparison();
        }

    }

}
