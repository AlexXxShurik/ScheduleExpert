package org.app.tabs;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class ShiftSettingsTab {

    private VBox root;

    public ShiftSettingsTab() {
        root = new VBox();
        initUI();
    }

    private void initUI() {
        root.getChildren().add(new Label("Настройка смен: Здесь будут элементы для настройки смен."));
    }

    public VBox getRoot() {
        return root;
    }
}

