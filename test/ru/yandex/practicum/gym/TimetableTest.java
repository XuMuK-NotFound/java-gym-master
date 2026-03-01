package ru.yandex.practicum.gym;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY),
                "Список занятий в понедельник должен возвращать 1");

        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY),
                "Список занятий во вторник должен быть пустым");

    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0)),
                "Список занятий в понедельник должен возвращать 1");

        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.TUESDAY, new TimeOfDay(14, 0)),
                "Список занятий во вторник должен быть пустым");
    }
    @Test
    public  void testShouldCountSessionsCorrectForSpecificDay(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(22, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(8, 0)));

        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY),
                "Должно быть 2 занятия в пятницу при проверке на порядок");

    }
    @Test
    public  void testShouldSortCoachesBySessionCountWithVasilievAsLeader(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachT1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachT2 = new Coach("Крылов", "Александр", "Максимович");

        timetable.addNewTrainingSession(new TrainingSession(group, coachT1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));

        Map<String, Integer> result = timetable.getCountByCoaches();

        String firstInTop = result.keySet().iterator().next();
        assertEquals(coachT1.toString(), firstInTop, "Васильев должен быть на первом месте, так как у него больше смен");

    }
    @Test
    public  void testShouldReturnCorrectUniqueCoachesCountForMultipleSessions(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachT1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachT2 = new Coach("Крылов", "Александр", "Максимович");
        Coach coachT3 = new Coach("Морозов", "Петр", "Борисович");

        timetable.addNewTrainingSession(new TrainingSession(group, coachT1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT2,
                DayOfWeek.THURSDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT3,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT2,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));

        Map<String, Integer> result = timetable.getCountByCoaches();

        assertEquals(3, result.size(), "Должно быть ровно 3 уникальных тренера в мапе");

    }
    @Test
    public  void testShouldCalculateExactSessionCountForEachCoachSeparately(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachT1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachT2 = new Coach("Крылов", "Александр", "Максимович");

        timetable.addNewTrainingSession(new TrainingSession(group, coachT1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT2,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coachT2,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0)));

        Map<String, Integer> result = timetable.getCountByCoaches();

        assertEquals(2, result.get(coachT2.toString()), "У Крылов должно быть насчитано 2 смены");
        assertEquals(1, result.get(coachT1.toString()), "У Васильева должна быть 1 смена");

    }
}
