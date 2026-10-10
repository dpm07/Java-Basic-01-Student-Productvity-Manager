package studentproductivity.model;

import java.io.Serializable;

public class TimetableEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;
    private String startTime;
    private String endTime;
    private String subject;
    private String type;
    private String location;

    public TimetableEntry(String day, String startTime, String endTime,
                          String subject, String type, String location) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.type = type;
        this.location = location;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return day + " | " + startTime + " - " + endTime + " | " + subject;
    }
}