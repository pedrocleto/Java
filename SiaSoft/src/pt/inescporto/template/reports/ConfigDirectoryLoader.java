package pt.inescporto.template.reports;

import java.io.File;

public abstract class ConfigDirectoryLoader {

  private static String configDir;

  static {
    File file = new File("config");
    if ( !file.exists() ) {
      String configDir1 = System.getenv("TMPL_CONFIG");
      if ( configDir1 == null ) {
        configDir = "";
      } else {
        configDir = configDir1;
      }
    } else {
      configDir = "config";
    }
  }

  public static String getConfigDir() {
    return configDir;
  }

}
