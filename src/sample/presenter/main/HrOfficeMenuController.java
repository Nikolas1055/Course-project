package sample.presenter.main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HrOfficeMenuController {
    @FXML
    private Tab addNewEmployeeTab;
    @FXML
    private Tab addNewDepartmentTab;
    @FXML
    private Tab dismissEmployeeTab;
    @FXML
    private Tab addNewPostTab;
    @FXML
    private Tab changeEmployeeTab;
    @FXML
    private Button backButton;
    @FXML
    private Tab changeDepartmentTab;
    @FXML
    private Label messageTextField;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }
}
