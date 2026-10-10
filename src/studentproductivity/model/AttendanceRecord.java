package studentproductivity.model;

import java.io.Serializable;

public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subjectId;
    private int classesAttended;
    private int totalClasses;

    public AttendanceRecord(String subjectId,
                            int classesAttended,
                            int totalClasses) {
        this.subjectId = subjectId;
        this.classesAttended = classesAttended;
        this.totalClasses = totalClasses;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getClassesAttended() {
        return classesAttended;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setClassesAttended(int classesAttended) {
        this.classesAttended = classesAttended;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public double getPercentage() {
        if (totalClasses == 0) {
            return 0.0;
        }

        return (classesAttended * 100.0) / totalClasses;
    }

    @Override
    public String toString() {
        return subjectId + " - " + classesAttended
                + "/" + totalClasses;
    }
}