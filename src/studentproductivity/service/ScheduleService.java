package studentproductivity.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import studentproductivity.data.DataManager;
import studentproductivity.model.TimetableEntry;

public class ScheduleService {

    private final DataManager dataManager;

    public ScheduleService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<TimetableEntry> getTodaySchedule() {
        String today = getTodayName();
        List<TimetableEntry> todaySchedule = new ArrayList<>();

        for (TimetableEntry entry : dataManager.getTimetableEntries()) {
            if (entry.getDay().equalsIgnoreCase(today)) {
                todaySchedule.add(entry);
            }
        }

        return todaySchedule;
    }

    private String getTodayName() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();

        switch (day) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return "";
        }
    }
}