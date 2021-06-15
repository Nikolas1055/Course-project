package sample.presenter.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import sample.services.DBSingleton;
import sample.services.admin.DataBaseOperationsLoader;

import java.util.Objects;
import java.util.ResourceBundle;

public class DataBaseOperationsController {
    @FXML
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
    @FXML
    private Button saveDataBaseButton;
    @FXML
    private TextArea dataBaseOperationsTextArea;

    @FXML
    void initialize() {
        dataBaseOperationsTextArea.setText(resourceBundle.getString("db_msg1"));

        saveDataBaseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                setButtonEffects(resourceBundle.getString("db_msg2"), saveDataBaseButton));

        saveDataBaseButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                removeButtonEffect(saveDataBaseButton));

        saveDataBaseButton.setOnAction(actionEvent -> {
            String saveDataBaseResult = new DataBaseOperationsLoader().saveDataBase();
            alerter(resourceBundle.getString("save_dataBase"),
                    Objects.requireNonNullElseGet(saveDataBaseResult, () -> resourceBundle.getString("db_msg3")));
        });

    }

    private void setButtonEffects(String string, Button button) {
        button.setEffect(new DropShadow());
        dataBaseOperationsTextArea.setText(string);
    }

    private void removeButtonEffect(Button button) {
        button.setEffect(null);
        dataBaseOperationsTextArea.setText(resourceBundle.getString("db_msg1"));
    }

    private void alerter(String title, String message) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION, message);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.showAndWait();
    }
}
