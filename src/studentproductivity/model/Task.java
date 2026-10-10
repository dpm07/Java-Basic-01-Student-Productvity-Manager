package studentproductivity.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String type;
    private LocalDate deadline;
    private String status;

    public Task(
            int id,
            String title,
            String type,
            LocalDate deadline,
            String status) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(status);
    }

    public boolean isOverdue() {

        if (deadline == null) {
            return false;
        }

        return deadline.isBefore(LocalDate.now())
                && !isCompleted();
    }

    @Override
    public String toString() {
        return title + " - " + status;
    }
}