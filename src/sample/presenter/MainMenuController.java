package sample.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import sample.services.DBSingleton;
import sample.services.MainMenuLoader;
import sample.services.StageFabric;
import sample.ui.views.Config;

import java.util.ResourceBundle;

public class MainMenuController {
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
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

        hrOfficeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                setButtonEffects(resourceBundle.getString("main_hr_msg"), hrOfficeButton));

        hrOfficeButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                removeButtonEffect(hrOfficeButton));

        adminPanelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                setButtonEffects(resourceBundle.getString("main_admin_msg"), adminPanelButton));

        adminPanelButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                removeButtonEffect(adminPanelButton));

        reportsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                setButtonEffects(resourceBundle.getString("main_report_msg"), reportsButton));

        reportsButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                removeButtonEffect(reportsButton));

        findEmployeeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent ->
                setButtonEffects(resourceBundle.getString("main_find_msg"), findEmployeeButton));

        findEmployeeButton.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent ->
                removeButtonEffect(findEmployeeButton));


        messageTextField.setText(resourceBundle.getString("main_message3"));
        hrOfficeButton.setOnAction(actionEvent -> new StageFabric(Config.HR_OFFICE_MENU).stage().show());
        reportsButton.setOnAction(actionEvent -> new StageFabric(Config.REPORTS_MENU).stage().show());
        findEmployeeButton.setOnAction(actionEvent -> new StageFabric(Config.FINDER_MENU).stage().show());
        adminPanelButton.setOnAction(actionEvent -> new StageFabric(Config.ADMIN_PANEL).stage().show());
    }

    private void setButtonEffects(String message, Button button) {
        messageTextField.setText(message);
        button.setEffect(new DropShadow());
    }

    private void removeButtonEffect(Button button) {
        messageTextField.setText(resourceBundle.getString("main_message3"));
        button.setEffect(null);
    }
}
