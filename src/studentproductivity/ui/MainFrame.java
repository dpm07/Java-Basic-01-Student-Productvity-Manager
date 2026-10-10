package studentproductivity.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;


import studentproductivity.data.DataManager;
import studentproductivity.model.TimetableEntry;
import studentproductivity.service.DashboardService;
import studentproductivity.service.ScheduleService;
import studentproductivity.service.TaskService;
import studentproductivity.service.AttendanceService;
import studentproductivity.service.TestService;
import studentproductivity.service.ProjectService;

public class MainFrame extends JFrame {

    private final DataManager dataManager;
    private final DashboardService dashboardService;
    private final AttendanceService attendanceService;
    private final ScheduleService scheduleService;
    private final TestService testService;
    private final TaskService taskService;
    private final ProjectService projectService;

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color SIDEBAR = new Color(31, 41, 55);
    private final Color CARD = Color.WHITE;
    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color ACCENT = new Color(59, 130, 246);

    private JPanel contentPanel;

    public MainFrame() {

        dataManager = DataManager.loadData();
        dashboardService = new DashboardService(dataManager);
        attendanceService = new AttendanceService(dataManager);
        scheduleService = new ScheduleService(dataManager);
        testService = new TestService(dataManager);
        taskService = new TaskService(dataManager);
        projectService = new ProjectService(dataManager);

        setTitle("Student Productivity Manager");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // =========================
        // LEFT SIDEBAR
        // =========================

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 700));
        sidebar.setBackground(SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        JLabel appTitle = new JLabel("Student Manager");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        appTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(appTitle);
        sidebar.add(Box.createVerticalStrut(30));

        String[] menuItems = {
                "Dashboard",
                "Courses",
                "Timetable",
                "Tests",
                "Projects",
                "Attendance",
                "Tasks"
        };

        for (String item : menuItems) {

    JButton button = new JButton(item);

    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setMaximumSize(new Dimension(190, 42));
    button.setPreferredSize(new Dimension(190, 42));

    button.setHorizontalAlignment(SwingConstants.LEFT);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setBackground(SIDEBAR);
    button.setForeground(Color.WHITE);
    button.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    15
            )
    );

    if (item.equals("Dashboard")) {

        button.addActionListener(e -> showDashboard());

    } else if (item.equals("Courses")) {

        button.addActionListener(e -> showCourses());

    } else if (item.equals("Timetable")) {

        button.addActionListener(e -> showTimetable());

    } else if (item.equals("Tests")) {

        button.addActionListener(e -> showTests());

    } else if (item.equals("Attendance")) {

        button.addActionListener(e -> showAttendance());
    } else if (item.equals("Tasks")) {

    button.addActionListener(e -> showTasks());
    }
    else if (item.equals("Projects")) {

           button.addActionListener(e -> showProjects());

    } 
    else {

        button.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        item + " module will be connected next."
                )
        );
    }

        sidebar.add(button);


} // closes MainFrame constructor
root.add(sidebar, BorderLayout.WEST);

contentPanel = new JPanel(new BorderLayout());
contentPanel.setBackground(BACKGROUND);

root.add(contentPanel, BorderLayout.CENTER);

setContentPane(root);

