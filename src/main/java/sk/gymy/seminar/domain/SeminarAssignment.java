package sk.gymy.seminar.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import sk.gymy.seminar.domain.solver.GroupStrengthWeightFactory;
import sk.gymy.seminar.domain.solver.MovableSeminarAssignmentSelectionFilter;
import sk.gymy.seminar.domain.solver.SubSeminarIndexStrengthWeightFactory;

@PlanningEntity(movableEntitySelectionFilter = MovableSeminarAssignmentSelectionFilter.class)
@XStreamAlias("SeminarAssignment")
public class SeminarAssignment extends AbstractPersistable{


    private int index;
    private StudentAssignment studentAssignment;
    private boolean locked;

    // Planning variables
    private Group group;
    private Integer subSeminarIndex;

    public SeminarAssignment() {
        super();
    }

    public SeminarAssignment(int index, StudentAssignment studentAssignment) {
        super();
        this.index = index;
        this.studentAssignment = studentAssignment;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @PlanningVariable(valueRangeProviderRefs = {"groupRange"},
            strengthWeightFactoryClass = GroupStrengthWeightFactory.class)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public StudentAssignment getStudentAssignment() {
        return studentAssignment;
    }

    public void setStudentAssignment(StudentAssignment studentAssignment) {
        this.studentAssignment = studentAssignment;
    }

    @PlanningVariable(valueRangeProviderRefs = {"subSeminarIndexRange"},
            strengthWeightFactoryClass = SubSeminarIndexStrengthWeightFactory.class)
    public Integer getSubSeminarIndex() {
        return subSeminarIndex;
    }

    public void setSubSeminarIndex(Integer subSeminarIndex) {
        this.subSeminarIndex = subSeminarIndex;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeminarAssignment)) {
            return false;
        }
        SeminarAssignment that = (SeminarAssignment) o;
        return new EqualsBuilder().append(index, that.index).append(locked, that.locked)
                .append(studentAssignment, that.studentAssignment).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(index).append(studentAssignment).append(locked).toHashCode();
    }

    @Override
    public String toString() {
        return studentAssignment + ":" + subSeminarIndex + "@" + group;
    }
}
