package org.app.tabs;

import database.DatabaseGet;
import database.PeopleData;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShiftSettingsTab {

    private final DatabaseGet databaseGet;
    private VBox root;

    @Autowired
    public ShiftSettingsTab(DatabaseGet databaseGet) {
        this.databaseGet = databaseGet;
        root = new VBox();
        initUI();
    }

    private void initUI() {
        // Получаем данные из базы
        List<PeopleData> peopleDataList = databaseGet.getAllPeople("Смена мастера Иванова"); // Пример смены

        // Создаём контейнер для таблицы
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Заголовки колонок
        gridPane.add(new Label("Табельный номер"), 0, 0);
        gridPane.add(new Label("Сотрудник"), 1, 0);
        gridPane.add(new Label("Смена"), 2, 0);

        // Создаём строки для каждой записи
        for (int i = 0; i < peopleDataList.size(); i++) {
            PeopleData person = peopleDataList.get(i);

            // Табельный номер
            TextField idField = new TextField(String.valueOf(person.getEmployee_number()));
            gridPane.add(idField, 0, i + 1);

            // Сотрудник (не редактируемый)
            Label nameLabel = new Label(person.getFull_name());
            gridPane.add(nameLabel, 1, i + 1);

            // Смена (для редактирования)
            TextField shiftComboBox = new TextField(String.valueOf(person.getShift()));
            gridPane.add(shiftComboBox, 2, i + 1);

            // Обработчик для изменения значений в shiftComboBox
            shiftComboBox.textProperty().addListener((observable, oldValue, newValue) -> {
                person.setShift(newValue); // Обновляем объект с новой сменой
                updateDatabase(person); // Метод для обновления базы данных
            });
        }

        root.getChildren().add(gridPane);
    }

    private void updateDatabase(PeopleData person) {
        // Пример: обновляем запись в базе данных
        databaseGet.updatePersonShift(person); // Метод для обновления записи в базе данных
    }

    public VBox getRoot() {
        return root;
    }
}