package studentproductivity.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String type;
    private String description;
    private String objective;
    private LocalDate deadline;
    private int progress;
    private boolean completed;

    public Project(
            String id,
            String name,
            String type,
            String description,
            String objective,
            LocalDate deadline) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.objective = objective;
        this.deadline = deadline;
        this.progress = 0;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getObjective() {
        return objective;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getProgress() {
        return progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getStatus() {
        if (completed || progress >= 100) {
            return "Completed";
        }

        if (progress > 0) {
            return "In Progress";
        }

        return "Pending";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setProgress(int progress) {

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 100) {
            progress = 100;
        }

        this.progress = progress;
        this.completed = progress == 100;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;

        if (completed) {
            this.progress = 100;
        }
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}