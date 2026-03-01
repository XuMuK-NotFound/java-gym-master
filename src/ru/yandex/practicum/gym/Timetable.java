package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
        TreeMap<TimeOfDay, List<TrainingSession>> timetablesForDay = timetable.get(dayOfWeek);

        if (!timetablesForDay.containsKey(timeOfDay)) {
            timetablesForDay.put(timeOfDay, new ArrayList<>());
        }
        timetablesForDay.get(timeOfDay).add(trainingSession);
    }

    public int getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        int count = 0;
        TreeMap<TimeOfDay, List<TrainingSession>> timetablesForDay = timetable.get(dayOfWeek);
        if (timetablesForDay == null) {
            System.out.println("Сегодня занятий нет.");
            return 0;

        }
        for (TimeOfDay timeOfDay : timetablesForDay.navigableKeySet()) {
            List<TrainingSession> sessions = timetablesForDay.get(timeOfDay);
            System.out.println("Время: " + timeOfDay + ", Тренировок в этот час: " + sessions.size());
            count += sessions.size();
        }
        return count;
    }

    public int getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.get(dayOfWeek);
        if (trainingSessionsForDay == null) {
            return 0;
        }

        List<TrainingSession> listOfSessions = trainingSessionsForDay.get(timeOfDay);
        if (listOfSessions == null) {
            return 0;
        }

        return listOfSessions.size();
    }

    public Map<String, Integer> getCountByCoaches() {

        Map<String, Integer> countByCoaches = getCouchesSessions();
        List<String> names = new ArrayList<>(countByCoaches.keySet());
        Map<String, Integer> topCoaches = new LinkedHashMap<>();
        names.sort((a, b) -> countByCoaches.get(b) - countByCoaches.get(a));

        for (String name : names) {
            topCoaches.put(name, countByCoaches.get(name));
            System.out.println("Тренер: " + name + ", кол-во смен: " + countByCoaches.get(name));
        }

        return topCoaches;
    }

    public Map<String, Integer> getCouchesSessions() {

        Map<String, Integer> countByCoaches = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> timetablesForDay : timetable.values()) {

            for (List<TrainingSession> listForTime : timetablesForDay.values()) {

                for (TrainingSession session : listForTime) {
                    String nameCouch = session.getCoach().toString();
                    countByCoaches.merge(nameCouch, 1, Integer::sum);
                }
            }
        }
        return countByCoaches;
    }
}
