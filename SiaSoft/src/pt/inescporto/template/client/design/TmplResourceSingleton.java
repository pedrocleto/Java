package pt.inescporto.template.client.design;

import java.util.ResourceBundle;

public abstract class TmplResourceSingleton {
  private static ResourceBundle resourceBundle = ResourceBundle.getBundle("pt.inescporto.template.client.design.TemplateResources");

  public static String getString( String key ) {
    return resourceBundle.getString( key );
  }
}
