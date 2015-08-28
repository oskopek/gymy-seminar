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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Seminar")
public class Seminar extends AbstractPersistable {

    private int index;
    private String name;
    private Teacher teacher;
    private int minSubSeminarIndex;
    private int maxSubSeminarIndex;

    public Seminar() {
        super();
    }

    public Seminar(int index, int maxSubSeminarIndex, int minSubSeminarIndex, String name, Teacher teacher) {
        this.index = index;
        this.maxSubSeminarIndex = maxSubSeminarIndex;
        this.minSubSeminarIndex = minSubSeminarIndex;
        this.name = name;
        this.teacher = teacher;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxSubSeminarIndex() {
        return maxSubSeminarIndex;
    }

    public void setMaxSubSeminarIndex(int maxSubSeminarIndex) {
        this.maxSubSeminarIndex = maxSubSeminarIndex;
    }

    public int getMinSubSeminarIndex() {
        return minSubSeminarIndex;
    }

    public void setMinSubSeminarIndex(int minSubSeminarIndex) {
        this.minSubSeminarIndex = minSubSeminarIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return name + "/" + teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seminar)) {
            return false;
        }
        Seminar seminar = (Seminar) o;
        return new EqualsBuilder().append(index, seminar.index).append(minSubSeminarIndex, seminar.minSubSeminarIndex)
                .append(maxSubSeminarIndex, seminar.maxSubSeminarIndex).append(name, seminar.name)
                .append(teacher, seminar.teacher).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(index).append(name).append(teacher).append(minSubSeminarIndex)
                .append(maxSubSeminarIndex).toHashCode();
    }
}
