package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

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






    private MainApp mainApp;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
