/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Merry Ann
 */

public class ConsumerController implements Initializable {
    public int idHolder;
    public dbConfig dbConfig = new dbConfig();
    @FXML
    private TableView<Consumer> consumerTable = new TableView<>();

    @FXML
    private TableColumn<Person, Integer> idColumn;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> middleNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, Integer> meterNumberColumn;
   
    @FXML
    private TableColumn<Person, Integer> statusColumn;
    @FXML
    private TableColumn<Person, Integer> noColumn;
    
    @FXML
    private TextField searchConsumer;

    ObservableList<Consumer> data;
    @FXML
    private TableColumn<Person, Integer> contactNoColumn;
    @FXML
    private TableColumn<Person, String> purokColumn;
    @FXML
    private TableColumn<String, String> postalCodeColumn;
    @FXML
    private Button deactBtn;
    @FXML
    private Button editInfo;
    @FXML
    private ChoiceBox<String> filterStatus;
    @FXML
    private Button deleteBtn;
    @FXML
    private AnchorPane consumerTableContainer;
    private DarkModeConfig darkModeConfig;
    private boolean darkModeIsOn;
    public ConsumerController() throws SQLException{
        this.data = dbConfig.getConsumers();
    }
    public void setDarkModeConfig(DarkModeConfig darkModeConfig) throws SQLException {
        this.darkModeConfig = darkModeConfig;
        this.darkModeIsOn = darkModeConfig.isDarkMode();
        System.out.println(darkModeIsOn);
    }
    public void showConsumerTable() throws SQLException{
        this.data = dbConfig.getConsumers();
        this.consumerTable.getItems().clear();
        if(!filterStatus.getValue().equals("All")){
            for (Consumer consumer : data) {
                if(filterStatus.getValue().equals(consumer.statusProperty().get())){
                    consumerTable.getItems().add(consumer);
                }
            }
        }else{
            consumerTable.getItems().addAll(data);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        purokColumn.setCellValueFactory(new PropertyValueFactory<>("purok"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        meterNumberColumn.setCellValueFactory(new PropertyValueFactory<>("meterNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        consumerTable.getItems().addAll(data);
        filterStatus.setItems(FXCollections.observableArrayList("All", "Active","Inactive"));
        filterStatus.setValue("All");
        filterStatus.setOnAction(this::filterByStatus);
    }    

    @FXML
    private void addConsumer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("consumer/addConsumer.fxml"));
        Parent rootNode = loader.load();
        AddConsumerController addConsumerController = loader.getController();
        addConsumerController.setController(this);
        Stage modalWindow = new Stage();
        modalWindow.setResizable(false);
        modalWindow.setScene(new Scene(rootNode));
        modalWindow.initModality(Modality.APPLICATION_MODAL);
        modalWindow.centerOnScreen(); // Corrected method call to centerOnScreen
        modalWindow.setTitle("Add Consumer");
        modalWindow.setOpacity(1);
        modalWindow.show();
    }

    @FXML
    private void deactivate(ActionEvent event) throws SQLException {
        Consumer selectedConsumer = consumerTable.getSelectionModel().getSelectedItem();
        int id = selectedConsumer.idProperty().get();
        dbConfig dbConfig = new dbConfig();
        dbConfig.deactivateConsumer(id);
        showConsumerTable();
        
        editInfo.setDisable(true);
        deactBtn.setDisable(true);
        deleteBtn.setDisable(true);
        deactBtn.setText("Deactivate");
    }
    
    @FXML
    private void deleteData(ActionEvent event) throws SQLException {
        Consumer selectedConsumer = consumerTable.getSelectionModel().getSelectedItem();
        int id = selectedConsumer.idProperty().get();
        dbConfig dbConfig = new dbConfig();
        dbConfig.deleteConsumer(id);
        showConsumerTable();
        
        editInfo.setDisable(true);
        deactBtn.setDisable(true);
        deleteBtn.setDisable(true);
         JOptionPane.showMessageDialog(null, "Consumer Data Deleted Successfully");
    }
    @FXML
    private void editInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("consumer/editConsumer.fxml"));
        Parent rootNode = loader.load();
        EditConsumerController editConsumerController = loader.getController();
        editConsumerController.setController(this);        
        Consumer selectedConsumer = consumerTable.getSelectionModel().getSelectedItem();
        int id = selectedConsumer.idProperty().get();
        editConsumerController.setConsumer(id);   
        Stage modalWindow = new Stage();
        modalWindow.setResizable(false);
        modalWindow.setScene(new Scene(rootNode));
        modalWindow.initModality(Modality.APPLICATION_MODAL);
        modalWindow.centerOnScreen(); // Corrected method call to centerOnScreen
        modalWindow.setTitle("Edit Consumer Information");
        modalWindow.setOpacity(1);
        modalWindow.show();
        editInfo.setDisable(true);
        deactBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    @FXML
    private void searchConsumer(KeyEvent event) {
        consumerTable.getItems().clear();
        String searchValue = searchConsumer.getText().toLowerCase(); 
        if(!searchValue.isEmpty()){    
            for (Consumer consumer : data) {
                if (consumer.firstNameProperty().get().toLowerCase().contains(searchValue) ||
                    consumer.middleNameProperty().get().toLowerCase().contains(searchValue) ||    
                    consumer.lastNameProperty().get().toLowerCase().contains(searchValue) ||    
                    consumer.purokProperty().get().toLowerCase().contains(searchValue) ||
                    String.valueOf(consumer.meterNumberProperty()).contains(searchValue)  
                    ){
                    if(!filterStatus.getValue().equals("All")){
                        if(filterStatus.getValue().equals(consumer.statusProperty().get())){
                            consumerTable.getItems().add(consumer);
                        }
                    }else{
                        consumerTable.getItems().addAll(consumer);
                    }
                }
            }
        }else{
            consumerTable.getItems().addAll(data);
        }
    }

    @FXML
    private void selectRowInfo(MouseEvent event) throws Exception{
        Consumer selectedConsumer = consumerTable.getSelectionModel().getSelectedItem();
        if(selectedConsumer != null){
            String status = selectedConsumer.statusProperty().get();
            if(status.equals("Active") ){
                deactBtn.setText("Deactivate");
            }else{
                deactBtn.setText("Activate");
            }
        }
        editInfo.setDisable(false);
        deactBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void filterByStatus(ActionEvent event) {
        consumerTable.getItems().clear();
        if(!filterStatus.getValue().equals("All")){
            for (Consumer consumer : data) {
                if(filterStatus.getValue().equals(consumer.statusProperty().get())){
                    consumerTable.getItems().add(consumer);
                }
            }
        }else{
            consumerTable.getItems().addAll(data);
        }
    }
}
