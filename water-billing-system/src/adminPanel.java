    import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class adminPanel extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("panel.fxml"));
        Parent root = loader.load();
        DarkModeConfig darkModeConfig = new DarkModeConfig();
        Scene scene = new Scene(root);
        sideBarContrller sideBarContrller = loader.getController();
        sideBarContrller.setDarkModeConfig(darkModeConfig);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
            
    }

    public static void main(String[] args) {
        launch(args);
    }
}
