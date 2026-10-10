package studentproductivity.service;

import java.time.LocalDate;
import java.util.List;

import studentproductivity.data.DataManager;
import studentproductivity.model.Project;

public class ProjectService {

    private final DataManager dataManager;

    public ProjectService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // =========================
    // ADD PROJECT
    // =========================

    public void addProject(
            String id,
            String name,
            String type,
            String description,
            String objective,
            LocalDate deadline,
            int progress) {

        Project project = new Project(
                id,
                name,
                type,
                description,
                objective,
                deadline
        );

        project.setProgress(progress);

        dataManager.getProjects().add(project);
        dataManager.saveData();
    }

    // Compatibility method
    // Keeps older code working.
    public void addProject(
            String id,
            String name,
            String type,
            String description,
            String objective,
            LocalDate deadline) {

        addProject(
                id,
                name,
                type,
                description,
                objective,
                deadline,
                0
        );
    }

    // =========================
    // GET PROJECTS
    // =========================

    public List<Project> getProjects() {
        return dataManager.getProjects();
    }

    // =========================
    // UPDATE PROGRESS
    // =========================

    public void updateProgress(
            String projectId,
            int progress) {

        for (Project project : dataManager.getProjects()) {

            if (project.getId().equals(projectId)) {

                project.setProgress(progress);

                dataManager.saveData();
                return;
            }
        }
    }

    // =========================
    // MARK COMPLETED
    // =========================

    public void markCompleted(String projectId) {

        for (Project project : dataManager.getProjects()) {

            if (project.getId().equals(projectId)) {

                project.setCompleted(true);

                dataManager.saveData();
                return;
            }
        }
    }

    // =========================
    // DELETE PROJECT
    // =========================

    public void deleteProject(String projectId) {

        Project projectToDelete = null;

        for (Project project : dataManager.getProjects()) {

            if (project.getId().equals(projectId)) {
                projectToDelete = project;
                break;
            }
        }

        if (projectToDelete != null) {

            dataManager.getProjects().remove(projectToDelete);
            dataManager.saveData();
        }
    }

    // =========================
    // COMPLETED COUNT
    // =========================

    public int getCompletedProjects() {

        int count = 0;

        for (Project project : dataManager.getProjects()) {

            if (project.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    // =========================
    // PENDING / ACTIVE COUNT
    // =========================

    public int getPendingProjects() {

        return dataManager.getProjects().size()
                - getCompletedProjects();
    }

    // =========================
    // ACTIVE PROJECTS
    // =========================

    public int getActiveProjects() {

        int count = 0;

        for (Project project : dataManager.getProjects()) {

            if (!project.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    // =========================
    // OVERDUE PROJECTS
    // =========================

    public int getOverdueProjects() {

        int count = 0;

        LocalDate today = LocalDate.now();

        for (Project project : dataManager.getProjects()) {

            if (project.isCompleted()) {
                continue;
            }

            LocalDate deadline = project.getDeadline();

            if (deadline != null
                    && deadline.isBefore(today)) {

                count++;
            }
        }

        return count;
    }

    // =========================
    // AVERAGE PROGRESS
    // =========================

    public double getAverageProgress() {

        if (dataManager.getProjects().isEmpty()) {
            return 0.0;
        }

        int totalProgress = 0;

        for (Project project : dataManager.getProjects()) {

            totalProgress += project.getProgress();
        }

        return (double) totalProgress
                / dataManager.getProjects().size();
    }
}