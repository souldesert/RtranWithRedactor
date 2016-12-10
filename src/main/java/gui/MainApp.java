package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
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
import redactorGui.alphabets.alphabetRecord;
import redactorGui.alphabets.alphabetsController;
import redactorGui.memoryTypes.memoryTypeRecord;
import redactorGui.memoryTypes.memoryTypesController;
import redactorGui.redactor.Command;
import redactorGui.redactor.RedactorController;
import structure.*;

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

    public MainApp() {
        r_pro = new R_pro();
        r_pro.setProgname("Без названия");
        redactorTab = new Tab();
        redactorTab.setText("Редактор");
        redactorTab.setClosable(false);
        memoryTypesTab = new Tab();
        memoryTypesTab.setText("Память");
        memoryTypesTab.setClosable(false);
        alphabetsTab = new Tab();
        alphabetsTab.setText("Синтермы");
        alphabetsTab.setClosable(false);
    }

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
        showRedactor();
        redactorTabs.getTabs().add(redactorTab);
        showMemoryTypes();
        redactorTabs.getTabs().add(memoryTypesTab);
        showAlphabets();
        redactorTabs.getTabs().add(alphabetsTab);
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
            primaryStage.setTitle(r_pro.getProgname());
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

    // ----------------- Редактор ----------------------------

    private Tab redactorTab;
    private Tab memoryTypesTab;
    private Tab alphabetsTab;
    //private Stage primaryStage;
    //private BorderPane RootWindow;
    private ObservableList<Command> commandData = FXCollections.observableArrayList();
    private ObservableList<memoryTypeRecord> memoryTypesData = FXCollections.observableArrayList();
    private ObservableList<alphabetRecord> alphabetsData = FXCollections.observableArrayList();

    private R_pro r_pro; // в этом объекте хранится сама программа Р-тран

    public R_pro getR_pro() {
        return r_pro;
    }

    public void updateR_pro(R_pro r_pro) {
        this.r_pro = r_pro;
    }




    public void setRedactorTab(Tab redactorTab) {
        this.redactorTab = redactorTab;
    }

    public void setMemoryTypesTab(Tab memoryTypesTab) {
        this.memoryTypesTab = memoryTypesTab;
    }

    public void setAlphabetsTab(Tab alphabetsTab) {
        this.alphabetsTab = alphabetsTab;
    }




//    public RedactorModule() {
//        r_pro = new R_pro();
//        r_pro.setProgname("Без названия");
//
//    }

    public ObservableList<Command> getCommandData() {
        return commandData;
    }
    public ObservableList<memoryTypeRecord> getMemoryTypesData() { return memoryTypesData; }
    public ObservableList<alphabetRecord> getAlphabetsData() { return alphabetsData; }


//    @Override
//    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        //this.primaryStage.setTitle("R_tran");
//
//        initRootWindow();
//        showRedactor();
//        showMemoryTypes();
//        showAlphabets();
//        this.getPrimaryStage().show();
//        //showTreeLeft();
//    }

