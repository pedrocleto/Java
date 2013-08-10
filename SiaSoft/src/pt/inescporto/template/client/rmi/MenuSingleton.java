package pt.inescporto.template.client.rmi;

import pt.inescporto.siasoft.comun.client.UserData;
import java.awt.Container;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public final class MenuSingleton {
  private MenuSingleton() {};

  private static MenuInterface menu = null;

  public static void setMenu( MenuInterface menu ) {
    MenuSingleton.menu = menu;
  }

  public static String getEnvironmentType() {
    return menu == null ? null : menu.getEnvironmentType();
  }

  public static String getRole() {
    return menu == null ? null : menu.getRole();
  }

  public static String getName() {
    return menu == null ? null : menu.getName();
  }

  public static String getEnterprise() {
    return menu == null ? null : menu.getEnterprise();
  }

  public static String getSelectedEnterprise() {
    return menu == null ? null : menu.getSelectedEnterprise();
  }

  public static UserData getUserData() {
    return menu == null ? null : menu.getUserData();
  }

  public static boolean isSupplier() {
    return menu == null ? false : menu.isSupplier();
  }

  public static void addContainer(Container container) {
    if (menu != null)
      menu.addContainerFW(container);
  }

  public static boolean loadForm(String formID, Object keyValue) {
    return (menu != null ? menu.loadForm(formID, keyValue) : false);
  }

  public static void addItemToMenu(String regID, Object regRef) {
    if (menu != null)
      menu.addItemToMenu(regID, regRef);
  }
}
