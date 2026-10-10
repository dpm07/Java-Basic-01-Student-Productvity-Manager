package studentproductivity.service;

import studentproductivity.data.DataManager;
import studentproductivity.model.Project;
import studentproductivity.model.Task;

public class DashboardService {

    private final DataManager dataManager;

    public DashboardService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public int getTotalTasks() {
        return dataManager.getTasks().size();
    }

    public int getCompletedTasks() {
        int completed = 0;

        for (Task task : dataManager.getTasks()) {
            if (task.isCompleted()) {
                completed++;
            }
        }

        return completed;
    }

    public int getPendingTasks() {
        return getTotalTasks() - getCompletedTasks();
    }

    public int getTotalProjects() {
        return dataManager.getProjects().size();
    }

    public int getCompletedProjects() {
        int completed = 0;

        for (Project project : dataManager.getProjects()) {
            if (project.isCompleted()) {
                completed++;
            }
        }

        return completed;
    }

    public int getPendingProjects() {
        return getTotalProjects() - getCompletedProjects();
    }

    public double getTaskCompletionPercentage() {
        int total = getTotalTasks();

        if (total == 0) {
            return 0.0;
        }

        return (getCompletedTasks() * 100.0) / total;
    }
}
