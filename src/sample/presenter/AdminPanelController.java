package sample.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import sample.services.StageFabric;
import sample.ui.views.Config;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Tab logViewTab;
    @FXML
    private Tab changeEmployeeAuthDataTab;
    @FXML
    private TabPane hrOfficeTabPane;
    @FXML
    private Tab deleteDepartmentTab;
    @FXML
    private Button backButton;
    @FXML
    private Tab changeCompanyDataTab;
    @FXML
    private Label messageTextField;
    @FXML
    private Tab dbOperationsTab;

    @FXML
    void initialize() {

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            new StageFabric(Config.MAIN_MENU).stage().show();
        });

    }
}
