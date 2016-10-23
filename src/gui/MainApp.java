package gui;

import gui.redactor.RedactorController;
import gui.treeLeft.TreeLeftController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane RootWindow;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("R_tran");

        initRootWindow();
        showRedactor();
        showTreeLeft();
    }

    public void initRootWindow() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootWindow.fxml"));
            RootWindow = (BorderPane) loader.load();
            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(RootWindow);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootWindowController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //Redactor
    public void showRedactor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("redactor/Redactor.fxml"));
            Pane redactor = (Pane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            RootWindow.setCenter(redactor);

            RedactorController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showTreeLeft() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("treeLeft/TreeLeft.fxml"));
            Pane redactor = (Pane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            RootWindow.setLeft(redactor);

            TreeLeftController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
