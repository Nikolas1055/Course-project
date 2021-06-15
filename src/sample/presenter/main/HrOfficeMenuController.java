package sample.presenter.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import sample.services.StageFabric;
import sample.ui.views.Config;

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
            new StageFabric(Config.MAIN_MENU).stage().show();
        });
    }
}
