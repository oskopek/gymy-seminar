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
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;

import java.util.List;

public class GroupStrengthWeightFactory implements SelectionSorterWeightFactory<Groups, Group> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupStrengthWeightFactory.class);

    public Comparable createSorterWeight(Groups groups, Group group) {
        int seminarCount = countSeminars(group, groups.getSeminarList());
        return new GroupStrengthWeight(group, seminarCount);
    }

    private static int countSeminars(Group group, List<Seminar> seminarList) {
        if (group == null) {
            LOGGER.error("Group to count seminars is null, returning 0 seminar count");
            return 0;
        }
        int counter = 0;
        for (Seminar seminar : seminarList) {
            if (seminar.getGroup() != null && seminar.getGroup().equals(group)) {
                counter++;
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
                    .append(seminarCount, other.seminarCount).append(group.getIndex(),
                            other.group.getIndex()) // Tie-breaker
                    .toComparison();
        }

    }

}
