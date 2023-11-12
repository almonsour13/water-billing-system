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
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ConsumerBillsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ConsumerBillsController con;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
    }
    public void setController(ConsumerBillsController con){
        this.con = con; 
    }
    @FXML
    private void backToParent(ActionEvent event) throws IOException {
        HomeWindowController conCon = new HomeWindowController();
        conCon.backToParent("consumer");
//       System.out.println("Asd");
    }
    
}
