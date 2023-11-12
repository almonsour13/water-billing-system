/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ConsumerController implements Initializable {

    @FXML
    private TableColumn<?, ?> noColumn;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> firstNameColumn;
    @FXML
    private TableColumn<?, ?> middleNameColumn;
    @FXML
    private TableColumn<?, ?> lastNameColumn;
    @FXML
    private TableColumn<?, ?> contactNoColumn;
    @FXML
    private TableColumn<?, ?> purokColumn;
    @FXML
    private TableColumn<?, ?> postalCodeColumn;
    @FXML
    private TableColumn<?, ?> meterNumberColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    private HomeWindowController conCon;
    @FXML
    private Pane consumerContainer;
    @FXML
    private TableColumn<?, ?> installtionDate;
    @FXML
    private Button viewBills;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conCon = new HomeWindowController();
    }  
    
    public void setController(HomeWindowController conCon){
        this.conCon = conCon;
    }

    @FXML
    private void addConsumer(ActionEvent event) throws IOException {
    }

    @FXML
    private void viewBills(ActionEvent event) throws IOException {
        consumerContainer.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/consumer/consumerBills.fxml"));
        Pane root = loader.load();
        consumerContainer.getChildren().setAll(root);
    }
    
}
