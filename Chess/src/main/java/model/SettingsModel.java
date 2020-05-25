package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsModel {

  private static final String SETTINGS_FILE_PATH = "res/config/settings.xml";

  private SettingsModel() {
    try (FileInputStream fis = new FileInputStream(SETTINGS_FILE_PATH)) {
      System.out.println(fis);
    } catch (FileNotFoundException e) {
      //create settings.xml file
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static SettingsModel loadFromFile() {
    return new SettingsModel();
  }

  public void saveToFile() {

  }
}
