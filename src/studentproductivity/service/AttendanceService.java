package studentproductivity.service;

import studentproductivity.data.DataManager;
import studentproductivity.model.AttendanceRecord;

public class AttendanceService {

    private final DataManager dataManager;

    public AttendanceService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public double getSubjectAttendance(String subjectId) {

        for (AttendanceRecord record : dataManager.getAttendanceRecords()) {

            if (record.getSubjectId().equalsIgnoreCase(subjectId)) {
                return record.getPercentage();
            }
        }

        return 0.0;
    }

    public double getOverallAttendance() {

        int totalAttended = 0;
        int totalClasses = 0;

        for (AttendanceRecord record : dataManager.getAttendanceRecords()) {

            totalAttended += record.getClassesAttended();
            totalClasses += record.getTotalClasses();
        }

        if (totalClasses == 0) {
            return 0.0;
        }

        return (totalAttended * 100.0) / totalClasses;
    }

    public void addOrUpdateAttendance(String subjectId,
                                      int classesAttended,
                                      int totalClasses) {

        for (AttendanceRecord record : dataManager.getAttendanceRecords()) {

            if (record.getSubjectId().equalsIgnoreCase(subjectId)) {

                record.setClassesAttended(classesAttended);
                record.setTotalClasses(totalClasses);

                dataManager.saveData();
                return;
            }
        }

        AttendanceRecord record = new AttendanceRecord(
                subjectId,
                classesAttended,
                totalClasses
        );

        dataManager.addAttendanceRecord(record);
        dataManager.saveData();
    }

    public int getTotalClassesAttended() {

        int total = 0;

        for (AttendanceRecord record : dataManager.getAttendanceRecords()) {
            total += record.getClassesAttended();
        }

        return total;
    }

    public int getTotalClasses() {

        int total = 0;

        for (AttendanceRecord record : dataManager.getAttendanceRecords()) {
            total += record.getTotalClasses();
        }

        return total;
    }
}