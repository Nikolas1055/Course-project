package sample.presenter.hr;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.services.hr.AddNewPostLoader;
import sample.services.DBSingleton;

import java.util.ResourceBundle;

public class AddNewPostController {
    @FXML
    private TextField newPostTextField;
    @FXML
    private TextArea newPostMessageTextField;
    @FXML
    private Button addNewPostButton;

    @FXML
    void initialize() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        newPostMessageTextField.setText(resourceBundle.getString("new_post_msg1"));

        addNewPostButton.disableProperty().bind(
                Bindings.isEmpty(newPostTextField.textProperty())
        );

        addNewPostButton.setOnAction(actionEvent -> {
            if (new AddNewPostLoader().addNewPost(newPostTextField.getText().trim())) {
                newPostMessageTextField.setText(resourceBundle.getString("new_post_msg2"));
            } else {
                newPostMessageTextField.setText(resourceBundle.getString("new_post_msg3"));
            }
        });
    }
}
