package pt.inescporto.template.client.web;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.*;

public class TmplJButtonLinkMenu extends TmplJButton {
  protected String menuId = null;
  protected JComponent[] links = null;

  public TmplJButtonLinkMenu() {
    super();
    setActionCommand("LINK_MENU");
  }

  public TmplJButtonLinkMenu( String menuId, JComponent[] links ) {
    super();
    this.menuId = menuId;
    this.links = links;
    setActionCommand("LINK_MENU");
  }

  public void setMenuId( String menuId ) {
    this.menuId = menuId;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setLinks( JComponent[] links ) {
    this.links = links;
  }

  public JComponent[] getLinks() {
    return links;
  }

  public void tmplMode( TemplateEvent e ) {
    if( e.getMode() == TmplFormModes.MODE_SELECT )
      setEnabled(true);
    else
      setEnabled(false);
  }
}
