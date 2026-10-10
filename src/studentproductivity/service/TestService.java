package studentproductivity.service;

import studentproductivity.data.DataManager;
import studentproductivity.model.Test;

public class TestService {

    private final DataManager dataManager;

    public TestService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void addTest(
            String name,
            String courseId,
            String type,
            String date,
            Double score,
            double maxMarks) {

        Test test = new Test(
                name,
                courseId,
                type,
                date,
                score,
                maxMarks
        );

        dataManager.addTest(test);
        dataManager.saveData();
    }

    public int getTotalTests() {

        return dataManager.getTests().size();
    }

    public double getAveragePercentage() {

        double total = 0.0;
        int completed = 0;

        for (Test test : dataManager.getTests()) {

            if (test.getScore() != null) {

                total += test.getPercentage();
                completed++;
            }
        }

        if (completed == 0) {
            return 0.0;
        }

        return total / completed;
    }

    public int getCompletedTests() {

        int completed = 0;

        for (Test test : dataManager.getTests()) {

            if (test.getScore() != null) {
                completed++;
            }
        }

        return completed;
    }

    public int getUpcomingTests() {

        return getTotalTests() - getCompletedTests();
    }

    public void deleteTest(int index) {

        if (index >= 0
                && index < dataManager.getTests().size()) {

            dataManager.getTests().remove(index);
            dataManager.saveData();
        }
    }
}