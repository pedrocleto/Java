package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.TmplFormModes;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.event.DataSourceListener;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import pt.inescporto.template.client.event.TemplateComponentListener;
import javax.swing.JComponent;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.ContextPropertiesClient;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class HTMLViewerButton extends JButton implements ActionListener, DataSourceListener, TemplateComponentListener {
  TplString link = null;
  String field = null;
  String url = null;
  ContextPropertiesClient cpc = new ContextPropertiesClient();

  public HTMLViewerButton() {
    super(new ImageIcon(HTMLViewerButton.class.getResource("acroread.png")));
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new Font("Dialog", Font.PLAIN, 10));
    addActionListener(this);
  }

  public void setField(String field) {
    this.field = field;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getLink2HTML() {
    if (link != null && link.getValue() != null) {
      String url2html = url + "/" + link.getValue();
      return url2html;
    }
    return null;
  }

  /**
   * Implementation of DataSourceListener
   *
   */
  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
  }

  public void tmplSave(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equalsIgnoreCase(field))
      link = (TplString)e.getLink();
  }

  /**
   * Implementation of TemplateComponentListener
   *
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplPermissions(TemplateEvent e) {
    setVisible(true);
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        this.setEnabled(true);
        break;
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_UPDATE:
      case TmplFormModes.MODE_FIND:
        this.setEnabled(false);
        break;
    }
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public boolean tmplRequired(TemplateEvent e) {
    return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  /**
   *
   * @param e ActionEvent
   */

  public void actionPerformed(ActionEvent e) {
    String urlBase = cpc.getBaseURL() + "/html";
    if (link != null && link.getValue() != null) {
//      System.out.println("Opening file from url <" + urlBase + "/" + link.getValue() + ".html>");
      ShowHTML htmlViewer = new ShowHTML();
      try {
        htmlViewer.showPdf(urlBase + "/" + link.getValue() + ".html");
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void tmplDispose(TemplateEvent e) {
  }
}

class ShowHTML {
  public void showPdf(String url) throws Exception {
    String os = System.getProperty("os.name");
    if (os.equalsIgnoreCase("linux")) {
      String[] cmd = new String[] {"/bin/sh", "-c", "/usr/bin/firefox " + url};
      Process p = Runtime.getRuntime().exec(cmd);
    }
    else {
      Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + url);
    }
  }
}
