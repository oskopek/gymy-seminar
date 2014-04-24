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

import org.optaplanner.core.impl.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.domain.Student;

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

            createGroups(groups, n);
            readStudentList(groups, studNum);
            readSeminarList(groups, semNum);
            BigInteger possibleSolutionSize = BigInteger.valueOf(2).pow(groups.getSeminarList().size());
            logger.info("Seminar {} - {} - has {} Students, {} Seminars, {} Groups with a search space of {}.",
                    getInputId(),
                    name,
                    groups.getStudentList().size(),
                    groups.getSeminarList().size(),
                    groups.getGroupList().size(),
                    getFlooredPossibleSolutionSize(possibleSolutionSize));
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

        private void createGroups(Groups groups, int n) {
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
            List<Student> studentList = new ArrayList<>();

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

        private void readSeminarList(Groups groups, int semNum) throws IOException {
            readEmptyLine();
            readConstantLine("SEMINARS:");
            List<Seminar> seminarList = new ArrayList<>();

            for (int i = 0; i < semNum; i++) {
                Seminar seminar = new Seminar();
                seminar.setId((long) i);
                seminar.setIndex(i);
                seminar.setLocked(false);
                List<Student> seminarStudents = new ArrayList<>();

                String line = bufferedReader.readLine();
                String[] split = splitBySpace(line);
                seminar.setName(split[0]);

                for (int j = 1; j < split.length; j++) {
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
