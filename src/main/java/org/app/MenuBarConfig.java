package org.app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MenuBarConfig {

    @Bean
    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(createFileMenu(), createHelpMenu());

        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("Файл");
        fileMenu.getItems().addAll(
                createMenuItem("Новый"),
                createMenuItem("Открыть"),
                createMenuItem("Выход", event -> System.exit(0))
        );
        return fileMenu;
    }

    private Menu createHelpMenu() {
        Menu helpMenu = new Menu("Помощь");
        helpMenu.getItems().add(createMenuItem("О программе"));
        return helpMenu;
    }

    private MenuItem createMenuItem(String text) {
        return new MenuItem(text);
    }

    private MenuItem createMenuItem(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(action);
        return menuItem;
    }
}
