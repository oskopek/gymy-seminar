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

import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.common.persistence.SolutionDao;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.GroupSolution;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.domain.Student;
import sk.gymy.seminar.domain.Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Best effort dataset generator. Doesn't guarantee validity or solvability (as that is itself NP-hard).
 */
public class SeminarGenerator extends LoggingMain {

    private SolutionDao solutionDao;

    private Random random;

    public SeminarGenerator() {
        this.random = new Random();
        this.solutionDao = new SeminarDao();
    }

    public SeminarGenerator(SolutionDao solutionDao) {
        this.random = new Random();
        this.solutionDao = solutionDao;
    }

    public static void main(String[] args) {
        new SeminarGenerator().generate();
    }

    public void generate() {
        writeGroups(3, 2, 20, 6, 15);
//        writeGroups(3, 2, 200, 60, 150);
//        writeGroups(3, 2, 2000, 600, 1500);
//        writeGroups(3, 5, 2000, 60, 125);
//        writeGroups(5, 3, 2000, 60, 125);
    }

    private void writeGroups(int N, int chooseSeminars, int studentN, int teacherN, int seminarN) {
        String outputFileName = "G" + N + "Ch" + chooseSeminars + "St" + studentN + "Tea" + teacherN + "Sem" + seminarN + "-seminar.xml";
        GroupSolution groupSolution = createGroups(N, chooseSeminars, studentN, teacherN, seminarN);
        solutionDao.writeSolution(groupSolution, new File(solutionDao.getDataDir().getPath() + "/unsolved/" + outputFileName));
    }

    public GroupSolution createGroups(int N, int chooseSeminars, int studentN, int teacherN, int seminarN) {
        GroupSolution groupSolution = new GroupSolution();
        groupSolution.setId(0L);
        groupSolution.setN(N);
        groupSolution.setChooseSeminars(chooseSeminars);
        groupSolution.setName("G" + N + "Ch" + chooseSeminars + "St" + studentN + "Tea" + teacherN + "Sem" + seminarN);
        groupSolution.setStudentList(createStudentList(groupSolution, studentN));
        groupSolution.setTeacherList(createTeacherList(groupSolution, teacherN));
        groupSolution.setGroupList(createGroupList(groupSolution, groupSolution.getN()));
        groupSolution.setSeminarList(createSeminarList(groupSolution, chooseSeminars, seminarN));
        logger.info("Seminar has {} Students, {} Seminars, {} Groups with a search space of {}.",
                groupSolution.getStudentList().size(), groupSolution.getSeminarList().size(), groupSolution.getGroupList().size(),
                SeminarImporter.calculatePossibleSolutionSize(groupSolution));
        return groupSolution;
    }

    private List<Student> createStudentList(GroupSolution groupSolution, int studentN) {
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

    private List<Teacher> createTeacherList(GroupSolution groupSolution, int teacherN) {
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

    private List<Group> createGroupList(GroupSolution groupSolution, int groupN) {
        List<Group> groupList = new ArrayList<>(groupN);
        for (int i = 0; i < groupN; i++) {
            Group group = new Group();
            group.setId((long) i);
            group.setIndex(i);
            groupList.add(group);
        }
        return groupList;
    }

    private List<Seminar> createSeminarList(GroupSolution groupSolution, int chooseSeminars, int seminarN) {
        final String base = "Sem";
        List<Seminar> seminarList = new ArrayList<>(seminarN);
        int teacherIndex = 0;
        Map<Student, Integer> availableStudentMap = new HashMap<>(groupSolution.getStudentList().size());
        for (Student student : groupSolution.getStudentList()) {
            availableStudentMap.put(student, chooseSeminars);
        }
        // Distribute the students as evenly as possible
        int maxStudentsInSeminar = ((groupSolution.getChooseSeminars() * groupSolution.getStudentList().size()) + 2 * seminarN) / seminarN;
        for (int i = 0; i < seminarN; i++) {
            Seminar seminar = new Seminar();
            seminar.setId((long) i);
            seminar.setIndex(i);
            seminar.setLocked(false);
            seminar.setName(base + i);
            List<Student> students = generateStudents(availableStudentMap, maxStudentsInSeminar, groupSolution.getN());
            seminar.setStudents(students);
            if (teacherIndex >= groupSolution.getTeacherList().size()) {
                teacherIndex = 0;
            }
            seminar.setTeacher(groupSolution.getTeacherList().get(teacherIndex));
            teacherIndex++;
            seminarList.add(seminar);
        }
        return seminarList;
    }

    private List<Student> generateStudents(Map<Student, Integer> availableStudentMap, int maxStudentsInSeminar, int groupN) {
        List<Student> students = new ArrayList<>(maxStudentsInSeminar);
        List<Student> totalStudentList = new ArrayList<>(availableStudentMap.keySet());
        for (int i = 0; i < maxStudentsInSeminar; i++) {
            if (availableStudentMap.isEmpty()) {
                break;
            }
            int index = random.nextInt(totalStudentList.size());
            Student student = totalStudentList.get(index);
            if (students.contains(student) || !availableStudentMap.containsKey(student)) {
                if (students.size() >= availableStudentMap.size()) {
                    // TODO Good heuristic to go to the next seminar (can get stuck in a forever loop)
                    break;
                }
                i--;
                continue;
            }
            Integer left = availableStudentMap.get(student) - 1;
            if (left == 0) {
                availableStudentMap.remove(student);
                totalStudentList.remove(index);
            } else {
                availableStudentMap.put(student, left);
            }
            students.add(student);
        }
        return students;
    }

}
