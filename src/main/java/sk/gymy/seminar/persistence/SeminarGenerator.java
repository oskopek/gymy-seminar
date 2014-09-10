/*
 * Copyright 2014 Ondrej Skopek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sk.gymy.seminar.persistence;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.domain.Student;
import sk.gymy.seminar.domain.Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeminarGenerator extends LoggingMain {

    private static final File outputDir = new File("data/seminar/unsolved/");

    protected SolutionDao solutionDao;

    private Multiset<Integer> studentSeminars;

    private Random random;

    public SeminarGenerator() {
        random = new Random();
        studentSeminars = HashMultiset.create();
    }

    public static void main(String[] args) {
        new SeminarGenerator().generate();
    }

    public void generate() {
        solutionDao = new SeminarDao();
        writeGroups(3, 20, 6, 18);
        //writeGroups(3, 200, 60, 180);
        //writeGroups(3, 2000, 600, 1800);
        //writeGroups(3, 2000, 600, 100);
    }

    private void writeGroups(int N, int studentN, int teacherN, int seminarN) {
        String outputFileName = "G" + N + "St" + studentN + "Tea" + teacherN + "Sem" + seminarN + "-seminar.xml";
        File outputFile = new File(outputDir, outputFileName);
        Groups groups = createGroups(N, studentN, teacherN, seminarN);
        solutionDao.writeSolution(groups, outputFile);
    }

    public Groups createGroups(int N, int studentN, int teacherN, int seminarN) {
        Groups groups = new Groups();
        groups.setId(0L);
        groups.setN(N);
        groups.setName("G" + N + "St" + studentN + "Tea" + teacherN + "Sem" + seminarN);
        groups.setStudentList(createStudentList(groups, studentN));
        groups.setTeacherList(createTeacherList(groups, teacherN));
        groups.setGroupList(createGroupList(groups, groups.getN()));
        groups.setSeminarList(createSeminarList(groups, seminarN));
        logger.info("Seminar has {} Students, {} Seminars, {} Groups with a search space of {}.",
                groups.getStudentList().size(),
                groups.getSeminarList().size(),
                groups.getGroupList().size(),
                SeminarImporter.calculatePossibleSolutionSize(groups));
        return groups;
    }

    private List<Student> createStudentList(Groups groups, int studentN) {
        final String base = "Stud";
        List<Student> studentList = new ArrayList<>(studentN);
        for (int i = 0; i < studentN; i++) {
            Student student = new Student();
            student.setId((long) i);
            student.setIndex(i + 1);
            student.setName(base + i);
            studentList.add(student);
        }
        return studentList;
    }

    private List<Teacher> createTeacherList(Groups groups, int teacherN) {
        final String base = "T";
        List<Teacher> teacherList = new ArrayList<>(teacherN);
        for (int i = 0; i < teacherN; i++) {
            Teacher teacher = new Teacher();
            teacher.setId((long) i);
            teacher.setIndex(i + 1);
            teacher.setName(base + i);
            teacherList.add(teacher);
        }
        return teacherList;
    }

    private List<Group> createGroupList(Groups groups, int groupN) {
        List<Group> groupList = new ArrayList<>(groupN);
        for (int i = 0; i < groupN; i++) {
            Group group = new Group();
            group.setId((long) i);
            group.setIndex(i);
            groupList.add(group);
        }
        return groupList;
    }

    private List<Seminar> createSeminarList(Groups groups, int seminarN) {
        final String base = "Sem";
        List<Seminar> seminarList = new ArrayList<>(seminarN);
        this.studentSeminars.clear();
        int teacherIndex = 0;
        for (int i = 0; i < seminarN; i++) {
            Seminar seminar = new Seminar();
            seminar.setId((long) i);
            seminar.setIndex(i);
            seminar.setLocked(false);
            seminar.setName(base + i);

            int numOfStudents = ((groups.getN()-1) * groups.getStudentList().size())/seminarN;
            List<Student> students = generateStudents(groups.getStudentList(), numOfStudents, groups.getN());
            seminar.setStudents(students);

            if(teacherIndex >= groups.getTeacherList().size()) {
                teacherIndex = 0;
            }
            seminar.setTeacher(groups.getTeacherList().get(teacherIndex));
            teacherIndex++;

            seminarList.add(seminar);
        }
        this.studentSeminars.clear();
        return seminarList;
    }

    private List<Student> generateStudents(List<Student> studentList, int n, int groupN) {
        List<Student> students = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int id = random.nextInt(studentList.size());
            if(studentSeminars.count(id) >= groupN) {
                i--;
                continue;
            }

            students.add(studentList.get(id));
            studentSeminars.add(id);
        }
        return students;
    }

}
