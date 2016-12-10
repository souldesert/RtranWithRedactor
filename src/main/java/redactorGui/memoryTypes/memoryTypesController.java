package redactorGui.memoryTypes;

import gui.MainApp;
import redactorGui.memoryTypes.addNewMemoryType.addNewMemoryTypeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import structure.R_pro;

import java.io.IOException;

/**
 * Created by Alex on 20.11.2016.
 */
public class memoryTypesController {

    @FXML
    private TableView<memoryTypeRecord> memoryTypesTable;

    @FXML
    private TableColumn<memoryTypeRecord, String> typeColumn;

    @FXML
    private TableColumn<memoryTypeRecord, String> nameColumn;

    @FXML
    private TableColumn<memoryTypeRecord, String> commentsColumn;

   // private mainApp mainApp;
    private MainApp mainApp;

    @FXML
    private void initialize() {
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        commentsColumn.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());
    }

//    public void setmainApp(mainApp mainApp) {
//        this.mainApp = mainApp;
//        memoryTypesTable.setItems(mainApp.getMemoryTypesData());
//    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        memoryTypesTable.setItems(mainApp.getMemoryTypesData());
    }

    private boolean showMemoryEditDialog(memoryTypeRecord memoryRecord) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/redactorGui/memoryTypes/addNewMemoryType.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование памяти");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            addNewMemoryTypeController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRecord(memoryRecord);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleDeleteRecord() {
        int selectedIndex = memoryTypesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            memoryTypesTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не была выбрана ни одна запись");
            alert.setContentText("Пожалуйста, выберите запись в таблице");
            alert.showAndWait();
        }

        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);

    }

    @FXML
    private void handleAddNewMemoryType() {
        memoryTypeRecord tempRecord = new memoryTypeRecord();
        //Command selectedCommand = commandTable.getSelectionModel().getSelectedItem();
        boolean okClicked = showMemoryEditDialog(tempRecord);
        if(okClicked) {
            mainApp.getMemoryTypesData().add(tempRecord);
        }

        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);

    }

    @FXML
    private void handleEditRecord() {

        memoryTypeRecord selectedRecord = memoryTypesTable.getSelectionModel().getSelectedItem();
        showMemoryEditDialog(selectedRecord);

        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);

    }

}
