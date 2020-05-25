package utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class FileUtils {

  private FileUtils() {
  }

  public static String loadFile(String filepath) throws IOException {
    return loadFromFile(filepath);
  }

  public static <T> T loadFileToObj(String filepath, Class<T> clazz) throws IOException {
    String buffer = loadFromFile(filepath);
    return new XmlMapper().readValue(buffer, clazz);
  }

  private static String loadFromFile(String filepath) throws IOException {
    FileInputStream fis = new FileInputStream(filepath);
    byte[] buffer = new byte[255];
    int readBytes;
    StringBuilder sb = new StringBuilder();
    while((readBytes = fis.read(buffer, 0, 255)) != 0) {
      sb.append(new String(buffer, 0, readBytes));
    }
    fis.close();
    return sb.toString();
  }
}
