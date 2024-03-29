package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by svkreml on 20.11.2016.
 */
public class NewProjectController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    File selectedDirectory;
    File projectDirectory;
    @FXML
    private TextField projectName;
    @FXML
    private TextField directoryName;
    @FXML
    private Label errorLabel;


    private Stage newProjectStage;

    public void setNewProjectStage(Stage newProjectStage) {
        this.newProjectStage = newProjectStage;
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        newProjectStage.close();
    }

    @FXML
    public void handleChooseDirectory() throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Создание проекта");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        selectedDirectory = chooser.showDialog(newProjectStage);
        directoryName.setText(selectedDirectory.toString());
        errorLabel.setText("");

    }
    @FXML
    public void handleCreateProject() throws IOException {
        projectDirectory=new File(selectedDirectory, projectName.getText());
        if(projectDirectory.isDirectory()) {
            errorLabel.setText("Папка уже существует");
            //todo создать окно ошибки
        }
        else if(!projectDirectory.mkdir()) {
            errorLabel.setText("Ошибка создания");
            //todo создать окно ошибки
        }
        else {
            createProjectFiles(projectDirectory);
            TreeController treeController = mainApp.getTreeController();
            treeController.openProject(new File(projectDirectory, "prject.rtran").toPath());
            newProjectStage.close();
        }
    }


    public void createProjectFiles(File directory){
        try {
            mainApp.getProject().create(directory);
            File input = new File(directory,"input");
            input.mkdir();
            File output = new File(directory,"output");
            output.mkdir();
            File test = new File(directory,"test");
            test.mkdir();
            File program = new File(directory,"program.rtran");
            program.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
