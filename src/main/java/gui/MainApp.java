package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Project;

import java.io.IOException;

public class MainApp extends Application {

    Project project=new Project();
    private Stage primaryStage;
    private BorderPane RootWindow;

    public static void main(String[] args) {
        launch(args);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("R_tran");
        initRootWindow();

        //initNewProjectWindow();
    }

    public void initRootWindow() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootWindow.fxml"));
            RootWindow = loader.load();
            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(RootWindow);
            primaryStage.setScene(scene);
            // Give the controller access to the main app.
            RootWindowController controller = loader.getController();
            controller.setRootWindow(primaryStage);
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initNewProjectWindow() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("NewProject.fxml"));
            GridPane NewProject = (GridPane) loader.load();

            Stage newProjectStage = new Stage();
            newProjectStage.setTitle("Создание нового проекта");
            newProjectStage.initModality(Modality.WINDOW_MODAL);
            newProjectStage.initOwner(primaryStage);
            Scene scene = new Scene(NewProject);
            newProjectStage.setScene(scene);
            NewProjectController controller = loader.getController();
            controller.setNewProjectStage(newProjectStage);
            controller.setMainApp(this);
            newProjectStage.showAndWait();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
