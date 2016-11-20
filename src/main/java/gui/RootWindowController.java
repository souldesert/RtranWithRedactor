package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by svkreml on 23.10.2016.
 */
public class RootWindowController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    private Stage rootWindow;

    public void setRootWindow(Stage rootWindow) {
        this.rootWindow = rootWindow;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void closeProgram() {
        System.exit(0);
    }

    @FXML
    private void newProject() {
        mainApp.initNewProjectWindow();
    }

    @FXML
    private void openProject() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Открытие проекта");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("R-tran", "*.rtran"));
        File projectDirectory = chooser.showOpenDialog(rootWindow);
        try {
            mainApp.getProject().open(projectDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeProject() {
        mainApp.setProject(null);
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
