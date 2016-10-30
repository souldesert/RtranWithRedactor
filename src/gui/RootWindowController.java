package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

/**
 * Created by svkreml on 23.10.2016.
 */
public class RootWindowController {
    @FXML
    private TableColumn<String, String> Метка;
    @FXML
    private TableColumn<String, String> Условие;
    @FXML
    private TableColumn<String, String> ЛинейныеОператоры;
    @FXML
    private TableColumn<String, String> МеткаПерехода;
    @FXML
    private TableColumn<String, String> Комментарии;
    @FXML
    private Tab redactorTab;
    @FXML
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.mainApp.redactorTab = redactorTab;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void закрытьОкно() {
        System.exit(0);
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("About");
        alert.setContentText("текста пример");
        alert.showAndWait();
    }
}
