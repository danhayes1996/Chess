package model;

import java.io.IOException;
import utils.FileUtils;

public class SettingsModel {

  private static final String SETTINGS_FILE_PATH = "dummyExternalDir/settings.xml";

  private SettingsModel() {
  }

  public static SettingsModel loadFromFile() {
    try {
     return FileUtils.loadFileToObj(SETTINGS_FILE_PATH, SettingsModel.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void saveToFile() {
    try {
      FileUtils.saveObjToFile(SETTINGS_FILE_PATH, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
