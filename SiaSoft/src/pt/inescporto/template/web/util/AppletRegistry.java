package pt.inescporto.template.web.util;

import java.util.Hashtable;
import javax.swing.JApplet;

public final class AppletRegistry {
  private static AppletRegistry instance = null;
  private Hashtable appletTable = new Hashtable();

  private AppletRegistry() {
  }

  public static AppletRegistry getInstance() {
    return (instance == null ? instance = new AppletRegistry() : instance);
  }

  public JApplet getApplet(String name) {
    return (JApplet) appletTable.get(name);
  }

  public void registerApplet(String name, JApplet applet) {
    appletTable.put(name, applet);
  }
}
