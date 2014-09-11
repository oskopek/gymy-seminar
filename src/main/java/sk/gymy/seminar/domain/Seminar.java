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
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import sk.gymy.seminar.domain.solver.GroupStrengthWeightFactory;
import sk.gymy.seminar.domain.solver.MovableSeminarSelectionFilter;
import sk.gymy.seminar.domain.solver.SeminarDifficultyWeightFactory;

import java.util.List;

@PlanningEntity(difficultyWeightFactoryClass = SeminarDifficultyWeightFactory.class,
        movableEntitySelectionFilter = MovableSeminarSelectionFilter.class)
@XStreamAlias("Seminar")
public class Seminar extends AbstractPersistable {

    private int index;
    private String name;
    private boolean locked;
    private Teacher teacher;
    private List<Student> students;

    //planning variable
    private Group group;

    public Seminar() {
        super();
    }

    public Seminar(int index, String name, boolean locked, Teacher teacher, List<Student> students, Group group) {
        super();
        this.index = index;
        this.name = name;
        this.locked = locked;
        this.teacher = teacher;
        this.students = students;
        this.group = group;
    }

    @PlanningVariable(valueRangeProviderRefs = {"groupRange"}, strengthWeightFactoryClass = GroupStrengthWeightFactory.class)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return name + ": " + group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seminar seminar = (Seminar) o;

        return new EqualsBuilder() // do not append Group!
                .append(getIndex(), seminar.getIndex())
                .append(getName(), seminar.getName())
                .append(getStudents(), seminar.getStudents())
                .append(getTeacher(), seminar.getTeacher())
                .append(isLocked(), seminar.isLocked())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(index).append(name) // do not append Group!
                .append(students).append(teacher).append(locked).toHashCode();
    }
}
