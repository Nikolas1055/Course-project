package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.repository.DBSingleton;
import sample.domain.DataBase;

public class AddNewPostController {

    @FXML
    private TextField newPostTextField;

    @FXML
    private TextArea newPostMessageTextField;

    @FXML
    private Button addNewPostButton;

    @FXML
    void initialize() {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();

        addNewPostButton.disableProperty().bind(
                Bindings.isEmpty(newPostTextField.textProperty())
        );

        addNewPostButton.setOnAction(actionEvent -> {
            if (checkPost(newPostTextField.getText().trim())) {
                dataBase.getPosts().add(newPostTextField.getText().trim());
                newPostMessageTextField.setText("Должность успешно добавлена.");
            } else {
                newPostMessageTextField.setText("Такая должность уже есть в списке должностей.");
            }
        });
    }

    private boolean checkPost(String newPost) {
        for (String post : DBSingleton.getInstance().getDataBase().getPosts()) {
            if (post.equals(newPost)) {
                return false;
            }
        }
        return true;
    }
}
