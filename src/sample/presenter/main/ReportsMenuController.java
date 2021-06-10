package sample.presenter.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.services.DBSingleton;
import sample.services.StageFabric;
import sample.services.main.ReportLoader;
import sample.ui.views.Config;

public class ReportsMenuController {
    private final ReportLoader reportLoader = new ReportLoader();
    @FXML
    public TextArea reportTextField;
    @FXML
    public Label messageTextField;
    @FXML
    private Button topTenSalariesButton;
    @FXML
    private Button companyStructureButton;
    @FXML
    private Button backButton;
    @FXML
    private Button averageSalaryButton;
    @FXML
    private Button topTenLoyalButton;

    @FXML
    void initialize() {
        messageTextField.setText(DBSingleton.getInstance().getResourceBundle().getString("report_text"));

        companyStructureButton.setOnAction(actionEvent -> setTextToReportAndMessage(reportLoader.reportStruct()));

        averageSalaryButton.setOnAction(actionEvent -> setTextToReportAndMessage(reportLoader.reportAverageSalary()));

        topTenSalariesButton.setOnAction(actionEvent -> setTextToReportAndMessage(reportLoader.reportTopTenSalaries()));

        topTenLoyalButton.setOnAction(actionEvent -> setTextToReportAndMessage(reportLoader.reportTopTenLoyal()));

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            new StageFabric(Config.MAIN_MENU).stage().show();
        });
    }

    private void setTextToReportAndMessage(String report) {
        reportTextField.setText(report);
        messageTextField.setText(reportLoader.saveReportToFileResult(report));
    }
}
