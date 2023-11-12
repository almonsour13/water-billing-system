/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class HomeWindowController implements Initializable {

    @FXML
    private Pane parentContainer;
    @FXML
    private VBox sidebar;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button consumerBtn;
    @FXML
    private Button meterReadingBtn;
    @FXML
    private Button billingBtn;
    @FXML
    private Button paymentBtn;
    @FXML
    private Button accountBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadPage("dashboard");
            updateButtonStyles(dashboardBtn);
        } catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public HomeWindowController(){
        parentContainer = new Pane();
    }
    public void backToParent(String page) throws IOException{
        loadPage(page);
       // System.out.println(page);
    }

    @FXML
    private void dashboard(ActionEvent event) throws IOException {
        loadPage("dashboard");
        updateButtonStyles(dashboardBtn);
    }

    @FXML
    private void consumer(ActionEvent event) throws IOException {
        loadPage("consumer");
        updateButtonStyles(consumerBtn);
    }

    @FXML
    private void meterReading(ActionEvent event) throws IOException {
        loadPage("meterReading");
        updateButtonStyles(meterReadingBtn);
    }

    @FXML
    private void billing(ActionEvent event) throws IOException {
        loadPage("billing");
        updateButtonStyles(billingBtn);
    }

    @FXML
    private void payment(ActionEvent event) {
    }

    @FXML
    private void account(ActionEvent event) {
    } 
     public void loadPage(String page) throws IOException {
        FXMLLoader loader = null;
        parentContainer.getChildren().clear();
        
        if(page.equals("dashboard")){
             loader = new FXMLLoader(getClass().getResource("/view/"+page + ".fxml"));
        }else if(page.equals("consumer")){
            loader = new FXMLLoader(getClass().getResource("/view/consumer/"+page + ".fxml"));
            ConsumerController conCon = new ConsumerController();
            conCon.setController(this);
        }else if(page.equals("meterReading")){
             loader = new FXMLLoader(getClass().getResource("/view/"+page + ".fxml"));
        }else if(page.equals("billing")){
             loader = new FXMLLoader(getClass().getResource("/view/"+page + ".fxml"));
        }
        Pane root = loader.load();
        parentContainer.getChildren().setAll(root);
    }
     private void updateButtonStyles(Button clickedButton) {
        for (Node node : sidebar.getChildren()) {
            if (node instanceof Button button) {
                if (button == clickedButton) {
                    button.getStyleClass().setAll("menu-btn-clicked");
                } else {
                    button.getStyleClass().setAll("menu-btn");
                }
            }
        }
    }
     public void clear(){
         parentContainer.getChildren().clear();
     }
     @FXML
    private void backToParent(ActionEvent event) throws IOException {
        HomeWindowController conCon = new HomeWindowController();
        conCon.backToParent("consumer");
//      
}
    
}
