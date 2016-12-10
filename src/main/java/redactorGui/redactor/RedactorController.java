package redactorGui.redactor;

import gui.MainApp;
import redactorGui.redactor.addNewLine.addNewLineController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import structure.R_pro;

import java.io.IOException;

/**
 * Created by svkreml on 23.10.2016.
 */
public class RedactorController {


    @FXML
    private TableView<Command> commandTable;

    @FXML
    private TableColumn<Command, String> metkaColumn;

    @FXML
    private TableColumn<Command, String> uslovieColumn;

    @FXML
    private TableColumn<Command, String> linopColumn;

    @FXML
    private TableColumn<Command, String> metkaPerehodaColumn;

    @FXML
    private TableColumn<Command, String> commentsColumn;

    //private mainApp mainApp;
    
    private MainApp mainApp;

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public RedactorController() {
    }

    @FXML
    private void initialize() {
        metkaColumn.setCellValueFactory(cellData -> cellData.getValue().metkaProperty());
        uslovieColumn.setCellValueFactory(cellData -> cellData.getValue().uslovieProperty());
        linopColumn.setCellValueFactory(cellData -> cellData.getValue().linopProperty());
        metkaPerehodaColumn.setCellValueFactory(cellData -> cellData.getValue().metkaPerehodaProperty());
        commentsColumn.setCellValueFactory(cellData -> cellData.getValue().commentsProperty());

        commandTable.setRowFactory(ct -> {
            TableRow<Command> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if(!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {

                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }

            });

            row.setOnDragDropped(event -> {

                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Command dragged = commandTable.getItems().remove(draggedIndex);

                    int dropIndex ;

                    if (row.isEmpty()) {
                        dropIndex = commandTable.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    commandTable.getItems().add(dropIndex, dragged);

                    event.setDropCompleted(true);
                    commandTable.getSelectionModel().select(dropIndex);
                    event.consume();
                    R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
                    mainApp.updateR_pro(updated);
                }

            });

            return row;
        });

    }

    private boolean showCommandEditDialog(Command command) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/redactorGui/redactor/addNewLine.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование команды");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            addNewLineController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCommand(command);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleDeleteCommand() {
        int selectedIndex = commandTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            commandTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не была выбрана ни одна команда");
            alert.setContentText("Пожалуйста, выберите команду в таблице");
            alert.showAndWait();
        }

        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);

    }

    @FXML
    private void handleAddNewLineCommand() {
        Command tempCommand = new Command();
        //Command selectedCommand = commandTable.getSelectionModel().getSelectedItem();
        boolean okClicked = showCommandEditDialog(tempCommand);
        if(okClicked) {
            mainApp.getCommandData().add(tempCommand);
        }
        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);
    }

    @FXML
    private void handleEditCommand() {

        Command selectedCommand = commandTable.getSelectionModel().getSelectedItem();
        showCommandEditDialog(selectedCommand);
        R_pro updated = new R_pro("1.0", mainApp.getR_pro().getProgname(), mainApp.getDescriptive_part(), mainApp.getAlg());
        mainApp.updateR_pro(updated);

    }


//    public void setmainApp(mainApp mainApp) {
//        this.mainApp = mainApp;
//        commandTable.setItems(mainApp.getCommandData());
//    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        commandTable.setItems(mainApp.getCommandData());
    }


}
