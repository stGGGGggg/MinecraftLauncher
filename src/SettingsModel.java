public class SettingsModel {
    private String language = "Русский (Россия)";
    private String theme = "Тёмная (Стандартная)";
    private int width = 800;
    private int height = 600;
    private boolean closeOnLaunch = true;
    private boolean enableSnapshots = false;

    // Геттеры и Сеттеры
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public boolean isCloseOnLaunch() { return closeOnLaunch; }
    public void setCloseOnLaunch(boolean closeOnLaunch) { this.closeOnLaunch = closeOnLaunch; }

    public boolean isEnableSnapshots() { return enableSnapshots; }
    public void setEnableSnapshots(boolean enableSnapshots) { this.enableSnapshots = enableSnapshots; }


    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}