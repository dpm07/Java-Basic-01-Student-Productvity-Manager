package studentproductivity.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import studentproductivity.model.AttendanceRecord;
import studentproductivity.model.Project;
import studentproductivity.model.Subject;
import studentproductivity.model.Task;
import studentproductivity.model.Test;
import studentproductivity.model.TimetableEntry;

public class DataManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Subject> subjects;
    private List<Task> tasks;
    private List<Project> projects;
    private List<TimetableEntry> timetableEntries;
    private List<AttendanceRecord> attendanceRecords;
    private List<Test> tests;

    public DataManager() {
        subjects = new ArrayList<>();
        tasks = new ArrayList<>();
        projects = new ArrayList<>();
        timetableEntries = new ArrayList<>();
        attendanceRecords = new ArrayList<>();
        tests = new ArrayList<>();
    }

    // =========================================================
    // NULL-SAFE INITIALIZATION
    // =========================================================

    private void initializeLists() {

        if (subjects == null) {
            subjects = new ArrayList<>();
        }

        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        if (projects == null) {
            projects = new ArrayList<>();
        }

        if (timetableEntries == null) {
            timetableEntries = new ArrayList<>();
        }

        if (attendanceRecords == null) {
            attendanceRecords = new ArrayList<>();
        }

        if (tests == null) {
            tests = new ArrayList<>();
        }
    }

    // =========================================================
    // SUBJECTS
    // =========================================================

    public List<Subject> getSubjects() {
        initializeLists();
        return subjects;
    }

    public void addSubject(Subject subject) {
        initializeLists();
        subjects.add(subject);
    }

    // =========================================================
    // TASKS
    // =========================================================

    public List<Task> getTasks() {
        initializeLists();
        return tasks;
    }

    public void addTask(Task task) {
        initializeLists();
        tasks.add(task);
    }

    // =========================================================
    // PROJECTS
    // =========================================================

    public List<Project> getProjects() {
        initializeLists();
        return projects;
    }

    public void addProject(Project project) {
        initializeLists();
        projects.add(project);
    }

    // =========================================================
    // TIMETABLE
    // =========================================================

    public List<TimetableEntry> getTimetableEntries() {
        initializeLists();
        return timetableEntries;
    }

    public void addTimetableEntry(TimetableEntry entry) {
        initializeLists();
        timetableEntries.add(entry);
    }

    // =========================================================
    // ATTENDANCE
    // =========================================================

    public List<AttendanceRecord> getAttendanceRecords() {
        initializeLists();
        return attendanceRecords;
    }

    public void addAttendanceRecord(AttendanceRecord record) {
        initializeLists();
        attendanceRecords.add(record);
    }

    // =========================================================
    // TESTS
    // =========================================================

    public List<Test> getTests() {
        initializeLists();
        return tests;
    }

    public void addTest(Test test) {
        initializeLists();
        tests.add(test);
    }

    // =========================================================
    // SAVE DATA
    // =========================================================

    public void saveData() {
        initializeLists();
        DataStore.save(this);
    }

    // =========================================================
    // LOAD DATA
    // =========================================================

    public static DataManager loadData() {

        Object data = DataStore.load();

        if (data instanceof DataManager) {

            DataManager manager = (DataManager) data;

            // Important for older saved data
            // where newly added lists may be null.
            manager.initializeLists();

            return manager;
        }

        return new DataManager();
    }

    // =========================================================
    // DESERIALIZATION SAFETY
    // =========================================================

    /*
     * Java serialization does not call the constructor when
     * loading an existing DataManager object.
     *
     * This method makes sure all lists exist after loading
     * older saved data.
     */
    private void readObject(ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {

        inputStream.defaultReadObject();

        initializeLists();
    }
}