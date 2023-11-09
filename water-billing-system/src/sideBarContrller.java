import javafx.scene.Node;  // Import JavaFX Node
import javafx.scene.control.Button;  // Import JavaFX Button
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Administrator
 */
public class sideBarContrller implements Initializable {
    @FXML
    private BorderPane panel;
    @FXML
    private AnchorPane pane;
    @FXML
    private VBox menuContainer;
    @FXML
    private Button darkmodeBtn;
    
    public boolean darkMode;
    
    private DarkModeConfig darkModeConfig;
    @FXML
    private Button consumerButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button meterReadingButton;
    @FXML
    private Button billsButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button accountLogsButton;
    @FXML
    private Button backUpButton;
    public void setDarkModeConfig(DarkModeConfig darkModeConfig) throws SQLException {
        this.darkModeConfig = darkModeConfig;
        ConsumerController consumerController = new ConsumerController();
        consumerController.setDarkModeConfig(darkModeConfig);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(darkModeConfig != null){
            updateDarkMode(darkModeConfig.isDarkMode());
        }
        try {
            loadPage("dashboard");
            if(panel != null){
                panel.getStylesheets().clear();
                panel.getStylesheets().add(theme());
            }
            updateButtonStyles(dashboardButton);
        } catch (IOException ex) {
            Logger.getLogger(sideBarContrller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void dashBoard(ActionEvent event) throws IOException {
        loadPage("dashboard");
        updateButtonStyles(dashboardButton);
    }
    @FXML
    private void consumer(ActionEvent event) throws IOException {
        loadPage("consumer");
        updateButtonStyles(consumerButton);
    }
    @FXML
    private void accounts(ActionEvent event) throws IOException {
        loadPage("accounts");
        updateButtonStyles(accountButton);
    }
    @FXML
    private void meterReading(ActionEvent event) throws IOException {
        loadPage("meterReading");
        updateButtonStyles(meterReadingButton);
    }
    @FXML
    private void bills(ActionEvent event) throws IOException {
        loadPage("bills");
        updateButtonStyles(billsButton);
    }
    @FXML
    private void payment(ActionEvent event) throws IOException {
        loadPage("payment");
        updateButtonStyles(paymentButton);
    }
    @FXML
    private void accountLogs(ActionEvent event) throws IOException {
        loadPage("accountLogs");
        updateButtonStyles(accountLogsButton);
    }
    @FXML
    private void backUp(ActionEvent event) throws IOException {
        loadPage("backUp");
        updateButtonStyles(backUpButton);
    }

    public void loadPage(String page) throws IOException {
        FXMLLoader loader = null;
        pane.getChildren().clear();
        
        if(page.equals("dashboard")){
             loader = new FXMLLoader(getClass().getResource("/dashboard/"+page + ".fxml"));
        }else if(page.equals("consumer")){
            loader = new FXMLLoader(getClass().getResource("/consumer/"+page + ".fxml"));
        }else if(page.equals("meterReading")){
             loader = new FXMLLoader(getClass().getResource("/meterReading/"+page + ".fxml"));
        }
        Pane root = loader.load();
        pane.getChildren().setAll(root);
    }
    private void updateButtonStyles(Button clickedButton) {
        for (Node node : menuContainer.getChildren()) {
            if (node instanceof Button button) {
                if (button == clickedButton) {
                    button.getStyleClass().setAll("menu-btn-clicked");
                } else {
                    button.getStyleClass().setAll("menu-btn");
                }
            }
        }
    }

     @FXML
    private void setDarkMode(ActionEvent event) {
        if (darkModeConfig != null) {
            darkModeConfig.setDarkMode(!darkModeConfig.isDarkMode());
            updateDarkMode(darkModeConfig.isDarkMode());
        }
    }

    private void updateDarkMode(boolean isDarkMode) {
        this.darkMode = isDarkMode;
        panel.getStylesheets().clear();
        panel.getStylesheets().add(theme());
        darkmodeBtn.setText(isDarkMode ? "Light Mode" : "Dark Mode");
    }

    public String theme() {
        if (!this.darkMode) {
            return getClass().getResource("css/style.css").toExternalForm();
        } else {
            return getClass().getResource("css/darkmode.css").toExternalForm();
        }
    }
    
    public boolean isDarkMode() {
        return this.darkMode;
    }
}
