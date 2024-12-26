package org.app.tabs;

import config.AppConfig;
import database.DatabaseGet;
import database.ScheduleData;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import org.app.ScheduleDisplay;
import org.app.TableSchedule;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class ScheduleTab {

    private final VBox root;
    private final ComboBox<Integer> yearComboBox;
    private final ComboBox<Month> monthComboBox;
    private final ComboBox<String> shiftComboBox;
    private final TableSchedule tableSchedule;
    private final DatabaseGet databaseGet;

    public ScheduleTab() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        tableSchedule = context.getBean(TableSchedule.class);
        databaseGet = context.getBean(DatabaseGet.class);

        yearComboBox = new ComboBox<>(FXCollections.observableArrayList(2023, 2024, 2025));
        yearComboBox.setValue(LocalDate.now().getYear());

        List<Month> monthsInRussian = Arrays.stream(Month.values()).toList();
        monthComboBox = new ComboBox<>(FXCollections.observableArrayList(monthsInRussian));
        monthComboBox.setValue(LocalDate.now().getMonth());

        // Создаем ButtonCell для отображения выбранного месяца
        monthComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Month item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"))); // Полное название на русском
                } else {
                    setText(null);
                }
            }
        });

        monthComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Month item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru")));
                } else {
                    setText(null);
                }
            }
        });

        shiftComboBox = new ComboBox<>(FXCollections.observableArrayList(
                databaseGet.GetAllSchedules(LocalDate.of(LocalDate.now().getYear(), 1, 1),
                                LocalDate.of(LocalDate.now().getYear(), 12, 31),
                                "%")
                        .stream()
                        .map(ScheduleData::getShift)
                        .distinct()
                        .toList()
        ));
        if (!shiftComboBox.getItems().isEmpty()) {
            shiftComboBox.setValue("Выберите график");
        }

        ToolBar toolbar = new ToolBar(yearComboBox, monthComboBox, shiftComboBox);

        yearComboBox.setOnAction(e -> updateTable());
        monthComboBox.setOnAction(e -> updateTable());
        shiftComboBox.setOnAction(e -> updateTable());

        var schedulePane = context.getBean(ScheduleDisplay.class).createDisplay();

        root = new VBox(toolbar, schedulePane);
        updateTable();
    }

    private void updateTable() {
        int year = yearComboBox.getValue();
        Month month = monthComboBox.getValue();
        int daysInMonth = month.length(year % 4 == 0);

        List<ScheduleData> schedules = databaseGet.GetAllSchedules(
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, daysInMonth),
                shiftComboBox.getValue()
        );

        var table = tableSchedule.createTable(schedules, month.getValue(), year, daysInMonth);

        root.getChildren().remove(1); // Удаляем старую таблицу
        root.getChildren().add(1, table); // Добавляем новую таблицу
    }

    public VBox getRoot() {
        return root;
    }
}
