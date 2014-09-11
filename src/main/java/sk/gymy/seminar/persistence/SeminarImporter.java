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

import com.google.common.math.BigIntegerMath;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.domain.Student;
import sk.gymy.seminar.domain.Teacher;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SeminarImporter extends AbstractTxtSolutionImporter {

    private static final String INPUT_FILE_SUFFIX = "sem";

    public static void main(String[] args) {
        new SeminarImporter().convertAll();
    }

    public SeminarImporter() {
        super(new SeminarDao());
    }

    @Override
    public String getInputFileSuffix() {
        return INPUT_FILE_SUFFIX;
    }

    public static String calculatePossibleSolutionSize(Groups groups) {
        int seminarN = groups.getSeminarList().size();
        int groupsN = groups.getN();
        int n = groupsN * seminarN;
        int k = seminarN;
        BigInteger possibleSolutionSize = BigIntegerMath.binomial(n, k);
        return getFlooredPossibleSolutionSize(possibleSolutionSize);
    }

    public TxtInputBuilder createTxtInputBuilder() {
        return new SeminarInputBuilder();
    }

    public static class SeminarInputBuilder extends TxtInputBuilder {

        public Solution readSolution() throws IOException {
            Groups groups = new Groups();
            groups.setId(0L);
            String name = readStringValue("Name:");
            groups.setName(name);
            int n = readIntegerValue("Groups:");
            groups.setN(n);
            int semNum = readIntegerValue("Seminars:");
            int studNum = readIntegerValue("Students:");
            int teachNum = readIntegerValue("Teachers:");
            int chooseSeminars = readIntegerValue("ChooseSeminars:");
            groups.setChooseSeminars(chooseSeminars);

            createGroups(groups);
            readTeacherList(groups, teachNum);
            readStudentList(groups, studNum);
            readSeminarList(groups, semNum);
            logger.info(
                    "Seminar {} - {} - has {} Students, {} Teachers, {} Seminars, {} Groups with a search space of {}.",
                    getInputId(), name, groups.getStudentList().size(), groups.getTeacherList().size(),
                    groups.getSeminarList().size(), groups.getGroupList().size(),
                    calculatePossibleSolutionSize(groups));
            return groups;
        }

        private Student findStudent(Integer index, List<Student> studentList) throws IllegalStateException {
            for (Student student : studentList) {
                if (student.getIndex() == index) {
                    return student;
                }
            }
            throw new IllegalStateException("No student found with index: " + index);
        }

        private Teacher findTeacher(String name, List<Teacher> teacherList) throws IllegalStateException {
            for (Teacher teacher : teacherList) {
                if (teacher.getName().equals(name)) {
                    return teacher;
                }
            }
            throw new IllegalStateException("No teacher found with name: " + name);
        }


        private void createGroups(Groups groups) {
            int n = groups.getN();
            ArrayList<Group> groupList = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                Group group = new Group();
                group.setIndex(i);
                group.setId((long) i);
                groupList.add(group);
            }
            groups.setGroupList(groupList);
        }

        private void readStudentList(Groups groups, int studNum) throws IOException {
            readEmptyLine();
            readConstantLine("STUDENTS:");
            List<Student> studentList = new ArrayList<>(studNum);

            for (int i = 0; i < studNum; i++) {
                Student student = new Student();
                student.setId((long) i);
                student.setIndex(i + 1);
                String line = bufferedReader.readLine();
                student.setName(line);
                studentList.add(student);
            }

            groups.setStudentList(studentList);
        }

        private void readTeacherList(Groups groups, int teachNum) throws IOException {
            readEmptyLine();
            readConstantLine("TEACHERS:");
            List<Teacher> teacherList = new ArrayList<>(teachNum);

            for (int i = 0; i < teachNum; i++) {
                Teacher teacher = new Teacher();
                teacher.setId((long) i);
                teacher.setIndex(i + 1);
                String line = bufferedReader.readLine();
                teacher.setName(line);
                teacherList.add(teacher);
            }

            groups.setTeacherList(teacherList);
        }

        private void readSeminarList(Groups groups, int semNum) throws IOException {
            readEmptyLine();
            readConstantLine("SEMINARS:");
            List<Seminar> seminarList = new ArrayList<>(semNum);

            for (int i = 0; i < semNum; i++) {
                Seminar seminar = new Seminar();
                seminar.setId((long) i);
                seminar.setIndex(i);
                seminar.setLocked(false);
                List<Student> seminarStudents = new ArrayList<>();

                String line = bufferedReader.readLine();
                String[] split = splitBySpace(line);
                seminar.setName(split[0]);
                seminar.setTeacher(findTeacher(split[1], groups.getTeacherList()));

                for (int j = 2; j < split.length; j++) {
                    Student student = findStudent(Integer.parseInt(split[j]), groups.getStudentList());
                    seminarStudents.add(student);
                }
                seminar.setStudents(seminarStudents);
                seminarList.add(seminar);
            }
            groups.setSeminarList(seminarList);
        }

    }

}
