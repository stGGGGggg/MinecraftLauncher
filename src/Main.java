import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Change from Login.fxml to Dashboard.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        // Get the controller to initialize the first page
        DashboardController controller = fxmlLoader.getController();
        controller.startDashboard();

        stage.setScene(scene);
        stage.setTitle("Minecraft Launcher - TNT:LAUNCHER");
        stage.show();
    }

    public static void main(String[] args) {
        DatabaseHelper.connect(); // Initialize DB on startup
        launch();
    }
}