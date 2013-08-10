package pt.inescporto.template.client.design;

import java.awt.event.*;
import javax.swing.*;
import pt.inescporto.template.web.util.TmplQuery;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;
import java.awt.Component;

public class TmplLookupButton extends TmplJButton {
  private String url;
  private String title;
  private String[] headers;
  private TmplDlgLookup dlg = null;
  private JTextField[] fill = null;
  private TmplQuery query = null;
  private String staticLinkCondition = null;
  private JComponent[] detailLinks = null;

  public TmplLookupButton() {
    super();

    setIcon(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));
    setHorizontalAlignment(SwingConstants.LEFT);

    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	doActionPerformed(e);
      }
    });
  }

  public TmplLookupButton(String title, String url, String[] headers, JTextField[] fill) {
    this.url = url;
    this.headers = headers;
    this.title = title;
    this.fill = fill;
    this.query = null;

    setIcon(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));
    setHorizontalAlignment(SwingConstants.LEFT);

    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	doActionPerformed(e);
      }
    });
  }

  public TmplLookupButton(String title, TmplQuery query, String[] headers, JTextField[] fill) {
    this.url = null;
    this.headers = headers;
    this.title = title;
    this.fill = fill;
    this.query = query;

    setIcon(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));
    setHorizontalAlignment(SwingConstants.LEFT);

    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	doActionPerformed(e);
      }
    });
  }

  public void doActionPerformed(ActionEvent e) {
    if (dlg != null)
      dlg.dispose();

    Component parent = getParent();
    while (parent != null && !(parent instanceof JFrame))
      parent = parent.getParent();

    if (url != null)
      dlg = new TmplDlgLookup((parent != null) ? (JFrame)parent : null, title, url, headers, fill, staticLinkCondition, detailLinks);
    else
      dlg = new TmplDlgLookup((parent != null) ? (JFrame)parent : null, title, query, headers, fill);
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setHeaders(String[] headers) {
    this.headers = headers;
  }

  public String[] getHeaders() {
    return headers;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setFillList(JTextField[] fill) {
    this.fill = fill;
  }

  public JTextField[] getFillList() {
    return fill;
  }

  public void setDefaultFill(JTextField fill) {
    this.fill = new JTextField[] {fill};
  }

  public JTextField getDefaultFill() {
    return fill[0];
  }

  public void setQuery(TmplQuery query) {
    this.query = query;
  }

  /**
   *
   * @param linkCondition slq where statement for static link conditions
   */
  public void setLinkCondition(String linkCondition) {
    staticLinkCondition = linkCondition;
  }

  public String getLinkCondition() {
    return staticLinkCondition;
  }

  /**
   *
   * @param detailLinks arrays of JComponents used to construct the dinamic
   * link condition.
   */
  public void setComponentLinkList(JComponent detailLinks[]) {
    this.detailLinks = detailLinks;
  }

  public JComponent[] getComponentLinkList() {
    return this.detailLinks;
  }

  //
  // Methods from TemplateListener interface
  //

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
	setEnabled(false);
	break;
      case TmplFormModes.MODE_INSERT:
	if (!(fill == null) && (fill[0]) instanceof pt.inescporto.template.client.design.TmplJTextField) {
	  if (((TmplJTextField)(fill[0])).getLink() != null && ((TmplJTextField)(fill[0])).getLink().isLinkKey())
	    setEnabled(false);
	  else
	    setEnabled(true);
	}
	else
	  setEnabled(true);
	break;
      case TmplFormModes.MODE_UPDATE:
	if (!(fill == null) && (fill[0]) instanceof pt.inescporto.template.client.design.TmplJTextField) {
	  if (((TmplJTextField)(fill[0])).getLink() != null && ((TmplJTextField)(fill[0])).getLink().isPrimaryKey())
	    setEnabled(false);
	  else
	    setEnabled(true);
	}
	else
	  setEnabled(true);
	break;
      case TmplFormModes.MODE_FIND:
	if (!(fill == null) && (fill[0]) instanceof pt.inescporto.template.client.design.TmplJTextField) {
	  if (((TmplJTextField)(fill[0])).getLink() != null && ((TmplJTextField)(fill[0])).getLink().isLinkKey())
	    setEnabled(false);
	  else
	    setEnabled(true);
	}
	else
	  setEnabled(true);
	break;
    }
  }
}
