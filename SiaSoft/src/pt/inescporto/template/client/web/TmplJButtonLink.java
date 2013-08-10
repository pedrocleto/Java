package pt.inescporto.template.client.web;

import javax.swing.*;
import java.awt.event.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.*;

public class TmplJButtonLink extends TmplJButton {
  protected String url = null;
  protected JComponent[] links = null;

  public TmplJButtonLink() {
    super();
    setActionCommand("LINK");
  }

  public TmplJButtonLink( String url, JComponent[] links ) {
    super();
    this.url = url;
    this.links = links;
    setActionCommand("LINK");
  }

  public void setUrl( String url ) {
    this.url = url;
  }

  public String getUrl() {
    return url;
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