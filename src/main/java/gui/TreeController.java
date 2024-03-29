package gui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.util.StringConverter;
import tree.AnyInfo;
import tree.FileInfo;
import tree.FolderInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TreeController {
    private final Image folderImage = new Image("/tree/folder-icon.png");
    private final Image fileImage = new Image("/tree/File-512.png");
    MainApp mainApp;
    @FXML
    TreeView treeView;
    Path projectDirectory;
    // new!!!!!!!!
    TreeItem oldTreeItem;
    TabPane redactorTabs;

    BiMap<Path, Tab> tabs = HashBiMap.create();
    boolean magic = false;
    Map<Tab, TreeItem> tabTree = new HashMap<>();
    private TreeItem<AnyInfo> dragging = null;

    static String readFile(Path path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, encoding);
    }

    private static boolean containsParent(TreeItem<?> parent, TreeItem<?> child) {
        TreeItem<?> current = child;
        while (current != null) {
            if (current.equals(parent))
                return true;
            current = current.getParent();
        }
        return false;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private TreeItem<AnyInfo> cloneTree(TreeItem<AnyInfo> item) {
        TreeItem<AnyInfo> copy;
        if (item.getValue() instanceof FolderInfo)
            copy = new TreeItem<>(item.getValue(), new ImageView(folderImage));
        else
            copy = new TreeItem<>(item.getValue(), new ImageView(fileImage));
        for (TreeItem<AnyInfo> child : item.getChildren()) {
            copy.getChildren().add(cloneTree(child));
        }
        return copy;
    }

    private void saveFolders(Path folder, TreeItem<AnyInfo> item) throws IOException {
        /*if(projectDirectory==null)
            return;
        AnyInfo value = item.getValue();
        Path file = folder.resolve(value.getName());
        if (value instanceof FolderInfo) {
            Files.createDirectories(file);
            for (TreeItem<AnyInfo> child : item.getChildren()) {
                saveFolders(file, child);
            }
        } else {
            Files.createFile(file);
            //todo заполнить файл содержимым
        }*/
    }



    void openProject(Path path) throws IOException {
        redactorTabs = mainApp.getRedactorTabs();
        projectDirectory = path;
        TreeItem<AnyInfo> rootItem = loadFolders(path.getParent());
        treeView.setRoot(rootItem);
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                showMenu(treeView, event);
            }
        });
        treeView.setEditable(true);
        treeView.setShowRoot(true);
        treeView.setCellFactory(param -> {
            TextFieldTreeCell<AnyInfo> cell = new TextFieldTreeCell<>();
            StringConverter<AnyInfo> converter = new StringConverter<AnyInfo>() {

                @Override
                public String toString(AnyInfo object) {
                    return object.getName();
                }

                @Override
                public AnyInfo fromString(String string) {
                    TreeItem<AnyInfo> item = cell.getTreeItem();
                    AnyInfo info = item.getValue();
                    info.setName(string);
                    sortChildren(item.getParent());
                    cell.getTreeView().getSelectionModel().select(item);
                    item.getValue().setName(string);
                    Path newName = Paths.get(item.getValue().getPath().getParent().toString(), string);
                    try {
                        Files.move(item.getValue().getPath(), newName);
                        item.getValue().setPath(newName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return info;
                }
            };
            cell.setOnDragDetected(event -> {
                Dragboard dragboard = cell.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                TreeItem<AnyInfo> treeItem = cell.getTreeItem();
                ClipboardContent content = new ClipboardContent();
//                content.put(NODE_DATA, toXml(treeItem));
                content.putString(treeItem.getValue().getName());
                dragboard.setContent(content);
                dragging = treeItem;
                event.consume();
            });
            treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateSelectedItem(newValue));
            cell.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
            cell.setOnDragDropped(event -> {
                //if(cell.getClass()==)
                Dragboard dragboard = event.getDragboard();
//                XmlNode content = (XmlNode) dragboard.getContent(NODE_DATA);
                MenuItem copyItem = new MenuItem("Копировать");
                MenuItem moveItem = new MenuItem("Перенести");
                TreeItem<AnyInfo> dropped = dragging; // куда перетаскиваем
                TreeItem<AnyInfo> dragged = cell.getTreeItem(); // то, что перетаскиваем
                if (containsParent(dropped, dragged)) {
                    // todo: показать ошибку
                    return;
                }
                if (dragged.getValue() instanceof FolderInfo) {
                    moveItem.setOnAction(e -> {
//                    dropped.getParent().getChildren().remove(dropped);
//                    TreeItem<AnyInfo> copyNode = fromXml(content);
//                    cell.getTreeItem().getChildren().add(copyNode);
//                    tree.getSelectionModel().select(copyNode);
//if(cell.getTreeItem().getValue()!=dropped)
                        //todo проверка на перемещение корня в внутрь себя

                        try {
                            Files.move(dropped.getValue().getPath(), Paths.get(dragged.getValue().getPath().toString(),
                                    dropped.getValue().getPath().getFileName().toString()));
                            dropped.getValue().setPath(Paths.get(dragged.getValue().getPath().toString(),
                                    dropped.getValue().getPath().getFileName().toString()));
                            dropped.getParent().getChildren().remove(dropped);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        insert(cell.getTreeItem(), dropped);
                        treeView.getSelectionModel().select(dropped);

                    });
                    copyItem.setOnAction(e -> {
//                    TreeItem<AnyInfo> copyNode = fromXml(content);
//                    cell.getTreeItem().getChildren().add(copyNode);
//                    tree.getSelectionModel().select(copyNode);
                        TreeItem<AnyInfo> copyNode = cloneTree(dropped);
                        insert(cell.getTreeItem(), copyNode);

                        try {

                            Files.copy(dropped.getValue().getPath(), Paths.get(cell.getTreeItem().getValue().getPath().toString(),
                                    dropped.getValue().getPath().getFileName().toString()));
                            dropped.getValue().setPath(Paths.get(cell.getTreeItem().getValue().getPath().toString(),
                                    dropped.getValue().getPath().getFileName().toString()));
                            dropped.getParent().getChildren().remove(dropped);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        insert(cell.getTreeItem(), dropped);
                        treeView.getSelectionModel().select(dropped);
                        treeView.getSelectionModel().select(copyNode);
                    });

                    dragging = null;
                    ContextMenu menu = new ContextMenu(copyItem, moveItem);
                    menu.show(treeView.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                } else System.out.println(" это не папка");
            });
            cell.setConverter(converter);
            return cell;
        });
    }

    private TreeItem<AnyInfo> loadFolders(Path file) throws IOException {
        if (Files.isDirectory(file)) {
            TreeItem<AnyInfo> folder = new TreeItem<>(new FolderInfo(file.getFileName().toString(), file.toAbsolutePath()), new ImageView(folderImage));
            Files.list(file).forEach(path -> {
                try {
                    folder.getChildren().add(loadFolders(path));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            sortChildren(folder);
            return folder;
        } else {
            return new TreeItem<>(new FileInfo(file.getFileName().toString(), file.toAbsolutePath()), new ImageView(fileImage));
        }
    }

    @FXML
    public void initialize() {
 /*       Tab tab = new Tab();
        tab.getOnClosed(), setOnClosed(new EventHandler<Event>(){
            @Override void handle(Event e){
                // What you have to do here
            }
        });*/
    }

    private void updateSelectedItem(Object newValue) {
        SingleSelectionModel<Tab> selectionModelTabs = redactorTabs.getSelectionModel();
        MultipleSelectionModel<TreeItem> selectionModelTree = treeView.getSelectionModel();
        TreeItem newTreeItem = (TreeItem) newValue;
        if (newTreeItem == null) return;
        if (!newTreeItem.equals(oldTreeItem) && newTreeItem.getValue() instanceof FileInfo) {
            oldTreeItem = newTreeItem;
            System.out.println(((FileInfo) newTreeItem.getValue()).getPath());
            FileInfo file = (FileInfo) newTreeItem.getValue();

            //mainApp.getTestTextArea().setText(readFile(((FileInfo) newTreeItem.getValue()).getPath(), Charset.defaultCharset()));
            Tab tab = new Tab();
            tab.setOnCloseRequest(arg0 -> {
                Tab cTab = (Tab) arg0.getTarget();
                BiMap<Tab, Path> inverse = tabs.inverse();
                tabs.remove(inverse.get(cTab));
                tabTree.remove(cTab);
            });
            tab.setOnSelectionChanged((arg0) -> {
       /*         //int row = treeView.getRow();
                //System.out.println("row = " + row);
                if (selectionModelTree.getSelectedItem() != tabTree.get(arg0.getTarget())) ;
                {
                    selectionModelTree.select(tabTree.get(arg0.getTarget()));
                }*/

       //todo надо переключать вкладки
            });
            if (!tabs.containsKey(file.getPath())) {
                tabTree.put(tab, newTreeItem);
                tabs.put(file.getPath(), tab);
                TextArea textArea = new TextArea();
                try {
                    textArea.setText(readFile(((FileInfo) newTreeItem.getValue()).getPath(), Charset.defaultCharset()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tab.setText(file.getName());
                tab.setContent(textArea);
                tab.setClosable(true);
                redactorTabs.getTabs().add(tab);
            } else {
                selectionModelTabs.select(tabs.get(file.getPath()));

            }
            if (file.getName().equals("project.rtran")) {
                //todo что-то сделать?
            } else if (file.getName().equals("program.rtran")) {
                //todo запустить редактор кода
            } else {
                //todo редактор текста
                //todo сделать сохранение изменений.
                //todo передавать путь, а не текст
                //mainApp.getTestTextArea().setText(readFile(((FileInfo) newTreeItem.getValue()).getPath(), Charset.defaultCharset()));
            }
        }
    }

    private void insert(TreeItem<AnyInfo> parent, TreeItem<AnyInfo> child) {
        ObservableList<TreeItem<AnyInfo>> children = parent.getChildren();
        children.add(child);
        sortChildren(parent);
    }

    private void sortChildren(TreeItem<AnyInfo> parent) {
        // if(parent.getChildren().size()==1) return;
        (parent.getChildren()).sort((o1, o2) -> {
            AnyInfo v1 = o1.getValue();
            AnyInfo v2 = o2.getValue();
            if (v1 instanceof FolderInfo && v2 instanceof FileInfo)
                return 1;
            if (v1 instanceof FileInfo && v2 instanceof FolderInfo)
                return -1;
            return v1.getName().compareToIgnoreCase(v2.getName());
        });
    }

    private void showMenu(TreeView<AnyInfo> tree, MouseEvent event) {
        ContextMenu menu;
        TreeItem<AnyInfo> current = tree.getSelectionModel().getSelectedItem();


        MenuItem itemEdit = new MenuItem("Изменить");

        itemEdit.setOnAction(e -> {
            tree.edit(current);
            //todo изменить название
            System.out.println("e = " + e);
            System.out.println("current = " + current.getValue().getPath());
            System.out.println();

        });
        itemEdit.setDisable(current.getValue().getPath().equals(projectDirectory.getParent()));
        itemEdit.setDisable(current.getParent().getValue().getPath().equals(projectDirectory.getParent()));
        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(e -> {
            try {
                Files.delete(current.getValue().getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            current.getParent().getChildren().remove(current);
        });
        itemDelete.setDisable(current.getParent() == null);
        itemDelete.setDisable(current.getValue().getPath().equals(projectDirectory.getParent()));
        itemDelete.setDisable(current.getParent().getValue().getPath().equals(projectDirectory.getParent()));
        if (current.getValue() instanceof FolderInfo) {

            MenuItem itemAddFolder = new MenuItem("Добавить папку");
            itemAddFolder.setOnAction(e -> {
                TreeItem<AnyInfo> folderItem = new TreeItem<>(new FolderInfo("Новая папка", current.getValue().getPath()), new ImageView(folderImage));
                insert(current, folderItem);
                tree.getSelectionModel().select(folderItem);
                Path path = folderItem.getValue().getPath();
                try {
                    Files.createDirectory(Paths.get(path.toString(), "Новая папка"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            MenuItem itemAddFile = new MenuItem("Добавить файл");
            itemAddFile.setOnAction(e -> {
                try {
                    Files.createFile(Paths.get(current.getValue().getPath().toString(), "Новый файл"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                TreeItem<AnyInfo> fileItem = new TreeItem<>(new FileInfo("Новый файл", current.getValue().getPath()), new ImageView(fileImage));
                insert(current, fileItem);
                tree.getSelectionModel().select(fileItem);
            });
            menu = new ContextMenu(itemAddFolder, itemAddFile, itemEdit, itemDelete);
        } else {
            menu = new ContextMenu(itemEdit, itemDelete);
        }
        menu.show(tree.getScene().getWindow(), event.getScreenX(), event.getScreenY());

    }

    @FXML
    void buttonSave() {

        TreeItem<AnyInfo> root = treeView.getRoot();


        /*try {
            saveFolders(projectDirectory, root);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    void buttonRefresh() throws IOException {
        // tabs.clear();
        if (projectDirectory == null) return;
        openProject(projectDirectory);
    }
}