showDashboard();

} 
private void showProjects() {

    contentPanel.removeAll();

    // =========================================================
    // MAIN CONTAINER
    // =========================================================

    JPanel main = new JPanel(new BorderLayout(0, 20));
    main.setBackground(BACKGROUND);
    main.setBorder(new EmptyBorder(25, 30, 25, 30));

    // =========================================================
    // HEADER
    // =========================================================

    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);

    JPanel headingPanel = new JPanel();
    headingPanel.setOpaque(false);
    headingPanel.setLayout(
            new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
    );

    JLabel heading = new JLabel("Projects");
    heading.setFont(
            new Font("SansSerif", Font.BOLD, 28)
    );
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
            "Manage your academic projects and deadlines"
    );
    subtitle.setFont(
            new Font("SansSerif", Font.PLAIN, 14)
    );
    subtitle.setForeground(MUTED);

    headingPanel.add(heading);
    headingPanel.add(Box.createVerticalStrut(5));
    headingPanel.add(subtitle);

    JButton addProjectButton =
            new JButton("+ Add Project");

    header.add(
            headingPanel,
            BorderLayout.WEST
    );

    header.add(
            addProjectButton,
            BorderLayout.EAST
    );

    main.add(
            header,
            BorderLayout.NORTH
    );

    // =========================================================
    // CENTER
    // =========================================================

    JPanel center =
            new JPanel(new BorderLayout(0, 15));

    center.setOpaque(false);

    // =========================================================
    // SUMMARY CARDS
    // =========================================================

    JPanel summaryPanel =
            new JPanel(
                    new GridLayout(1, 4, 15, 0)
            );

    summaryPanel.setOpaque(false);

    JLabel totalValue = new JLabel("0");
    JLabel activeValue = new JLabel("0");
    JLabel completedValue = new JLabel("0");
    JLabel overdueValue = new JLabel("0");

    summaryPanel.add(
            createProjectSummaryCard(
                    "Total Projects",
                    totalValue
            )
    );

    summaryPanel.add(
            createProjectSummaryCard(
                    "Active",
                    activeValue
            )
    );

    summaryPanel.add(
            createProjectSummaryCard(
                    "Completed",
                    completedValue
            )
    );

    summaryPanel.add(
            createProjectSummaryCard(
                    "Overdue",
                    overdueValue
            )
    );

    center.add(
            summaryPanel,
            BorderLayout.NORTH
    );

    // =========================================================
    // PROJECT TABLE
    // =========================================================

    String[] columns = {
            "ID",
            "Project Name",
            "Type",
            "Deadline",
            "Progress",
            "Status"
    };

    DefaultTableModel model =
            new DefaultTableModel(
                    columns,
                    0
            ) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column) {

                    return false;
                }
            };

    JTable table = new JTable(model);

    table.setRowHeight(32);

    table.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
    );

    table.getTableHeader().setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    table.getTableHeader()
            .setReorderingAllowed(false);

    JScrollPane scrollPane =
            new JScrollPane(table);

    center.add(
            scrollPane,
            BorderLayout.CENTER
    );

    // =========================================================
    // ACTION BAR
    // =========================================================

    JPanel actionPanel =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            10,
                            5
                    )
            );

    actionPanel.setOpaque(false);

    JButton editButton =
            new JButton("Edit");

    JButton progressButton =
            new JButton("Update Progress");

    JButton completeButton =
            new JButton("Mark Completed");

    JButton deleteButton =
            new JButton("Delete");

    actionPanel.add(editButton);
    actionPanel.add(progressButton);
    actionPanel.add(completeButton);
    actionPanel.add(deleteButton);

    JLabel hintLabel =
            new JLabel(
                    "Double-click a project to edit"
            );

    hintLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    12
            )
    );

    hintLabel.setForeground(MUTED);

    actionPanel.add(
            Box.createHorizontalStrut(10)
    );

    actionPanel.add(hintLabel);

    center.add(
            actionPanel,
            BorderLayout.SOUTH
    );

    main.add(
            center,
            BorderLayout.CENTER
    );

    contentPanel.add(
            main,
            BorderLayout.CENTER
    );

    // =========================================================
    // REFRESH PROJECT TABLE
    // =========================================================

    Runnable refreshProjects = () -> {

        model.setRowCount(0);

        for (studentproductivity.model.Project project :
                projectService.getProjects()) {

            String status;

            if (project.isCompleted()) {

                status = "Completed";

            } else if (
                    project.getDeadline() != null
                    && project.getDeadline()
                            .isBefore(LocalDate.now())) {

                status = "Overdue";

            } else {

                status = project.getStatus();
            }

            model.addRow(
                    new Object[]{
                            project.getId(),
                            project.getName(),
                            project.getType(),
                            project.getDeadline(),
                            project.getProgress() + "%",
                            status
                    }
            );
        }

        totalValue.setText(
                String.valueOf(
                        projectService
                                .getProjects()
                                .size()
                )
        );

        activeValue.setText(
                String.valueOf(
                        projectService
                                .getActiveProjects()
                )
        );

        completedValue.setText(
                String.valueOf(
                        projectService
                                .getCompletedProjects()
                )
        );

        overdueValue.setText(
                String.valueOf(
                        projectService
                                .getOverdueProjects()
                )
        );

        table.clearSelection();

        contentPanel.revalidate();
        contentPanel.repaint();
    };

    // =========================================================
    // LOAD EXISTING PROJECTS
    // =========================================================

    refreshProjects.run();

    // =========================================================
    // ADD PROJECT
    // =========================================================

    addProjectButton.addActionListener(e -> {

        JPanel form =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                5,
                                5
                        )
                );

        JTextField idField =
                new JTextField();

        JTextField nameField =
                new JTextField();

        JComboBox<String> typeField =
                new JComboBox<>(
                        new String[]{
                                "Mini",
                                "Macro",
                                "Major",
                                "Other"
                        }
                );

        JTextField deadlineField =
                new JTextField();

        JTextField progressField =
                new JTextField("0");

        form.add(
                new JLabel("Project ID")
        );
        form.add(idField);

        form.add(
                new JLabel("Project Name")
        );
        form.add(nameField);

        form.add(
                new JLabel("Type")
        );
        form.add(typeField);

        form.add(
                new JLabel("Deadline (YYYY-MM-DD)")
        );
        form.add(deadlineField);

        form.add(
                new JLabel("Progress (0-100)")
        );
        form.add(progressField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        form,
                        "Add Project",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String id =
                idField.getText().trim();

        String name =
                nameField.getText().trim();

        String type =
                (String) typeField.getSelectedItem();

        String deadlineText =
                deadlineField.getText().trim();

        if (id.isEmpty()
                || name.isEmpty()
                || deadlineText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in Project ID, Name and Deadline.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate deadline =
                    LocalDate.parse(
                            deadlineText
                    );

            int progress =
                    Integer.parseInt(
                            progressField
                                    .getText()
                                    .trim()
                    );

            if (progress < 0 || progress > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Progress must be between 0 and 100.",
                        "Invalid Progress",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            projectService.addProject(
                    id,
                    name,
                    type,
                    "",
                    "",
                    deadline,
                    progress
            );

            refreshProjects.run();

        } catch (java.time.format.DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the deadline as YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Progress must be a number between 0 and 100.",
                    "Invalid Progress",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    // =========================================================
    // FIND SELECTED PROJECT
    // =========================================================

    java.util.function.Supplier<
            studentproductivity.model.Project
            > getSelectedProject = () -> {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {
            return null;
        }

        String projectId =
                model.getValueAt(
                        selectedRow,
                        0
                ).toString();

        for (
                studentproductivity.model.Project project :
                projectService.getProjects()
        ) {

            if (project.getId().equals(projectId)) {
                return project;
            }
        }

        return null;
    };

    // =========================================================
    // EDIT PROJECT
    // =========================================================

    Runnable editSelectedProject = () -> {

        studentproductivity.model.Project project =
                getSelectedProject.get();

        if (project == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first.",
                    "No Project Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JTextField idField =
                new JTextField(
                        project.getId()
                );

        JTextField nameField =
                new JTextField(
                        project.getName()
                );

        JComboBox<String> typeField =
                new JComboBox<>(
                        new String[]{
                                "Mini",
                                "Macro",
                                "Major",
                                "Other"
                        }
                );

        typeField.setSelectedItem(
                project.getType()
        );

        JTextField deadlineField =
                new JTextField(
                        project.getDeadline() == null
                                ? ""
                                : project.getDeadline()
                                        .toString()
                );

        JTextField progressField =
                new JTextField(
                        String.valueOf(
                                project.getProgress()
                        )
                );

        JPanel form =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                5,
                                5
                        )
                );

        form.add(
                new JLabel("Project ID")
        );
        form.add(idField);

        form.add(
                new JLabel("Project Name")
        );
        form.add(nameField);

        form.add(
                new JLabel("Type")
        );
        form.add(typeField);

        form.add(
                new JLabel("Deadline (YYYY-MM-DD)")
        );
        form.add(deadlineField);

        form.add(
                new JLabel("Progress (0-100)")
        );
        form.add(progressField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        form,
                        "Edit Project",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String newId =
                idField.getText().trim();

        String newName =
                nameField.getText().trim();

        String newType =
                (String) typeField.getSelectedItem();

        String deadlineText =
                deadlineField.getText().trim();

        if (newId.isEmpty()
                || newName.isEmpty()
                || deadlineText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all project fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate newDeadline =
                    LocalDate.parse(
                            deadlineText
                    );

            int newProgress =
                    Integer.parseInt(
                            progressField
                                    .getText()
                                    .trim()
                    );

            if (newProgress < 0
                    || newProgress > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Progress must be between 0 and 100.",
                        "Invalid Progress",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            project.setId(newId);
            project.setName(newName);
            project.setType(newType);
            project.setDeadline(newDeadline);
            project.setProgress(newProgress);

            dataManager.saveData();

            refreshProjects.run();

        } catch (
                java.time.format.DateTimeParseException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the deadline as YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Progress must be a number between 0 and 100.",
                    "Invalid Progress",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    };

    editButton.addActionListener(
            e -> editSelectedProject.run()
    );

    // =========================================================
    // DOUBLE CLICK = EDIT
    // =========================================================

    table.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    if (e.getClickCount() == 2
                            && e.getButton()
                            == java.awt.event.MouseEvent.BUTTON1) {

                        editSelectedProject.run();
                    }
                }
            }
    );

    // =========================================================
    // UPDATE PROGRESS
    // =========================================================

    progressButton.addActionListener(e -> {

        studentproductivity.model.Project project =
                getSelectedProject.get();

        if (project == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first.",
                    "No Project Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter progress (0-100):",
                        project.getProgress()
                );

        if (input == null) {
            return;
        }

        try {

            int progress =
                    Integer.parseInt(
                            input.trim()
                    );

            if (progress < 0 || progress > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Progress must be between 0 and 100.",
                        "Invalid Progress",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            projectService.updateProgress(
                    project.getId(),
                    progress
            );

            refreshProjects.run();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number.",
                    "Invalid Progress",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    // =========================================================
    // MARK COMPLETED
    // =========================================================

    completeButton.addActionListener(e -> {

        studentproductivity.model.Project project =
                getSelectedProject.get();

        if (project == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first.",
                    "No Project Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        projectService.markCompleted(
                project.getId()
        );

        refreshProjects.run();
    });

    // =========================================================
    // DELETE PROJECT
    // =========================================================

    deleteButton.addActionListener(e -> {

        studentproductivity.model.Project project =
                getSelectedProject.get();

        if (project == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first.",
                    "No Project Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete project \""
                                + project.getName()
                                + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation
                != JOptionPane.YES_OPTION) {

            return;
        }

        projectService.deleteProject(
                project.getId()
        );

        refreshProjects.run();
    });

    contentPanel.revalidate();
    contentPanel.repaint();
}


// =========================================================
// PROJECT SUMMARY CARD
// =========================================================

private JPanel createProjectSummaryCard(
        String title,
        JLabel valueLabel) {

    JPanel card =
            new JPanel(
                    new BorderLayout()
            );

    card.setBackground(CARD);

    card.setBorder(
            new EmptyBorder(
                    15,
                    18,
                    15,
                    18
            )
    );

    JLabel titleLabel =
            new JLabel(title);

    titleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    titleLabel.setForeground(MUTED);

    valueLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    24
            )
    );

    valueLabel.setForeground(ACCENT);

    card.add(
            titleLabel,
            BorderLayout.NORTH
    );

    card.add(
            valueLabel,
            BorderLayout.CENTER
    );

    return card;
}
private void showTasks() {

    contentPanel.removeAll();

    // =========================================================
    // MAIN CONTAINER
    // =========================================================

    JPanel main = new JPanel(new BorderLayout(0, 20));
    main.setBackground(BACKGROUND);
    main.setBorder(new EmptyBorder(25, 30, 25, 30));

    // =========================================================
    // HEADER
    // =========================================================

    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);

    JPanel headingPanel = new JPanel();
    headingPanel.setOpaque(false);
    headingPanel.setLayout(
            new BoxLayout(
                    headingPanel,
                    BoxLayout.Y_AXIS
            )
    );

    JLabel heading = new JLabel("Tasks");
    heading.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    28
            )
    );
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
            "Keep track of your academic work and deadlines"
    );
    subtitle.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );
    subtitle.setForeground(MUTED);

    headingPanel.add(heading);
    headingPanel.add(
            Box.createVerticalStrut(5)
    );
    headingPanel.add(subtitle);

    JButton addButton = new JButton("+ Add Task");

    header.add(
            headingPanel,
            BorderLayout.WEST
    );

    header.add(
            addButton,
            BorderLayout.EAST
    );

    main.add(
            header,
            BorderLayout.NORTH
    );

    // =========================================================
    // CENTER
    // =========================================================

    JPanel center = new JPanel(
            new BorderLayout(0, 20)
    );
    center.setOpaque(false);

    // =========================================================
    // SUMMARY CARDS
    // =========================================================

    JPanel summaryPanel = new JPanel(
            new GridLayout(1, 4, 15, 0)
    );
    summaryPanel.setOpaque(false);

    JLabel totalValue = new JLabel();
    JLabel pendingValue = new JLabel();
    JLabel completedValue = new JLabel();
    JLabel overdueValue = new JLabel();

    summaryPanel.add(
            createTaskSummaryCard(
                    "Total Tasks",
                    totalValue
            )
    );

    summaryPanel.add(
            createTaskSummaryCard(
                    "Pending",
                    pendingValue
            )
    );

    summaryPanel.add(
            createTaskSummaryCard(
                    "Completed",
                    completedValue
            )
    );

    summaryPanel.add(
            createTaskSummaryCard(
                    "Overdue",
                    overdueValue
            )
    );

    center.add(
            summaryPanel,
            BorderLayout.NORTH
    );

    // =========================================================
    // TABLE
    // =========================================================

    String[] columns = {
            "Task",
            "Type",
            "Deadline",
            "Status"
    };

    DefaultTableModel model =
            new DefaultTableModel(
                    columns,
                    0
            ) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column
                ) {
                    return false;
                }
            };

    JTable table = new JTable(model);

    table.setRowHeight(32);

    table.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    table.getTableHeader().setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
    );

    JScrollPane scrollPane =
            new JScrollPane(table);

    center.add(
            scrollPane,
            BorderLayout.CENTER
    );

    // =========================================================
    // BOTTOM BUTTONS
    // =========================================================

    JPanel bottomPanel = new JPanel(
            new FlowLayout(
                    FlowLayout.RIGHT
            )
    );

    bottomPanel.setOpaque(false);

    JButton editButton =
            new JButton("Edit");

    JButton deleteButton =
            new JButton("Delete");

    bottomPanel.add(editButton);
    bottomPanel.add(deleteButton);

    center.add(
            bottomPanel,
            BorderLayout.SOUTH
    );

    main.add(
            center,
            BorderLayout.CENTER
    );

    contentPanel.add(
            main,
            BorderLayout.CENTER
    );

    // =========================================================
    // REFRESH TABLE
    // =========================================================

    Runnable refresh = () -> {

        model.setRowCount(0);

        for (studentproductivity.model.Task task :
                taskService.getTasks()) {

            String deadlineText = "";

            if (task.getDeadline() != null) {

                deadlineText =
                        task.getDeadline().format(
                                java.time.format.DateTimeFormatter
                                        .ofPattern("dd/MM/yyyy")
                        );
            }

            String status =
                    task.isOverdue()
                            ? "Overdue"
                            : task.getStatus();

            model.addRow(
                    new Object[]{
                            task.getTitle(),
                            task.getType(),
                            deadlineText,
                            status
                    }
            );
        }

        totalValue.setText(
                String.valueOf(
                        taskService.getTasks().size()
                )
        );

        pendingValue.setText(
                String.valueOf(
                        taskService.getPendingTasks()
                )
        );

        completedValue.setText(
                String.valueOf(
                        taskService.getCompletedTasks()
                )
        );

        overdueValue.setText(
                String.valueOf(
                        taskService.getOverdueTasks()
                )
        );
    };

    refresh.run();

    // =========================================================
    // ADD TASK
    // =========================================================

    addButton.addActionListener(e -> {

        JTextField titleField =
                new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "Assignment",
                                "Practical",
                                "Study",
                                "Project",
                                "Report",
                                "Presentation",
                                "Homework",
                                "Other"
                        }
                );

        JTextField deadlineField =
                new JTextField();

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "In Progress",
                                "Completed"
                        }
                );

        JPanel form = new JPanel(
                new GridLayout(
                        0,
                        1,
                        5,
                        5
                )
        );

        form.add(
                new JLabel("Task")
        );
        form.add(titleField);

        form.add(
                new JLabel("Type")
        );
        form.add(typeBox);

        form.add(
                new JLabel("Deadline (DD/MM/YYYY)")
        );
        form.add(deadlineField);

        form.add(
                new JLabel("Status")
        );
        form.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        form,
                        "Add Task",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title =
                titleField.getText().trim();

        String type =
                (String) typeBox.getSelectedItem();

        String deadlineText =
                deadlineField.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        if (title.isEmpty()
                || deadlineText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all task fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate deadline =
                    LocalDate.parse(
                            deadlineText,
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy")
                    );

            taskService.addTask(
                    title,
                    type,
                    deadline,
                    status
            );

            refresh.run();

        } catch (java.time.format.DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date as DD/MM/YYYY.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    // =========================================================
    // EDIT TASK
    // =========================================================

    editButton.addActionListener(e -> {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a task first.",
                    "No Task Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        studentproductivity.model.Task task =
                taskService.getTasks()
                        .get(selectedRow);

        JTextField titleField =
                new JTextField(
                        task.getTitle()
                );

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "Assignment",
                                "Practical",
                                "Study",
                                "Project",
                                "Report",
                                "Presentation",
                                "Homework",
                                "Other"
                        }
                );

        typeBox.setSelectedItem(
                task.getType()
        );

        JTextField deadlineField =
                new JTextField(
                        task.getDeadline() == null
                                ? ""
                                : task.getDeadline()
                                        .format(
                                                java.time.format.DateTimeFormatter
                                                        .ofPattern("dd/MM/yyyy")
                                        )
        );

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "In Progress",
                                "Completed"
                        }
                );

        statusBox.setSelectedItem(
                task.getStatus()
        );

        JPanel form = new JPanel(
                new GridLayout(
                        0,
                        1,
                        5,
                        5
                )
        );

        form.add(
                new JLabel("Task")
        );
        form.add(titleField);

        form.add(
                new JLabel("Type")
        );
        form.add(typeBox);

        form.add(
                new JLabel("Deadline (DD/MM/YYYY)")
        );
        form.add(deadlineField);

        form.add(
                new JLabel("Status")
        );
        form.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        form,
                        "Edit Task",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title =
                titleField.getText().trim();

        String type =
                (String) typeBox.getSelectedItem();

        String deadlineText =
                deadlineField.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        if (title.isEmpty()
                || deadlineText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all task fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate deadline =
                    LocalDate.parse(
                            deadlineText,
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy")
                    );

            taskService.updateTask(
                    task.getId(),
                    title,
                    type,
                    deadline,
                    status
            );

            refresh.run();

        } catch (java.time.format.DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date as DD/MM/YYYY.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    // =========================================================
    // DELETE TASK
    // =========================================================

    deleteButton.addActionListener(e -> {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a task first.",
                    "No Task Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete the selected task?",
                        "Delete Task",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        studentproductivity.model.Task task =
                taskService.getTasks()
                        .get(selectedRow);

        taskService.deleteTask(
                task.getId()
        );

        refresh.run();
    });

    // =========================================================
    // FINAL REFRESH
    // =========================================================

    contentPanel.revalidate();
    contentPanel.repaint();
}
private JPanel createTaskSummaryCard(
        String title,
        JLabel valueLabel) {

    JPanel card =
            new JPanel(
                    new BorderLayout()
            );

    card.setBackground(CARD);

    card.setBorder(
            new EmptyBorder(
                    15,
                    18,
                    15,
                    18
            )
    );

    JLabel titleLabel =
            new JLabel(title);

    titleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    titleLabel.setForeground(MUTED);

    valueLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    24
            )
    );

    valueLabel.setForeground(TEXT);

    card.add(
            titleLabel,
            BorderLayout.NORTH
    );

    card.add(
            valueLabel,
            BorderLayout.CENTER
    );

    return card;
}
// closes MainFrame constructor
private void showTests() {

    contentPanel.removeAll();

    JPanel main = new JPanel(new BorderLayout());
    main.setBackground(BACKGROUND);
    main.setBorder(new EmptyBorder(25, 30, 25, 30));

    // =========================
    // HEADER
    // =========================

    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);

    JLabel heading = new JLabel("Tests");
    heading.setFont(
            new Font("SansSerif", Font.BOLD, 28)
    );
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
            "Manage tests, scores and academic performance"
    );
    subtitle.setFont(
            new Font("SansSerif", Font.PLAIN, 14)
    );
    subtitle.setForeground(MUTED);

    JPanel headingPanel = new JPanel();
    headingPanel.setOpaque(false);
    headingPanel.setLayout(
            new BoxLayout(
                    headingPanel,
                    BoxLayout.Y_AXIS
            )
    );

    headingPanel.add(heading);
    headingPanel.add(Box.createVerticalStrut(5));
    headingPanel.add(subtitle);

    header.add(
            headingPanel,
            BorderLayout.WEST
    );

    main.add(header, BorderLayout.NORTH);

    // =========================
    // CENTER
    // =========================

    JPanel center = new JPanel(
            new BorderLayout(0, 15)
    );
    center.setOpaque(false);

    // =========================
    // ENTRY FORM
    // =========================

    JPanel formPanel = new JPanel(
            new GridBagLayout()
    );

    formPanel.setBackground(CARD);
    formPanel.setBorder(
            new EmptyBorder(18, 18, 18, 18)
    );

    GridBagConstraints gbc =
            new GridBagConstraints();

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel =
            new JLabel("Test Name");

    JTextField nameField =
            new JTextField(10);

    JLabel courseLabel =
            new JLabel("Course");

    JComboBox<String> courseBox =
            new JComboBox<>();

    for (studentproductivity.model.Subject subject :
            dataManager.getSubjects()) {

        courseBox.addItem(
                subject.getId()
                        + " - "
                        + subject.getName()
        );
    }

    JLabel typeLabel =
            new JLabel("Type");

    JComboBox<String> typeBox =
            new JComboBox<>(new String[]{
                    "Unit Test",
                    "Graded Test",
                    "Quarterly Test",
                    "Semester Test",
                    "End-Semester Test",
                    "Practical",
                    "Practical Viva",
                    "Other"
            });

    JLabel dateLabel =
            new JLabel("Date");

    JTextField dateField =
            new JTextField(10);

    dateField.setToolTipText(
            "DD/MM/YYYY"
    );

    JLabel scoreLabel =
            new JLabel("Obtained Marks");

    JTextField scoreField =
            new JTextField(6);

    JLabel maxLabel =
            new JLabel("Total Marks");

    JTextField maxField =
            new JTextField(6);

    JButton saveButton =
            new JButton("Add Test");

    // =========================
    // FORM ROW 0
    // =========================

    gbc.gridx = 0;
    gbc.gridy = 0;
    formPanel.add(nameLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(nameField, gbc);

    gbc.gridx = 2;
    formPanel.add(courseLabel, gbc);

    gbc.gridx = 3;
    formPanel.add(courseBox, gbc);

    // =========================
    // FORM ROW 1
    // =========================

    gbc.gridx = 0;
    gbc.gridy = 1;
    formPanel.add(typeLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(typeBox, gbc);

    gbc.gridx = 2;
    formPanel.add(dateLabel, gbc);

    gbc.gridx = 3;
    formPanel.add(dateField, gbc);

    // =========================
    // FORM ROW 2
    // =========================

    gbc.gridx = 0;
    gbc.gridy = 2;
    formPanel.add(scoreLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(scoreField, gbc);

    gbc.gridx = 2;
    formPanel.add(maxLabel, gbc);

    gbc.gridx = 3;
    formPanel.add(maxField, gbc);

    gbc.gridx = 4;
    formPanel.add(saveButton, gbc);

    center.add(
            formPanel,
            BorderLayout.NORTH
    );

    // =========================
    // TABLE
    // =========================

    String[] columns = {
            "Test",
            "Course",
            "Type",
            "Date",
            "Obtained",
            "Total Marks",
            "Percentage"
    };

    DefaultTableModel model =
            new DefaultTableModel(
                    columns,
                    0
            ) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column
                ) {
                    return false;
                }
            };

    JTable table =
            new JTable(model);

    table.setRowHeight(30);

    table.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    table.getTableHeader().setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    JScrollPane scrollPane =
            new JScrollPane(table);

    center.add(
            scrollPane,
            BorderLayout.CENTER
    );

    // =========================
    // BOTTOM SUMMARY
    // =========================

    JPanel bottomPanel =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT
                    )
            );

    bottomPanel.setOpaque(false);

    JLabel averageLabel =
            new JLabel(
                    String.format(
                            "Average Performance: %.1f%%",
                            testService
                                    .getAveragePercentage()
                    )
            );

    averageLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    16
            )
    );

    averageLabel.setForeground(ACCENT);

    bottomPanel.add(averageLabel);

    center.add(
            bottomPanel,
            BorderLayout.SOUTH
    );

    main.add(
            center,
            BorderLayout.CENTER
    );

    contentPanel.add(
            main,
            BorderLayout.CENTER
    );

    // =========================
    // LOAD EXISTING TESTS
    // =========================

    refreshTestTable(
            model,
            averageLabel
    );

    // =========================
    // SAVE TEST
    // =========================

    saveButton.addActionListener(e -> {

        String name =
                nameField.getText().trim();

        String course =
                (String) courseBox.getSelectedItem();

        String type =
                (String) typeBox.getSelectedItem();

        String date =
                dateField.getText().trim();

        String scoreText =
                scoreField.getText().trim();

        String maxText =
                maxField.getText().trim();

        // =========================
        // REQUIRED FIELDS
        // =========================

        if (name.isEmpty()
                || course == null
                || date.isEmpty()
                || maxText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required test fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =========================
        // DATE FORMAT
        // =========================

        if (!date.matches(
                "\\d{2}/\\d{2}/\\d{4}"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date as DD/MM/YYYY.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            // =========================
            // OPTIONAL SCORE
            // =========================

            Double score = null;

            if (!scoreText.isEmpty()) {

                score =
                        Double.parseDouble(
                                scoreText
                        );
            }

            // =========================
            // TOTAL MARKS
            // =========================

            double maxMarks =
                    Double.parseDouble(
                            maxText
                    );

            if (maxMarks <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Total marks must be greater than zero.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // =========================
            // SCORE VALIDATION
            // =========================

            if (score != null
                    && (score < 0
                    || score > maxMarks)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Obtained marks are invalid.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // =========================
            // COURSE ID
            // =========================

            String courseId =
                    course.split(
                            " - ",
                            2
                    )[0].trim();

            // =========================
            // SAVE TEST
            // =========================

            testService.addTest(
                    name,
                    courseId,
                    type,
                    date,
                    score,
                    maxMarks
            );

            // =========================
            // REFRESH TABLE
            // =========================

            refreshTestTable(
                    model,
                    averageLabel
            );

            // =========================
            // CLEAR FORM
            // =========================

            nameField.setText("");
            dateField.setText("");
            scoreField.setText("");
            maxField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Obtained marks and total marks must be numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    contentPanel.revalidate();
    contentPanel.repaint();
}

    // =========================================================
    // DASHBOARD
    // =========================================================

    private void showDashboard() {

        contentPanel.removeAll();

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Your academic productivity at a glance"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(
                headingPanel,
                BoxLayout.Y_AXIS
        ));

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        header.add(headingPanel, BorderLayout.WEST);

        content.add(header, BorderLayout.NORTH);

        // Dashboard body
        JPanel dashboard = new JPanel();
        dashboard.setOpaque(false);
        dashboard.setLayout(new BoxLayout(
                dashboard,
                BoxLayout.Y_AXIS
        ));

        dashboard.add(Box.createVerticalStrut(25));

        // Summary cards
        JPanel cards = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );
        cards.setOpaque(false);

        cards.add(createCard(
                "Courses",
                String.valueOf(dataManager.getSubjects().size()),
                "Active courses"
        ));

        cards.add(createCard(
                "Tasks",
                String.valueOf(dashboardService.getPendingTasks()),
                "Pending tasks"
        ));

        cards.add(createCard(
                "Projects",
                String.valueOf(dashboardService.getPendingProjects()),
                "Pending projects"
        ));

        cards.add(createCard(
                "Attendance",
                String.format(
                        "%.1f%%",
                        attendanceService.getOverallAttendance()
                ),
                "Overall attendance"
        ));

        dashboard.add(cards);
        dashboard.add(Box.createVerticalStrut(25));

        // Lower section
        JPanel lower = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );
        lower.setOpaque(false);

       // =========================
// TODAY'S SCHEDULE
// =========================

JPanel schedulePanel = createSectionPanel("Today's Schedule");

java.util.List<studentproductivity.model.TimetableEntry> todaySchedule =
        scheduleService.getTodaySchedule();

if (todaySchedule.isEmpty()) {

    JLabel scheduleMessage = new JLabel(
            "<html>No classes scheduled for today.</html>"
    );

    scheduleMessage.setForeground(MUTED);
    scheduleMessage.setFont(
            new Font("SansSerif", Font.PLAIN, 14)
    );

    schedulePanel.add(scheduleMessage);

} else {

    for (studentproductivity.model.TimetableEntry entry : todaySchedule) {

        JPanel entryPanel = new JPanel();
        entryPanel.setBackground(new Color(248, 250, 252));
        entryPanel.setLayout(new BorderLayout());
        entryPanel.setBorder(
                new EmptyBorder(10, 12, 10, 12)
        );
        entryPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 70)
        );

        // Time
        JLabel timeLabel = new JLabel(
                entry.getStartTime() + " - " + entry.getEndTime()
        );

        timeLabel.setForeground(ACCENT);
        timeLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        // Subject
        JLabel subjectLabel = new JLabel(
                entry.getSubject()
        );

        subjectLabel.setForeground(TEXT);
        subjectLabel.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );

        // Details
        JLabel detailsLabel = new JLabel(
                entry.getType() + " • " + entry.getLocation()
        );

        detailsLabel.setForeground(MUTED);
        detailsLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        JPanel informationPanel = new JPanel();
        informationPanel.setOpaque(false);
        informationPanel.setLayout(
                new BoxLayout(informationPanel, BoxLayout.Y_AXIS)
        );

        informationPanel.add(subjectLabel);
        informationPanel.add(Box.createVerticalStrut(4));
        informationPanel.add(detailsLabel);

        entryPanel.add(
                timeLabel,
                BorderLayout.WEST
        );

        entryPanel.add(
                informationPanel,
                BorderLayout.CENTER
        );

        schedulePanel.add(entryPanel);
        schedulePanel.add(
                Box.createVerticalStrut(10)
        );
    }
}
 // =========================
