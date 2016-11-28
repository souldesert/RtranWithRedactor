package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Project;

import java.io.IOException;

public class MainApp extends Application {
    public TextArea getTestTextArea() {
        return testTextArea;
    }

    public void setTestTextArea(TextArea testTextArea) {
        this.testTextArea = testTextArea;
    }

    public javafx.scene.control.TabPane getRedactorTabs() {
        return redactorTabs;
    }

    TabPane redactorTabs;
    TextArea testTextArea;
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
        initTree();
        //initNewProjectWindow();
    }

    public TextFlow getErrorConsole() {
        return errorConsole;
    }

    TextFlow errorConsole;
    public void printError(String text){
        Text text2 = new Text(text);
        text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        errorConsole.getChildren().add(text2);
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
            treeViewPane= controller.getTreeViewPane();
            primaryStage.show();
            testTextArea=controller.getTestTextArea();
            redactorTabs =controller.getRedactorTabs();
            this.errorConsole=controller.getErrorConsole();
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
    AnchorPane treeViewPane;

    public TreeController getTreeController() {
        return treeController;
    }

    TreeController treeController;
    public void initTree() {
        try {

            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Tree.fxml"));
            AnchorPane treeView = loader.load();
            // Даём контроллеру доступ к главному приложению.
            treeController = loader.getController();
            //testTextArea= treeController.getT
            treeViewPane.getChildren().add(treeView);
           // treeView.fit
            treeController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
