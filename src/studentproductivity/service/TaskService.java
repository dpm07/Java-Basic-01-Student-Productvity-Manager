package studentproductivity.service;

import java.time.LocalDate;
import java.util.List;

import studentproductivity.data.DataManager;
import studentproductivity.model.Task;

public class TaskService {

    private final DataManager dataManager;

    public TaskService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // =========================================================
    // ADD TASK
    // =========================================================

    public void addTask(
            String title,
            String type,
            LocalDate deadline,
            String status) {

        int id = dataManager.getTasks().size() + 1;

        Task task = new Task(
                id,
                title,
                type,
                deadline,
                status
        );

        dataManager.getTasks().add(task);
        dataManager.saveData();
    }

    // =========================================================
    // GET TASKS
    // =========================================================

    public List<Task> getTasks() {
        return dataManager.getTasks();
    }

    // =========================================================
    // UPDATE TASK
    // =========================================================

    public void updateTask(
            int id,
            String title,
            String type,
            LocalDate deadline,
            String status) {

        for (Task task : dataManager.getTasks()) {

            if (task.getId() == id) {

                task.setTitle(title);
                task.setType(type);
                task.setDeadline(deadline);
                task.setStatus(status);

                dataManager.saveData();
                return;
            }
        }
    }

    // =========================================================
    // DELETE TASK
    // =========================================================

    public void deleteTask(int id) {

        dataManager.getTasks().removeIf(
                task -> task.getId() == id
        );

        dataManager.saveData();
    }

    // =========================================================
    // PENDING TASKS
    // =========================================================

    public int getPendingTasks() {

        int count = 0;

        for (Task task : dataManager.getTasks()) {

            if ("Pending".equalsIgnoreCase(
                    task.getStatus())) {

                count++;
            }
        }

        return count;
    }

    // =========================================================
    // COMPLETED TASKS
    // =========================================================

    public int getCompletedTasks() {

        int count = 0;

        for (Task task : dataManager.getTasks()) {

            if (task.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    // =========================================================
    // OVERDUE TASKS
    // =========================================================

    public int getOverdueTasks() {

        int count = 0;

        for (Task task : dataManager.getTasks()) {

            if (task.isOverdue()) {
                count++;
            }
        }

        return count;
    }
}