// ATTENDANCE OVERVIEW
// =========================

JPanel attendancePanel =
        createSectionPanel("Attendance Overview");

double overallAttendance =
        attendanceService.getOverallAttendance();

JLabel percentageLabel = new JLabel(
        String.format("%.1f%%", overallAttendance)
);

percentageLabel.setForeground(ACCENT);
percentageLabel.setFont(
        new Font("SansSerif", Font.BOLD, 28)
);

JLabel statusLabel;

if (overallAttendance == 0) {

    statusLabel = new JLabel(
            "No attendance records available yet."
    );

} else if (overallAttendance >= 75) {

    statusLabel = new JLabel(
            "Overall attendance is above 75%."
    );

} else {

    statusLabel = new JLabel(
            "Overall attendance is below 75%."
    );
}

statusLabel.setForeground(MUTED);
statusLabel.setFont(
        new Font("SansSerif", Font.PLAIN, 14)
);

attendancePanel.add(percentageLabel);
attendancePanel.add(Box.createVerticalStrut(8));
attendancePanel.add(statusLabel);

        lower.add(schedulePanel);
        lower.add(attendancePanel);

        dashboard.add(lower);

        content.add(dashboard, BorderLayout.CENTER);

        contentPanel.add(content, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =========================================================
    // TIMETABLE MODULE
    // =========================================================

    private void showTimetable() {

        contentPanel.removeAll();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel heading = new JLabel("Timetable");
        heading.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                28
        ));
        heading.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage your weekly academic schedule"
        );
        subtitle.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                14
        ));
        subtitle.setForeground(MUTED);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(
                headingPanel,
                BoxLayout.Y_AXIS
        ));

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        header.add(
                headingPanel,
                BorderLayout.WEST
        );

        main.add(header, BorderLayout.NORTH);

        // Center area
        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setOpaque(false);

        // Add-entry card
        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );
        formPanel.setBackground(CARD);
        formPanel.setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> dayBox =
                new JComboBox<>(new String[]{
                        "Monday",
                        "Tuesday",
                        "Wednesday",
                        "Thursday",
                        "Friday",
                        "Saturday",
                        "Sunday"
                });

        JComboBox<String> subjectField =
        new JComboBox<>();

