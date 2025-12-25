import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingsController {

    // Элементы управления, которые мы определили в Settings.fxml
    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private ComboBox<String> themeComboBox;

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    private CheckBox closeOnLaunchCheckBox;

    @FXML
    private CheckBox enableSnapshotsCheckBox;

    private DashboardController mainController;

    @FXML
    private Button saveButton;

    private SettingsModel settingsModel;

    private boolean enableSnapshots = false;

    public void setSettingsModel(SettingsModel model) {
        this.settingsModel = model;
        initializeUIFromModel(); // Загружаем данные в UI после получения модели
    }
    public void setMainController(DashboardController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void initialize() {
        // Инициализация ComboBox
        languageComboBox.getItems().addAll("Русский (Россия)", "English (US)", "Қазақша (Қазақстан)");
        languageComboBox.setValue("Русский (Россия)");
        saveButton.setOnAction(e -> handleSaveSettings());
        themeComboBox.getItems().addAll("Тёмная (Стандартная)", "Светлая", "Космическая");
        themeComboBox.setValue("Тёмная (Стандартная)");

    }
    private void initializeUIFromModel() {
        if (settingsModel != null) {
            languageComboBox.setValue(settingsModel.getLanguage());
            widthField.setText(String.valueOf(settingsModel.getWidth()));
            heightField.setText(String.valueOf(settingsModel.getHeight()));
            closeOnLaunchCheckBox.setSelected(settingsModel.isCloseOnLaunch());
            enableSnapshotsCheckBox.setSelected(settingsModel.isEnableSnapshots());
        }
    }


    // Метод для обработки сохранения настроек
    private void handleSaveSettings() {
        try {
            // Чтение данных
            String lang = languageComboBox.getValue();
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            boolean closeOnLaunch = closeOnLaunchCheckBox.isSelected();

            boolean enableSnapshotsValue = enableSnapshotsCheckBox.isSelected();

            settingsModel.setLanguage(lang);
            settingsModel.setWidth(width);
            settingsModel.setHeight(height);
            settingsModel.setCloseOnLaunch(closeOnLaunch);
            settingsModel.setEnableSnapshots(enableSnapshots);

            System.out.println("Settings saved to Model.");

            if (mainController != null) {
                mainController.applyTheme();
            }

            // Здесь должна быть логика сохранения в файл/базу данных
            System.out.println("Settings saved:");
            System.out.println("Language: " + lang);
            System.out.println("Resolution: " + width + "x" + height);
            System.out.println("Close on launch: " + closeOnLaunch);
            String selectedTheme = themeComboBox.getValue();

            // Передаем в модель (нужно будет добавить поле theme в SettingsModel.java)
            settingsModel.setTheme(selectedTheme);

            System.out.println("Выбранная тема: " + selectedTheme);

            // Показываем подтверждение
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сохранение");
            alert.setHeaderText(null);
            alert.setContentText("Настройки успешно сохранены!");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // Если в полях разрешения не числа
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверный формат данных");
            alert.setContentText("Поля 'Ширина' и 'Высота' должны содержать только целые числа.");
            alert.showAndWait();
        }
    }
}