//    public void initRootWindow() {
//        try {
//            // Загружаем корневой макет из fxml файла.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(RedactorModule.class.getClassLoader().getResource("RedactorWindow.fxml"));
//            RootWindow = (BorderPane) loader.load();
//            // Отображаем сцену, содержащую корневой макет.
//            Scene scene = new Scene(RootWindow);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle(r_pro.getProgname());
//
//            // Give the controller access to the main app.
//            RedactorModuleController controller = loader.getController();
//            controller.setRedactorModule(this);
//
//            //primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//    }
    //Redactor
    private void showRedactor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/redactorGui/redactor/Redactor.fxml"));
            AnchorPane redactor = (AnchorPane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            redactorTab.setContent(redactor);
            //Scene scene = new Scene(redactor);
            //primaryStage.setScene(scene);


            RedactorController controller = loader.getController();
            controller.setMainApp(this);

            //primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMemoryTypes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/redactorGui/memoryTypes/memoryTypes.fxml"));
            AnchorPane memoryTypes = (AnchorPane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            memoryTypesTab.setContent(memoryTypes);
            //Scene scene = new Scene(redactor);
            //primaryStage.setScene(scene);


            memoryTypesController controller = loader.getController();
            //controller.setRedactorModule(this);
            controller.setMainApp(this);

            //primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlphabets() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/redactorGui/alphabets/alphabets.fxml"));
            AnchorPane alphabets = (AnchorPane) loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            alphabetsTab.setContent(alphabets);
            //Scene scene = new Scene(redactor);
            //primaryStage.setScene(scene);


            alphabetsController controller = loader.getController();
            //controller.setRedactorModule(this);
            controller.setMainApp(this);

            //primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Memory> getMemory() {
        ObservableList<Memory> memory = FXCollections.observableArrayList();
        for (memoryTypeRecord record : memoryTypesData) {
            memory.add(new Memory(record.getType(), record.getName()));
        }
        return memory;
    }

    public ObservableList<Abc> getAbcs() {
        ObservableList<Abc> abcs = FXCollections.observableArrayList();
        for(alphabetRecord record : alphabetsData) {
            abcs.add(new Abc(record.getName(), record.getShortName(), record.getComments(), record.getValues()));
        }
        return abcs;
    }

    public Descriptive_part getDescriptive_part() {
        Memory_block memory_block = new Memory_block(getMemory());
        Alphabet alphabet = new Alphabet(getAbcs());
        Descriptive_part descriptive_part = new Descriptive_part(memory_block, alphabet);
        return descriptive_part;
    }


    // TODO: 27.11.2016 сделать разбор операции
    // TODO: 27.11.2016 сделать другие типы предикатов кроме expression
    public Alg getAlg() {
        Alg alg = new Alg();
        int curArm = -1;
        int curEdge = 0;
        for (Command record : commandData) {
            switch (record.getFlag()) {
                case TAG:

                    Left left = new Left(record.getLinop().split(" ")[0]);
                    String operator = record.getLinop().split(" ")[1];
                    Right right = new Right(record.getLinop().split(" ")[2]);
                    Operation operation = new Operation(left, operator, right);

                    String type = "expression";
                    Memory memoryLeft = new Memory(record.getUslovie().split(" ")[0]);
                    String sign = record.getUslovie().split(" ")[1];
                    Memory memoryRight = new Memory(record.getUslovie().split(" ")[2]);
                    Predicate predicate = new Predicate(type, memoryLeft, sign, memoryRight);

                    curEdge = 0;

                    Edge edge = new Edge(predicate, operation);
                    edge.addEnd(record.getMetkaPerehoda());

                    Arm arm = new Arm(record.getMetka());
                    arm.addEdge(edge);
                    alg.addArm(arm);
                    curArm++;

                    System.out.println(record.getFlag() + ". Записано: " + curArm + curEdge + " " + record.getLinop());

                    break;

                case CONDITION:

                    left = new Left(record.getLinop().split(" ")[0]);
                    operator = record.getLinop().split(" ")[1];
                    right = new Right(record.getLinop().split(" ")[2]);
                    operation = new Operation(left, operator, right);

                    type = "expression";
                    memoryLeft = new Memory(record.getUslovie().split(" ")[0]);
                    sign = record.getUslovie().split(" ")[1];
                    memoryRight = new Memory(record.getUslovie().split(" ")[2]);
                    predicate = new Predicate(type, memoryLeft, sign, memoryRight);

                    curEdge++;

                    edge = new Edge(predicate, operation);
                    edge.addEnd(record.getMetkaPerehoda());

                    alg.getArm(curArm).addEdge(edge);

                    System.out.println(record.getFlag() + ". Записано: " + curArm + curEdge + " " + record.getLinop());

//                    Arm currentArm = alg.getArm(curArm);
//                    currentArm.addEdge(edge);
//                    alg.updateArm(currentArm);

                    break;

                case OPERATOR:

                    left = new Left(record.getLinop().split(" ")[0]);
                    operator = record.getLinop().split(" ")[1];
                    right = new Right(record.getLinop().split(" ")[2]);
                    operation = new Operation(left, operator, right);

                    alg.getArm(curArm).getEdge(curEdge).addOperation(operation);
                    alg.getArm(curArm).getEdge(curEdge).addEnd(record.getMetkaPerehoda());

                    //System.out.println(record.getFlag() + ". Записано: " + curArm + curEdge + " " + record.getLinop());
                    break;
            }
        }
        return alg;
    }

}
