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

package sk.gymy.seminar.swingui;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import sk.gymy.seminar.domain.Group;
import sk.gymy.seminar.domain.Groups;
import sk.gymy.seminar.domain.Seminar;
import sk.gymy.seminar.domain.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SeminarPanel extends SolutionPanel {

    public static final String LOGO_PATH = "/sk/gymy/seminar/swingui/seminarLogo.png";

    private final ImageIcon lockedIcon;

    public SeminarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        lockedIcon = new ImageIcon(getClass().getResource("locked.png"));
    }

    private Groups getGroups() {
        return (Groups) solutionBusiness.getSolution();
    }

    public void resetPanel(Solution solution) {
        removeAll();
        repaint(); // When GridLayout doesn't fill up all the space
        Groups groups = (Groups) solution;
        int n = groups.getN();

        List<Seminar> seminarList = groups.getSeminarList();
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints constraints;
        for (int row = 0; row < n; row++) {
            constraints = new GridBagConstraints();
            JLabel rowLabel = new JLabel("Group " + row);
            constraints.gridx = 0;
            constraints.gridy = row;
            constraints.fill = GridBagConstraints.VERTICAL;
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.anchor = GridBagConstraints.LINE_START;
            add(rowLabel, constraints);

            int counter = 0;
            for (Seminar seminar : seminarList) {
                if (seminar == null || seminar.getGroup() == null) {
                    continue;
                }
                if (seminar.getGroup().getIndex() == row) {
                    counter++;
                    constraints = new GridBagConstraints();

                    String toolTipText = seminar.toString();
                    JButton button = new JButton(new SeminarAction(seminar));
                    button.setText(seminar.getName());
                    button.setToolTipText(toolTipText);
                    if (seminar.isLocked()) {
                        button.setIcon(lockedIcon);
                    }

                    constraints.gridx = counter;
                    constraints.gridy = row;
                    constraints.weightx = 2;
                    constraints.fill = GridBagConstraints.BOTH;
                    add(button, constraints);
                }
            }
        }
    }

    private class SeminarAction extends AbstractAction {

        private Seminar seminar;

        public SeminarAction(Seminar seminar) {
            super(null);
            this.seminar = seminar;
        }

        public void actionPerformed(ActionEvent e) {
            final List<Group> groupList = getGroups().getGroupList();
            JTabbedPane tabbedPane = new JTabbedPane();

            JPanel operationsPanel = new JPanel(new GridLayout(2, 2));
            operationsPanel.add(new JLabel("Move to group: "), BorderLayout.WEST);
            Group[] groupArray = new Group[groupList.size()];
            JComboBox<Group> groupListField = new JComboBox<>(groupList.toArray(groupArray));
            groupListField.setSelectedItem(seminar.getGroup());
            operationsPanel.add(groupListField, BorderLayout.CENTER);
            operationsPanel.add(new JLabel("Locked:"));
            JCheckBox lockedField = new JCheckBox("immovable during planning");
            lockedField.setSelected(seminar.isLocked());
            operationsPanel.add(lockedField);
            tabbedPane.addTab("Operations", operationsPanel);

            JPanel statsPanel = new JPanel(new GridLayout(2, 2));
            statsPanel.add(new JLabel("Students: "), BorderLayout.WEST);
            statsPanel.add(new JLabel(Integer.toString(seminar.getStudents().size())), BorderLayout.CENTER);
            statsPanel.add(new JLabel("Teacher: "), BorderLayout.WEST);
            statsPanel.add(new JLabel(seminar.getTeacher().getName()),
                    BorderLayout.CENTER);
            tabbedPane.addTab("Statistics", statsPanel);

            JPanel studentListPanel = new JPanel(new GridLayout(1, 1));
            DefaultListModel<String> studentNameListModel = new DefaultListModel<>();
            for (Student student : seminar.getStudents()) {
                studentNameListModel.addElement(student.getName());
            }
            JList<String> studentList = new JList<>(studentNameListModel);
            studentListPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);
            tabbedPane.addTab("Students", studentListPanel);

            int result = JOptionPane.showConfirmDialog(SeminarPanel.this.getRootPane(), tabbedPane,
                    "Seminar: " + seminar.getName(), JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Group toGroup = (Group) groupListField.getSelectedItem();
                if (!toGroup.equals(seminar.getGroup())) {
                    solutionBusiness.doChangeMove(seminar, "group", toGroup);
                    solverAndPersistenceFrame.resetScreen();
                }
                boolean toLocked = lockedField.isSelected();
                if (seminar.isLocked() != toLocked) {
                    if (solutionBusiness.isSolving()) {
                        logger.error("Not doing user change because the solver is solving.");
                        return;
                    }
                    seminar.setLocked(toLocked);
                }
                solverAndPersistenceFrame.resetScreen();
            }
        }

    }

}
