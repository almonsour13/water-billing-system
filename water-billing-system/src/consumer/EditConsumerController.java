/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class EditConsumerController implements Initializable {
//     private ConsumerController consumerController;
    public dbConfig dbConfig = new dbConfig();
    @FXML
    private AnchorPane addConsumerContainer;
    @FXML
    private TextField fName;
    @FXML
    private TextField mName;
    @FXML
    private TextField lName;
    @FXML
    private TextField suffix;
    @FXML
    private TextField contactNo;
    @FXML
    private TextField emailAd;
    @FXML
    private TextField meterNo;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> region;
    @FXML
    private ComboBox<String> province;
    @FXML
    private ComboBox<String> municipality;
    @FXML
    private ComboBox<String> baranggay;
    @FXML
    private ComboBox<String> purok;
    @FXML
    private TextField postalCode;
    private ConsumerController consumerController;
    public int id;
    
    public void setController(ConsumerController consumerController) {
        this.consumerController = consumerController;
    }
    public void setConsumer(int id){
        this.id = id;
        try {
            Consumer consumer = dbConfig.getSelectedConsumerByID(id);

            if (consumer != null) {
                fName.setText(consumer.firstNameProperty().get());
                mName.setText(consumer.middleNameProperty().get());
                lName.setText(consumer.lastNameProperty().get());
                suffix.setText(consumer.suffixProperty().get());
                emailAd.setText(consumer.emailAdProperty().get());
                contactNo.setText("0"+consumer.contactNoProperty().get());
                meterNo.setText(consumer.meterNumberProperty().get());
                country.setValue(consumer.countryProperty().get());
                region.setValue(consumer.regionProperty().get());
                province.setValue(consumer.provinceProperty().get());
                municipality.setValue(consumer.municipalityProperty().get());
                baranggay.setValue(consumer.baranggayProperty().get());
                purok.setValue(consumer.purokProperty().get());
                postalCode.setText(consumer.postalCodeProperty().get());
            }
        } catch (SQLException e) {
            // Handle the exception here
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        country.setItems(FXCollections.observableArrayList("Philippines"));
        region.setItems(FXCollections.observableArrayList("Region XI"));
        province.setItems(FXCollections.observableArrayList("Davao Oriental"));
        municipality.setItems(FXCollections.observableArrayList("Lupon")); 
        baranggay.setItems(FXCollections.observableArrayList("Macangao"));
        purok.setItems(FXCollections.observableArrayList("Manga","Mansanitas","BaranggaySite", "San Isidro"));
        //System.out.println(consumerController.getSelectedID());
        
    }    
    
    private void clickAnchorpane(MouseEvent event) {
        if (!addConsumerContainer.getBoundsInParent().contains(event.getX(), event.getY())) {
                // Close the window
            Stage stage = (Stage) addConsumerContainer.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void saveConsumer(ActionEvent event) throws SQLException {
        if(!fName.getText().isEmpty() && !lName.getText().isEmpty() &&
           !contactNo.getText().isEmpty() && !meterNo.getText().isEmpty() && 
           country.getValue()  != null && 
           region.getValue() != null && municipality.getValue()  != null && 
           baranggay.getValue()  != null && purok.getValue()  != null
        ){
            if(!isValidContactNo(contactNo.getText()) || !emailAd.getText().isEmpty() && !isValidEmailAddress(emailAd.getText())) {
                JOptionPane.showMessageDialog(null, "Please enter valid contact number and email address.");
                return;
            }else if(!isValidMeterNo(meterNo.getText())){
                JOptionPane.showMessageDialog(null, "Please enter valid meter number");
                return;
            }else{
                // Proceed with the database insertion
                dbConfig.updateConsumer(this.id,
                    fName.getText(),
                    mName.getText().isEmpty() ? "" : mName.getText(),
                    lName.getText(),
                    suffix.getText().isEmpty() ? "" : suffix.getText(),
                    contactNo.getText(),
                    emailAd.getText().isEmpty() ? "" : emailAd.getText(),
                    meterNo.getText(), 
                    country.getValue(),
                    region.getValue(),
                    province.getValue(),
                    municipality.getValue(),
                    baranggay.getValue(),
                    purok.getValue(),
                    postalCode.getText().isEmpty() ? "" : postalCode.getText()
                );
                consumerController.showConsumerTable();

                Stage stage = (Stage) addConsumerContainer.getScene().getWindow();
                stage.close();
                
                JOptionPane.showMessageDialog(null, "Consumer Information Updated Successfully");
            }
        }else{
            if (fName.getText().isEmpty() || lName.getText().isEmpty() || 
                contactNo.getText().isEmpty() || meterNo.getText().isEmpty() || 
                country.getValue() == null || region.getValue() == null || 
                province.getValue() == null || municipality.getValue() == null || 
                baranggay.getValue() == null || purok.getValue() == null) {
                
                JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                return;
            }
        }
    }
  
    private boolean isValidMeterNo(String meterNo){
        try {
            Integer.parseInt(meterNo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidContactNo(String contactNo) {
        return Pattern.compile("^(?:09)[0-9]{9}$").matcher(contactNo).matches();
    }
    private boolean isValidEmailAddress(String emailAddress) {
        return Pattern.compile("^(.+)@(.+)$").matcher(emailAddress).matches();
    }


    @FXML
    private void cancelAddConsumer(ActionEvent event) throws SQLException {
        Stage stage = (Stage) addConsumerContainer.getScene().getWindow();
        stage.close();
        consumerController.showConsumerTable();
    }

    private Object dbConfig() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
