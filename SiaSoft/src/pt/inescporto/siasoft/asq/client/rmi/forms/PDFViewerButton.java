package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.elements.TplString;
import javax.swing.JComponent;
import pt.inescporto.template.client.util.TmplFormModes;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import java.awt.Insets;
import java.awt.event.ActionListener;
import pt.inescporto.template.client.event.TemplateComponentListener;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
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
public class PDFViewerButton extends JButton implements ActionListener, DataSourceListener, TemplateComponentListener {
  TplString link = null;
  String field = null;
  String url = null;
  ContextPropertiesClient cpc = new ContextPropertiesClient();

  public PDFViewerButton() {
    super(new ImageIcon(PDFViewerButton.class.getResource("acroread.png")));
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

  public String getLink2PDF() {
    if (link != null && link.getValue() != null) {
      String url2pdf = url + "/" + link.getValue();
      return url2pdf;
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
    String urlBase = cpc.getBaseURL() + "/pdf";
    if (link != null && link.getValue() != null) {
      ShowPDF pdfViewer = new ShowPDF();
      try {
	pdfViewer.showPdf(urlBase + "/" + link.getValue() + ".pdf");
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
