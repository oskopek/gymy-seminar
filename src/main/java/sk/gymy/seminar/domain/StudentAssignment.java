package sk.gymy.seminar.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("StudentAssignment")
public class StudentAssignment extends AbstractPersistable {

    private Student student;

    private Seminar seminar;

    public StudentAssignment(Seminar seminar, Student student) {
        super();
        this.seminar = seminar;
        this.student = student;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentAssignment)) {
            return false;
        }
        StudentAssignment that = (StudentAssignment) o;
        return new EqualsBuilder().append(student, that.student).append(seminar, that.seminar).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(student).append(seminar).toHashCode();
    }

    @Override
    public String toString() {
        return "SA(" + student + "->" + seminar + ")";
    }
}
