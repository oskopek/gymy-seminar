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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;

import java.util.List;

public class GroupStrengthWeightFactory implements SelectionSorterWeightFactory<Groups, Group> {

    public Comparable createSorterWeight(Groups groups, Group group) {
        int seminarCount = countSeminars(group, groups.getSeminarList());
        return new GroupStrengthWeight(group, seminarCount);
    }

    private static int countSeminars(Group group, List<Seminar> seminarList) {
        int counter = 0;
        for (Seminar seminar : seminarList) {
            if (seminar.getGroup() == group) {
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

        public int compareTo(GroupStrengthWeight other) {
            return new CompareToBuilder()
                    // The stronger rows are on the side, so they have a higher distance to the middle
                    .append(seminarCount, other.seminarCount)
                    .append(group.getIndex(), other.group.getIndex())
                    .toComparison();
        }

    }

}
