import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.security.MessageDigest;

public class DashboardController {

    @FXML private StackPane contentArea;
    @FXML private Button homeButton;
    @FXML private Button settingsButton;
    @FXML private TextField usernameField; // Для строки 145
    @FXML private PasswordField passwordField; // Для строки 146
    @FXML private Label statusLabel;
    @FXML private VBox loginCard;
    @FXML private VBox profileCard;
    @FXML private Label loggedUserLabel;
    @FXML private ComboBox<String> versionComboBox;

    private SettingsModel settings = new SettingsModel();
    private boolean isLoggedIn = false;

    public void startDashboard() {
        loadPage("Home.fxml");
        if (homeButton != null) setActiveButton(homeButton);
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = hashPassword(passwordField.getText().trim());

        // Используем DatabaseHelper для проверки
        if (DatabaseHelper.loginUser(username, password)) {
            isLoggedIn = true;
            statusLabel.setText("Вход выполнен успешно!");
            updateHomeViewToProfile(username);
        } else {
            statusLabel.setText("Ошибка: Неверные данные!");
        }
    }

    private void updateHomeViewToProfile(String username) {
        if (loginCard != null) {
            loginCard.setVisible(false);
            loginCard.setManaged(false);
        }
        if (profileCard != null) {
            profileCard.setVisible(true);
            profileCard.setManaged(true);
            loggedUserLabel.setText(username);
        }
        if (versionComboBox != null) {
            versionComboBox.getItems().setAll("1.21", "1.20.6", "1.19.4", "1.12.2");
            versionComboBox.setValue("1.21");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { return password; }
    }

    private void loadPage(String fxmlFileName) {
        try {
            contentArea.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

            // Ручное назначение контроллера для связи Home.fxml с этим классом
            if (fxmlFileName.equals("Home.fxml")) {
                loader.setController(this);
            }

            Pane newPane = loader.load();
            if (fxmlFileName.equals("Settings.fxml")) {
                SettingsController sc = loader.getController();
                sc.setSettingsModel(this.settings);
            }
            contentArea.getChildren().add(newPane);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML private void onHomeClicked(ActionEvent event) { loadPage("Home.fxml"); setActiveButton((Button) event.getSource()); }
    @FXML private void onSettingsClicked(ActionEvent event) { loadPage("Settings.fxml"); setActiveButton((Button) event.getSource()); }

    private void setActiveButton(Button active) {
        if (homeButton != null) homeButton.getStyleClass().remove("active-top-nav-button");
        if (settingsButton != null) settingsButton.getStyleClass().remove("active-top-nav-button");
        active.getStyleClass().add("active-top-nav-button");
    }

    @FXML public void handleLaunch(ActionEvent event) { System.out.println("Minecraft launch initiated..."); }

    @FXML private void onVkClicked(ActionEvent event) { openLink("https://vk.com/club234535356"); }
    @FXML private void onDiscordClicked(ActionEvent event) { openLink("https://discord.gg/fKxMdHps"); }

    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}