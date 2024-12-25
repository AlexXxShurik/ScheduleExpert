package org.app;

import database.ScheduleData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Component
public class TableSchedule {

    public TableView<Person> createTable(List<ScheduleData> schedules, int month, int year, int daysInMonth) {
        TableView<Person> tableView = new TableView<>();

        // Создание стандартных столбцов
        TableColumn<Person, Integer> serialCol = new TableColumn<>("ПП");
        serialCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        TableColumn<Person, String> tabCol = new TableColumn<>("Таб. №");
        tabCol.setCellValueFactory(new PropertyValueFactory<>("tabNumber"));

        TableColumn<Person, String> nameCol = new TableColumn<>("ФИО");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().addAll(serialCol, tabCol, nameCol);

        // Генерация столбцов для каждого дня месяца
        for (int day = 1; day <= daysInMonth; day++) {
            TableColumn<Person, String> dayColumn = new TableColumn<>(String.valueOf(day));
            final int columnIndex = day - 1; // Индекс дня в массиве days
            dayColumn.setCellValueFactory(cellData -> {
                String[] days = cellData.getValue().getDays();
                return new SimpleStringProperty(days[columnIndex]);
            });
            tableView.getColumns().add(dayColumn);
        }

        // Преобразование данных из ScheduleData в Person
        ObservableList<Person> data = FXCollections.observableArrayList();
        int serialNumber = 1;
        for (ScheduleData schedule : schedules) {
            String[] patternParts = schedule.getSchedulePattern().split(" ");
            int patternIndex = Integer.parseInt(schedule.getShiftNumber());
            LocalDate scheduleDate = schedule.getDate();
            LocalDate targetDate = LocalDate.of(year, month, 1);

            // Рассчитываем начальный день
            int startDay = 0;
            if (scheduleDate.getMonthValue() == month && scheduleDate.getYear() == year) {
                startDay = scheduleDate.getDayOfMonth() - 1;
            } else if (targetDate.isAfter(scheduleDate)) {
                patternIndex += (int) (ChronoUnit.DAYS.between(scheduleDate, targetDate) % patternParts.length);
            }

            // Проверяем, есть ли запись для текущего табельного номера
            if (!data.isEmpty() && data.get(data.size() - 1).getTabNumber().equals(schedule.getEmployeeNumber().toString())) {
                // Изменяем существующий элемент
                Person lastPerson = data.get(data.size() - 1);
                String[] days = lastPerson.getDays();

                for (int currentDay = startDay; currentDay < days.length; currentDay++) {
                    if (patternIndex >= patternParts.length) {
                        patternIndex = 0; // Сброс шаблона
                    }
                    days[currentDay] = patternParts[patternIndex++];
                }
            } else {
                // Создаем нового человека
                String[] days = new String[daysInMonth];
                Arrays.fill(days, ""); // Инициализация пустыми значениями

                for (int currentDay = startDay; currentDay < days.length; currentDay++) {
                    if (patternIndex >= patternParts.length) {
                        patternIndex = 0; // Сброс шаблона
                    }
                    days[currentDay] = patternParts[patternIndex++];
                }

                data.add(new Person(
                        serialNumber++,
                        schedule.getEmployeeNumber().toString(),
                        schedule.getFullName(),
                        days
                ));
            }
        }


        tableView.setItems(data);
        return tableView;
    }

    private boolean isLeapYear(int year) {
        // Правило определения високосного года
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    // Класс данных
    public static class Person {
        private final String name;
        private final String tabNumber;
        private final int serialNumber;
        private final String[] days;

        public Person(int serialNumber, String tabNumber, String name, String[] days) {
            this.serialNumber = serialNumber;
            this.tabNumber = tabNumber;
            this.name = name;
            this.days = days;
        }

        public int getSerialNumber() {
            return serialNumber;
        }

        public String getTabNumber() {
            return tabNumber;
        }

        public String getName() {
            return name;
        }

        public String[] getDays() {
            return days;
        }
    }
}