for (studentproductivity.model.Subject subject :
        dataManager.getSubjects()) {

    subjectField.addItem(
            subject.getId() + " - " + subject.getName()
    );
}

        JTextField endField =
                new JTextField();

        JTextField startField =
        new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(new String[]{
                        "Lecture",
                        "Lab",
                        "Exam",
                        "Other"
                });

        JTextField locationField =
                new JTextField();

        addFormField(
                formPanel,
                gbc,
                0,
                "Day",
                dayBox
        );

        addFormField(
                formPanel,
                gbc,
                1,
                "Start Time",
                startField
        );

        addFormField(
                formPanel,
                gbc,
                2,
                "End Time",
                endField
        );

        addFormField(
                formPanel,
                gbc,
                3,
                "Subject",
                subjectField
        );

        addFormField(
                formPanel,
                gbc,
                4,
                "Type",
                typeBox
        );

        addFormField(
                formPanel,
                gbc,
                5,
                "Location",
                locationField
        );

        JButton addButton =
                new JButton("Add Entry");

        addButton.setFocusPainted(false);

        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;

        formPanel.add(addButton, gbc);

        center.add(
                formPanel,
                BorderLayout.NORTH
        );

        // Table
        String[] columns = {
                "Day",
                "Start",
                "End",
                "Subject",
                "Type",
                "Location"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        JTable table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                13
        ));

        table.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        center.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Buttons
        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT
                ));

        buttonPanel.setOpaque(false);

        JButton editButton =
                new JButton("Edit Selected");

        JButton deleteButton =
                new JButton("Delete Selected");

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        center.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        main.add(
                center,
                BorderLayout.CENTER
        );

        contentPanel.add(
                main,
                BorderLayout.CENTER
        );

        // Load existing timetable entries
        refreshTimetableTable(model);

        // =====================================================
        // ADD ENTRY
        // =====================================================

        addButton.addActionListener(e -> {

            String day =
                    (String) dayBox.getSelectedItem();

            String start =
                    startField.getText().trim();

            String end =
                    endField.getText().trim();

            String selectedSubject =
        (String) subjectField.getSelectedItem();

if (selectedSubject == null) {

    JOptionPane.showMessageDialog(
            this,
            "Please add a course first.",
            "No Course Selected",
            JOptionPane.WARNING_MESSAGE
    );

    return;
}

String subject = selectedSubject;

            String type =
                    (String) typeBox.getSelectedItem();

            String location =
                    locationField.getText().trim();

            if (start.isEmpty()
                    || end.isEmpty()
                    || subject.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter start time, end time and subject.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            TimetableEntry entry =
                    new TimetableEntry(
                            day,
                            start,
                            end,
                            subject,
                            type,
                            location
                    );

            dataManager.addTimetableEntry(entry);
            dataManager.saveData();

            refreshTimetableTable(model);

            startField.setText("");
            endField.setText("");
            subjectField.setSelectedIndex(-1);
            locationField.setText("");
        });

        // =====================================================
        // DELETE
        // =====================================================

        deleteButton.addActionListener(e -> {

            int selectedRow =
                    table.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Select a timetable entry first."
                );

                return;
            }

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete selected timetable entry?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            dataManager.getTimetableEntries()
                    .remove(selectedRow);

            dataManager.saveData();

            refreshTimetableTable(model);
        });

        // =====================================================
        // EDIT
        // =====================================================

        editButton.addActionListener(e -> {

            int selectedRow =
                    table.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Select a timetable entry first."
                );

                return;
            }

            TimetableEntry entry =
                    dataManager.getTimetableEntries()
                            .get(selectedRow);

            JTextField editStart =
                    new JTextField(
                            entry.getStartTime()
                    );

            JTextField editEnd =
                    new JTextField(
                            entry.getEndTime()
                    );

            JTextField editSubject =
                    new JTextField(
                            entry.getSubject()
                    );

            JTextField editLocation =
                    new JTextField(
                            entry.getLocation()
                    );

            JComboBox<String> editDay =
                    new JComboBox<>(new String[]{
                            "Monday",
                            "Tuesday",
                            "Wednesday",
                            "Thursday",
                            "Friday",
                            "Saturday",
                            "Sunday"
                    });

            editDay.setSelectedItem(
                    entry.getDay()
            );

            JComboBox<String> editType =
                    new JComboBox<>(new String[]{
                            "Lecture",
                            "Lab",
                            "Exam",
                            "Other"
                    });

            editType.setSelectedItem(
                    entry.getType()
            );

            JPanel editPanel =
                    new JPanel(new GridLayout(
                            0,
                            2,
                            8,
                            8
                    ));

            editPanel.add(new JLabel("Day"));
            editPanel.add(editDay);

            editPanel.add(new JLabel("Start Time"));
            editPanel.add(editStart);

            editPanel.add(new JLabel("End Time"));
            editPanel.add(editEnd);

            editPanel.add(new JLabel("Subject"));
            editPanel.add(editSubject);

            editPanel.add(new JLabel("Type"));
            editPanel.add(editType);

            editPanel.add(new JLabel("Location"));
            editPanel.add(editLocation);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            editPanel,
                            "Edit Timetable Entry",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            entry.setDay(
                    (String) editDay.getSelectedItem()
            );

            entry.setStartTime(
                    editStart.getText().trim()
            );

            entry.setEndTime(
                    editEnd.getText().trim()
            );

            entry.setSubject(
                    editSubject.getText().trim()
            );

            entry.setType(
                    (String) editType.getSelectedItem()
            );

            entry.setLocation(
                    editLocation.getText().trim()
            );

            dataManager.saveData();

            refreshTimetableTable(model);
        });

        contentPanel.revalidate();
        contentPanel.repaint();
    }
