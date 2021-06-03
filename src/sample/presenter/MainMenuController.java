package sample.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.services.DBSingleton;
import sample.services.MainMenuLoader;
import sample.services.StageFabric;
import sample.ui.views.Config;

public class MainMenuController {
    @FXML
    private Button hrOfficeButton;
    @FXML
    private Button adminPanelButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Label messageTextField;
    @FXML
    private Button findEmployeeButton;

    @FXML
    void initialize() {
        MainMenuLoader mainMenuLoader = new MainMenuLoader();
        if (mainMenuLoader.checkAuthRoleIsNull()) {
            hrOfficeButton.setVisible(false);
            adminPanelButton.setVisible(false);
        } else if (mainMenuLoader.checkAuthRoleIsHrOfficer()) {
            adminPanelButton.setVisible(false);
        }
        messageTextField.setText(DBSingleton.getInstance().getResourceBundle().getString("main_message3"));
        hrOfficeButton.setOnAction(actionEvent -> new StageFabric(Config.HR_OFFICE_MENU).stage().show());
        reportsButton.setOnAction(actionEvent -> new StageFabric(Config.REPORTS_MENU).stage().show());
        findEmployeeButton.setOnAction(actionEvent -> new StageFabric(Config.FINDER_MENU).stage().show());
    }
}
