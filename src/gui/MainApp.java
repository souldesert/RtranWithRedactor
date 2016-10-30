package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    public Tab redactorTab;


    private Stage primaryStage;
    private BorderPane RootWindow;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("R_tran");
        initRootWindow();
        showredactor();
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
    public void showredactor() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Table.fxml"));
            AnchorPane redactor = (AnchorPane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            redactorTab.setContent(redactor);
            //redactorTabAnchorPane.getChildren().addAll(redactor);
            //redactorTabAnchorPane
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