private void showAttendance() {

    contentPanel.removeAll();

    JPanel main = new JPanel(new BorderLayout(0, 18));
    main.setBackground(BACKGROUND);
    main.setBorder(new EmptyBorder(25, 30, 25, 30));

    // =========================================================
    // HEADER
    // =========================================================

    JPanel header = new JPanel();
    header.setOpaque(false);
    header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

    JLabel heading = new JLabel("Attendance");
    heading.setFont(
            new Font("SansSerif", Font.BOLD, 28)
    );
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
            "Track your subject-wise attendance"
    );
    subtitle.setFont(
            new Font("SansSerif", Font.PLAIN, 14)
    );
    subtitle.setForeground(MUTED);

    header.add(heading);
    header.add(Box.createVerticalStrut(5));
    header.add(subtitle);

    main.add(header, BorderLayout.NORTH);

    // =========================================================
    // TOP SECTION
    // Contains Overall Attendance + Entry Form
    // =========================================================

    JPanel topSection = new JPanel();
    topSection.setOpaque(false);
    topSection.setLayout(
            new BoxLayout(topSection, BoxLayout.Y_AXIS)
    );

    // =========================================================
    // OVERALL ATTENDANCE CARD
    // =========================================================

    JPanel overallPanel =
            new JPanel(new BorderLayout());

    overallPanel.setBackground(CARD);
    overallPanel.setBorder(
            new EmptyBorder(18, 25, 18, 25)
    );

    overallPanel.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    80
            )
    );

    JLabel overallTitle =
            new JLabel("Overall Attendance");

    overallTitle.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    15
            )
    );

    overallTitle.setForeground(MUTED);

    JLabel overallLabel =
            new JLabel(
                    String.format(
                            "%.1f%%",
                            attendanceService
                                    .getOverallAttendance()
                    )
            );

    overallLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    30
            )
    );

    overallLabel.setForeground(ACCENT);

    overallPanel.add(
            overallTitle,
            BorderLayout.WEST
    );

    overallPanel.add(
            overallLabel,
            BorderLayout.EAST
    );

    topSection.add(overallPanel);

    topSection.add(
            Box.createVerticalStrut(15)
    );

    // =========================================================
    // ATTENDANCE ENTRY FORM
    // =========================================================

    JPanel formPanel =
            new JPanel(new GridBagLayout());

    formPanel.setBackground(CARD);
    formPanel.setBorder(
            new EmptyBorder(
                    15,
                    20,
                    15,
                    20
            )
    );

    formPanel.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    105
            )
    );

    GridBagConstraints gbc =
            new GridBagConstraints();

    gbc.insets =
            new Insets(
                    6,
                    8,
                    6,
                    8
            );

    gbc.fill =
            GridBagConstraints.HORIZONTAL;

    // ---------------------------------------------------------
    // Course
    // ---------------------------------------------------------

    JLabel subjectLabel =
            new JLabel("Course");

    JComboBox<String> subjectField =
            new JComboBox<>();

    for (studentproductivity.model.Subject subject :
            dataManager.getSubjects()) {

        subjectField.addItem(
                subject.getId()
                        + " - "
                        + subject.getName()
        );
    }

    // ---------------------------------------------------------
    // Attended
    // ---------------------------------------------------------

    JLabel attendedLabel =
            new JLabel("Classes Attended");

    JTextField attendedField =
            new JTextField(8);

    // ---------------------------------------------------------
    // Total
    // ---------------------------------------------------------

    JLabel totalLabel =
            new JLabel("Total Classes");

    JTextField totalField =
            new JTextField(8);

    // ---------------------------------------------------------
    // Save Button
    // ---------------------------------------------------------

    JButton saveButton =
            new JButton("Save / Update");

    // =========================================================
    // ROW 0
    // =========================================================

    gbc.gridy = 0;

    gbc.gridx = 0;
    formPanel.add(
            subjectLabel,
            gbc
    );

    gbc.gridx = 1;
    gbc.weightx = 1.0;
    formPanel.add(
            subjectField,
            gbc
    );

    gbc.gridx = 2;
    gbc.weightx = 0;
    formPanel.add(
            attendedLabel,
            gbc
    );

    gbc.gridx = 3;
    formPanel.add(
            attendedField,
            gbc
    );

    // =========================================================
    // ROW 1
    // =========================================================

    gbc.gridy = 1;

    gbc.gridx = 0;
    formPanel.add(
            totalLabel,
            gbc
    );

    gbc.gridx = 1;
    formPanel.add(
            totalField,
            gbc
    );

    gbc.gridx = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;

    formPanel.add(
            saveButton,
            gbc
    );

    topSection.add(formPanel);

    // =========================================================
    // ADD TOP SECTION
    // =========================================================

    main.add(
            topSection,
            BorderLayout.NORTH
    );

    // =========================================================
    // TABLE
    // =========================================================

    String[] columns = {
            "Course",
            "Classes Attended",
            "Total Classes",
            "Attendance"
    };

    DefaultTableModel model =
            new DefaultTableModel(
                    columns,
                    0
            ) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column
                ) {
                    return false;
                }
            };

    JTable table =
            new JTable(model);

    table.setRowHeight(32);

    table.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    table.getTableHeader().setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    table.getTableHeader().setReorderingAllowed(false);

    JScrollPane scrollPane =
            new JScrollPane(table);

    scrollPane.setBorder(
            BorderFactory.createLineBorder(
                    new Color(220, 224, 230)
            )
    );

    main.add(
            scrollPane,
            BorderLayout.CENTER
    );

    // =========================================================
    // ADD TO CONTENT PANEL
    // =========================================================

    contentPanel.add(
            main,
            BorderLayout.CENTER
    );

    // =========================================================
    // LOAD EXISTING DATA
    // =========================================================

    refreshAttendanceTable(
            model,
            overallLabel
    );

    // =========================================================
    // SAVE / UPDATE ACTION
    // =========================================================

    saveButton.addActionListener(e -> {

        String selectedCourse =
                (String) subjectField.getSelectedItem();

        if (selectedCourse == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please add a course first.",
                    "No Course Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String subjectId =
                selectedCourse
                        .split(" - ", 2)[0]
                        .trim();

        String attendedText =
                attendedField.getText().trim();

        String totalText =
                totalField.getText().trim();

        if (subjectId.isEmpty()
                || attendedText.isEmpty()
                || totalText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all attendance fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int attended =
                    Integer.parseInt(
                            attendedText
                    );

            int total =
                    Integer.parseInt(
                            totalText
                    );

            // -------------------------------------------------
            // Validation
            // -------------------------------------------------

            if (attended < 0
                    || total <= 0
                    || attended > total) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance values are invalid.\n"
                                + "Attended classes cannot be greater "
                                + "than total classes.",
                        "Invalid Attendance",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // Save / Update
            // -------------------------------------------------

            attendanceService.addOrUpdateAttendance(
                    subjectId,
                    attended,
                    total
            );

            // -------------------------------------------------
            // Refresh table and percentage
            // -------------------------------------------------

            refreshAttendanceTable(
                    model,
                    overallLabel
            );

            // -------------------------------------------------
            // Clear input fields
            // -------------------------------------------------

            subjectField.setSelectedIndex(-1);
            attendedField.setText("");
            totalField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Classes attended and total classes must be numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });

    contentPanel.revalidate();
    contentPanel.repaint();
}

private void refreshAttendanceTable(
        DefaultTableModel model,
        JLabel overallLabel) {

    model.setRowCount(0);

    for (studentproductivity.model.AttendanceRecord record :
            dataManager.getAttendanceRecords()) {

        model.addRow(new Object[]{
                record.getSubjectId(),
                record.getClassesAttended(),
                record.getTotalClasses(),
                String.format(
                        "%.1f%%",
                        record.getPercentage()
                )
        });
    }

    overallLabel.setText(
            String.format(
                    "%.1f%%",
                    attendanceService.getOverallAttendance()
            )
    );
}
private void refreshTestTable(
        DefaultTableModel model,
        JLabel averageLabel) {

    model.setRowCount(0);

    for (studentproductivity.model.Test test :
            dataManager.getTests()) {

        model.addRow(new Object[]{
                test.getName(),
                test.getCourseId(),
                test.getType(),
                test.getDate(),
                test.getScore(),
                test.getMaxMarks(),
                String.format(
                        "%.1f%%",
                        test.getPercentage()
                )
        });
    }

    averageLabel.setText(
            String.format(
                    "Average Performance: %.1f%%",
                    testService.getAveragePercentage()
            )
    );
}
private void showCourses() {

    contentPanel.removeAll();

    JPanel main = new JPanel(new BorderLayout());
    main.setBackground(BACKGROUND);
    main.setBorder(new EmptyBorder(25, 30, 25, 30));

    // =========================
    // HEADER
    // =========================

    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);

    JLabel heading = new JLabel("Courses");
    heading.setFont(
            new Font("SansSerif", Font.BOLD, 28)
    );
    heading.setForeground(TEXT);

    JLabel subtitle = new JLabel(
            "Manage your courses and academic information"
    );
    subtitle.setFont(
            new Font("SansSerif", Font.PLAIN, 14)
    );
    subtitle.setForeground(MUTED);

    JPanel headingPanel = new JPanel();
    headingPanel.setOpaque(false);
    headingPanel.setLayout(
            new BoxLayout(
                    headingPanel,
                    BoxLayout.Y_AXIS
            )
    );

    headingPanel.add(heading);
    headingPanel.add(Box.createVerticalStrut(5));
    headingPanel.add(subtitle);

    header.add(
            headingPanel,
            BorderLayout.WEST
    );

    main.add(header, BorderLayout.NORTH);

    // =========================
    // TABLE
    // =========================

    String[] columns = {
            "Course ID",
            "Course Name",
            "Professor",
            "Score"
    };

    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(
                    columns,
                    0
            ) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column
                ) {
                    return false;
                }
            };

    JTable table = new JTable(model);

    table.setRowHeight(30);

    table.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    table.getTableHeader().setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    JScrollPane scrollPane =
            new JScrollPane(table);

    main.add(
            scrollPane,
            BorderLayout.CENTER
    );

    // =========================
    // BUTTONS
    // =========================

    JPanel buttons = new JPanel(
            new FlowLayout(
                    FlowLayout.LEFT
            )
    );

    buttons.setOpaque(false);

    JButton addButton =
            new JButton("Add Course");

    JButton editButton =
            new JButton("Edit");

    JButton deleteButton =
            new JButton("Delete");

    buttons.add(addButton);
    buttons.add(editButton);
    buttons.add(deleteButton);

    main.add(
            buttons,
            BorderLayout.SOUTH
    );

    // =========================
    // LOAD COURSES
    // =========================

    refreshCourseTable(model);

    // =========================
    // ADD
    // =========================

    addButton.addActionListener(e ->
            showCourseDialog(
                    null,
                    model
            )
    );

    // =========================
    // EDIT
    // =========================

    editButton.addActionListener(e -> {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a course first.",
                    "No Course Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String courseId =
                model.getValueAt(
                        selectedRow,
                        0
                ).toString();

        for (studentproductivity.model.Subject subject :
                dataManager.getSubjects()) {

            if (subject.getId()
                    .equalsIgnoreCase(courseId)) {

                showCourseDialog(
                        subject,
                        model
                );

                break;
            }
        }
    });

    // =========================
    // DELETE
    // =========================

    deleteButton.addActionListener(e -> {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a course first.",
                    "No Course Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String courseId =
                model.getValueAt(
                        selectedRow,
                        0
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete course " + courseId + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            dataManager.getSubjects().removeIf(
                    subject ->
                            subject.getId()
                                    .equalsIgnoreCase(
                                            courseId
                                    )
            );

            dataManager.saveData();

            refreshCourseTable(model);
        }
    });

    contentPanel.add(
            main,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}
