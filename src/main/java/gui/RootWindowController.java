package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.TabSet;
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

    public AnchorPane getTreeViewPane() {
        return treeViewPane;
    }

    @FXML
    private AnchorPane treeViewPane;

    public TextFlow getErrorConsole() {
        return errorConsole;
    }


    @FXML
    private TextFlow errorConsole;

    @FXML
    private void initialize() {

    }

    public TextArea getTestTextArea() {
        return testTextArea;
    }

    @FXML
    TextArea testTextArea;

    public TabPane getRedactorTabs() {
        return redactorTabs;
    }

    @FXML
    TabPane redactorTabs;
    @FXML
    private void closeProgram() {
        System.exit(0);
    }

    @FXML
    private void newProject() {
        mainApp.initNewProjectWindow();
    }

    @FXML
    private void openProject() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Открытие проекта");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("R-tran", "project.rtran"));
        File projectDirectory = chooser.showOpenDialog(rootWindow);
        TreeController treeController = mainApp.getTreeController();
        treeController.openProject(projectDirectory.toPath());
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
