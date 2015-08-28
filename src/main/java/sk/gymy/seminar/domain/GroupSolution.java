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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.buildin.hardmediumsoft.HardMediumSoftScoreDefinition;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.impl.score.XStreamScoreConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@PlanningSolution
@XStreamAlias("GroupSolution")
public class GroupSolution extends AbstractPersistable implements Solution<HardMediumSoftScore> {

    private String name;
    private int n;
    private int chooseSeminars;

    private int minSubSeminarIndex;
    private int maxSubSeminarIndex;

    // Problem facts
    private List<Student> studentList;
    private List<Group> groupList;
    private List<Teacher> teacherList;
    private List<Seminar> seminarList;

    // Planning entities
    private List<SeminarAssignment> seminarAssignmentList;

    @XStreamConverter(value = XStreamScoreConverter.class, types = {HardMediumSoftScoreDefinition.class})
    private HardMediumSoftScore score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getChooseSeminars() {
        return chooseSeminars;
    }

    public void setChooseSeminars(int chooseSeminars) {
        this.chooseSeminars = chooseSeminars;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Seminar> getSeminarList() {
        return seminarList;
    }

    public void setSeminarList(List<Seminar> seminarList) {
        this.seminarList = seminarList;
        this.maxSubSeminarIndex = Collections.max(seminarList, new Comparator<Seminar>() {
            @Override
            public int compare(Seminar o1, Seminar o2) {
                return Integer.compare(o1.getMaxSubSeminarIndex(), o2.getMaxSubSeminarIndex());
            }
        }).getMaxSubSeminarIndex();
        this.minSubSeminarIndex = Collections.min(seminarList, new Comparator<Seminar>() {
            @Override
            public int compare(Seminar o1, Seminar o2) {
                return Integer.compare(o1.getMinSubSeminarIndex(), o2.getMinSubSeminarIndex());
            }
        }).getMinSubSeminarIndex();
    }

    @ValueRangeProvider(id = "groupRange")
    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @ValueRangeProvider(id = "subSeminarIndexRange")
    public CountableValueRange<Integer> getSubSeminarIndexRange() {
        return ValueRangeFactory.createIntValueRange(minSubSeminarIndex, maxSubSeminarIndex);
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @PlanningEntityCollectionProperty
    public List<SeminarAssignment> getSeminarAssignmentList() {
        return seminarAssignmentList;
    }

    public void setSeminarAssignmentList(List<SeminarAssignment> seminarAssignmentList) {
        this.seminarAssignmentList = seminarAssignmentList;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GroupSolution{name=" + name + ", score=" + score + "}";
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public Collection<?> getProblemFacts() {
        List<Object> facts = new ArrayList<>();
        facts.addAll(studentList);
        facts.addAll(groupList);
        facts.addAll(teacherList);
        facts.addAll(seminarList);
        facts.add(new Integer(chooseSeminars));
        // Do not add the planning entity's (seminarAssignmentList) because that will be done automatically
        return facts;
    }

}
