package sample.presenter.main;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.domain.Department;
import sample.services.DBSingleton;
import sample.services.main.FinderMenuLoader;
import sample.ui.CommonUiService;

public class FinderMenuController {
    private final FinderMenuLoader finderMenuLoader = new FinderMenuLoader();
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private TextArea findByChiefFullNameResultTextField;
    @FXML
    private TextArea findByDepartmentResultTextField;
    @FXML
    private ComboBox<String> postComboBox;
    @FXML
    private Button findByFullNameButton;
    @FXML
    private Label messageTextField;
    @FXML
    private TextArea findByPostResultTextField;
    @FXML
    private TextArea findByFullNameResultTextField;
    @FXML
    private TextField chiefFullNameTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button findByChiefFullNameButton;

    @FXML
    void initialize() {
        setFinderPresets();

        postComboBox.setOnAction(actionEvent -> setTextToFinderResultAndMessage(
                finderMenuLoader.findEmployeeByPost(postComboBox.getValue()),
                findByPostResultTextField));

        departmentComboBox.setOnAction(actionEvent -> setTextToFinderResultAndMessage(
                finderMenuLoader.findByDepartment(departmentComboBox.getValue()),
                findByDepartmentResultTextField));

        findByFullNameButton.setOnAction(actionEvent -> setTextToFinderResultAndMessage(
                finderMenuLoader.findByFullName(fullNameTextField.getText().trim()),
                findByFullNameResultTextField));

        findByChiefFullNameButton.setOnAction(actionEvent -> setTextToFinderResultAndMessage(
                finderMenuLoader.findByChiefFullName(chiefFullNameTextField.getText().trim()),
                findByChiefFullNameResultTextField));

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }

    private void setTextToFinderResultAndMessage(String report, TextArea textArea) {
        textArea.setText(report);
        messageTextField.setText(finderMenuLoader.saveFindResultToFile(report));
    }

    private void setFinderPresets() {
        messageTextField.setText(DBSingleton.getInstance().getResourceBundle().getString("finder_msg1"));
        postComboBox.setItems(CommonUiService.getPostsForFinder());
        departmentComboBox.setItems(CommonUiService.getDepartments());
        CommonUiService.setDepartment(departmentComboBox);
        findByFullNameButton.disableProperty().bind(
                Bindings.isEmpty(fullNameTextField.textProperty())
        );
        findByChiefFullNameButton.disableProperty().bind(
                Bindings.isEmpty(chiefFullNameTextField.textProperty())
        );
    }
}
