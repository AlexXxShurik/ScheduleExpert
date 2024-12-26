package org.app;

import config.AppConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.app.tabs.AddPeopleTab;
import org.app.tabs.ScheduleTab;
import org.app.tabs.ShiftSettingsTab;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApplication extends Application {

    private AnnotationConfigApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage primaryStage) {

        // Получаем MenuBarConfig из контекста и создаем меню-бар
        MenuBarConfig menuBarConfig = context.getBean(MenuBarConfig.class);
        var menuBar = menuBarConfig.createMenuBar();

        ShiftSettingsTab shiftSettingsTabBeen = context.getBean(ShiftSettingsTab.class);
        AddPeopleTab addPeopleTabBeen = context.getBean(AddPeopleTab.class);

        TabPane tabPane = new TabPane();

        Tab scheduleTab = new Tab("Графики выходов", new ScheduleTab().getRoot());
        scheduleTab.setClosable(false);
        Tab shiftSettingsTab = new Tab("Настройка смен", shiftSettingsTabBeen.getRoot());
        shiftSettingsTab.setClosable(false);
        Tab addPeopleTab = new Tab("Добавление людей", addPeopleTabBeen.getRoot());
        addPeopleTab.setClosable(false);

        tabPane.getSelectionModel().select(scheduleTab); // Устанавливаем первую вкладку как начальную
        tabPane.getTabs().addAll(scheduleTab, shiftSettingsTab, addPeopleTab);

        // Добавляем меню-бар перед таб-панелью
        VBox root = new VBox(menuBar, tabPane); // Меню-бар добавляем сверху
        Scene scene = new Scene(root, 1024, 768); // Устанавливаем VBox в сцену

        primaryStage.setTitle("Графики и настройки");
        primaryStage.setScene(scene); // Устанавливаем сцену
        primaryStage.show(); // Показать Stage
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
