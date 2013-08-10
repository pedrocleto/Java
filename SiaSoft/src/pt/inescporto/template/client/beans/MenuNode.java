package pt.inescporto.template.client.beans;

import javax.swing.ImageIcon;
import java.util.ResourceBundle;

public class MenuNode {
  private static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.template.client.web.MenuResource");
  String menuId = null;
  String menuDescription = null;
  String menuResourceId = null;
  String menuTooltip = null;
  ImageIcon scIcon = null;

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuDescription(String menuDescription) {
    this.menuDescription = menuDescription;
  }

  public String getMenuDescription() {
    return menuDescription;
  }

  public void setMenuResourceId(String menuResourceId) {
    this.menuResourceId = menuResourceId;
  }

  public String getMenuResourceId() {
    return menuResourceId;
  }

  public void setMenuTooltip(String menuTooltip) {
    this.menuTooltip = menuTooltip;
  }

  public String getMenuTooltip() {
    return menuTooltip;
  }

  public String toString() {
    String resId = null;
    try {
      resId = res.getString(menuResourceId);
    }
    catch (Exception ex) {
    }
    return resId == null ? menuDescription : resId;
  }

  public void setScIcon(ImageIcon scIcon) {
    this.scIcon = scIcon;
  }

  public ImageIcon getScIcon() {
    return this.scIcon;
  }
}