private void refreshCourseTable(
        javax.swing.table.DefaultTableModel model) {

    model.setRowCount(0);

    for (studentproductivity.model.Subject subject :
            dataManager.getSubjects()) {

        model.addRow(new Object[]{
                subject.getId(),
                subject.getName(),
                subject.getProfessorName(),
                String.format(
                        "%.1f",
                        subject.getScore()
                )
        });
    }
}
    // =========================================================
    // REFRESH TABLE
    // =========================================================

    private void refreshTimetableTable(
            DefaultTableModel model
    ) {

        model.setRowCount(0);

        for (TimetableEntry entry :
                dataManager.getTimetableEntries()) {

            model.addRow(new Object[]{
                    entry.getDay(),
                    entry.getStartTime(),
                    entry.getEndTime(),
                    entry.getSubject(),
                    entry.getType(),
                    entry.getLocation()
            });
        }
    }

    // =========================================================
    // FORM FIELD HELPER
    // =========================================================

    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int column,
            String label,
            JComponent component
    ) {

        gbc.gridx = column;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1;

        JPanel field =
                new JPanel();

        field.setOpaque(false);

        field.setLayout(
                new BoxLayout(
                        field,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelComponent =
                new JLabel(label);

        labelComponent.setForeground(MUTED);
        labelComponent.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        field.add(labelComponent);
        field.add(Box.createVerticalStrut(4));
        field.add(component);

        panel.add(field, gbc);
    }

    // =========================================================
    // SUMMARY CARD
    // =========================================================

    private JPanel createCard(
            String title,
            String value,
            String description
    ) {

        JPanel card = new JPanel();

        card.setBackground(CARD);

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(MUTED);
        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(ACCENT);
        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descriptionLabel);

        return card;
    }

    // =========================================================
    // SECTION PANEL
    // =========================================================

    private JPanel createSectionPanel(
            String title
    ) {

        JPanel panel = new JPanel();

        panel.setBackground(CARD);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }
    

    private void showCourseDialog(
        studentproductivity.model.Subject subject,
        javax.swing.table.DefaultTableModel model) {

    boolean editing = subject != null;

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField professorField = new JTextField();
    JTextField scoreField = new JTextField();

    if (editing) {
        idField.setText(subject.getId());
        nameField.setText(subject.getName());
        professorField.setText(subject.getProfessorName());
        scoreField.setText(String.valueOf(subject.getScore()));
    }

    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

    panel.add(new JLabel("Course ID:"));
    panel.add(idField);

    panel.add(new JLabel("Course Name:"));
    panel.add(nameField);

    panel.add(new JLabel("Professor:"));
    panel.add(professorField);

    panel.add(new JLabel("Score:"));
    panel.add(scoreField);

    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            editing ? "Edit Course" : "Add Course",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String id = idField.getText().trim();
    String name = nameField.getText().trim();
    String professor = professorField.getText().trim();
    String scoreText = scoreField.getText().trim();

    if (id.isEmpty() || name.isEmpty() || professor.isEmpty()
            || scoreText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    double score;

    try {
        score = Double.parseDouble(scoreText);
    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Score must be a valid number.",
                "Invalid Score",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    if (editing) {

        subject.setId(id);
        subject.setName(name);
        subject.setProfessorName(professor);
        subject.setScore(score);

    } else {

        studentproductivity.model.Subject newSubject =
                new studentproductivity.model.Subject(
                        id,
                        name,
                        professor,
                        score
                );

        dataManager.addSubject(newSubject);
    }

    dataManager.saveData();

    refreshCourseTable(model);
}
}