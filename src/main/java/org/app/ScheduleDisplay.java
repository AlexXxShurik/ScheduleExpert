package org.app;

import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDisplay {

    private final TableSchedule tableSchedule;

    public ScheduleDisplay(TableSchedule tableSchedule) {
        this.tableSchedule = tableSchedule;
    }

    public VBox createDisplay() {
        return new VBox(); // Здесь будет контейнер для таблицы расписания
    }
}
