package studentproductivity.model;

import java.io.Serializable;

public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String courseId;
    private String type;
    private String date;

    // Can be null if marks are not yet obtained
    private Double score;

    private double maxMarks;

    public Test(
            String name,
            String courseId,
            String type,
            String date,
            Double score,
            double maxMarks) {

        this.name = name;
        this.courseId = courseId;
        this.type = type;
        this.date = date;
        this.score = score;
        this.maxMarks = maxMarks;
    }

    public String getName() {
        return name;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public Double getScore() {
        return score;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public double getPercentage() {

        if (score == null || maxMarks == 0) {
            return 0.0;
        }

        return (score * 100.0) / maxMarks;
    }

    @Override
    public String toString() {
        return name + " - " + courseId + " - " + type;
    }
}