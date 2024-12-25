package org.app.tabs;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AddPeopleTab {

    private VBox root;

    public AddPeopleTab() {
        root = new VBox();
        initUI();
    }

    private void initUI() {
        root.getChildren().add(new Label("Добавление людей: Здесь будут элементы для добавления людей."));
    }

    public VBox getRoot() {
        return root;
    }
}

