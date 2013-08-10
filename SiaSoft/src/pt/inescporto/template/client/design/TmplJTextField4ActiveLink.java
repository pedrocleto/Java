package pt.inescporto.template.client.design;

import pt.inescporto.template.elements.TplString;
import javax.swing.JComponent;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.TmplPerms;
import javax.swing.JTextField;
import pt.inescporto.template.client.event.DataSourceListener;
import javax.swing.text.AbstractDocument;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.event.TemplateEvent;
import javax.swing.JLabel;
import java.awt.Font;
import pt.inescporto.template.client.event.TemplateComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.io.*;

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
public class TmplJTextField4ActiveLink extends JTextField implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = new TplObject();
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  private int currentMode = -1;
  boolean underlined = false;

  public TmplJTextField4ActiveLink() {
    this((JLabel)null, null);
  }

  public TmplJTextField4ActiveLink(JLabel jlblHolder, String field) {
    super();
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));

    addChangeFont();
  }

  public TmplJTextField4ActiveLink(String label, String field) {
    super();
    this.label = label;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));

    addChangeFont();
  }

  private void addChangeFont() {
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() == 2) {
	  String os = System.getProperty("os.name");
	  if (os.equalsIgnoreCase("linux")) {
	    String[] cmd = new String[] {"/bin/sh", "-c", "/usr/bin/firefox " + ((JTextField)e.getComponent()).getText()};
	    Runtime run = Runtime.getRuntime();
	    try {
	      Process pp = run.exec(cmd);
	      BufferedReader in = new BufferedReader(new InputStreamReader(pp.getErrorStream()));
	      String line;
/*	      while ((line = in.readLine()) != null) {
		System.out.println(line);
	      }*/
	      int exitVal = pp.waitFor();
//	      System.out.println("Process exitValue: " + exitVal);
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      System.out.println(ex.getMessage());
	    }
	  }
          else {
	    String cmd = new String("rundll32 url.dll,FileProtocolHandler " + ((JTextField)e.getComponent()).getText());
	    Runtime run = Runtime.getRuntime();
	    try {
	      Process pp = run.exec(cmd);
	      BufferedReader in = new BufferedReader(new InputStreamReader(pp.getErrorStream()));
	      String line;
/*	      while ((line = in.readLine()) != null) {
		System.out.println(line);
	      }*/
	      int exitVal = pp.waitFor();
//	      System.out.println("Process exitValue: " + exitVal);
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      System.out.println(ex.getMessage());
	    }
	  }
	}
      }
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {
        underlined = true;
        repaint();
      }
      public void mouseExited(MouseEvent e) {
        underlined = false;
        repaint();
      }
    });
  }

  public void paint(Graphics g) {
    super.paint(g);
    if (underlined && currentMode == TmplFormModes.MODE_SELECT) {
      Font f = getFont();
      FontMetrics fm = getFontMetrics(f);
      int x1 = 0;
      int y1 = fm.getHeight();
      int x2 = fm.stringWidth(getText());
      if (getText().length() > 0)
	g.drawLine(x1, y1, x2, y1);
    }
  }

  public void setLink(TplObject link) {
    this.link = link;

    if (this.jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return this.link;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public void setHolder(JLabel holder) {
    jlblHolder = holder;
  }

  public JLabel getHolder() {
    return jlblHolder;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    if (this.jlblHolder != null)
      return this.jlblHolder.getText();
    else
      return label;
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplMode(TemplateEvent e) {
    currentMode = e.getMode();
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        setEnabled(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
          if (link.isLinkKey() || link.isGenKey()) {
            setEnabled(false);
            // clear for generated keys
            if (link.isGenKey()) {
              setText("");
              fireActionPerformed();
              link.resetValue();
            }
          }
          else {
            setEnabled(true);
            setText("");
            fireActionPerformed();
            link.resetValue();
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
          setEnabled(true);
        break;
      case TmplFormModes.MODE_FIND:
        if (link == null || !(link.isLinkKey())) {
          setEnabled(true);
          setText("");
          if (link != null)
            link.resetValue();
        }
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null) {
        setText(link.getValueAsObject() == null ? "" : link.getValueAsObject().toString());
      }
    }
    catch (Exception ex) {
      setText("");
    }
    fireActionPerformed();
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (getText().equals(""))
        link.resetValue();
      else {
        String value = getText();
        if (value == null || value == "" || value.compareTo("") == 0)
          link.resetValue();
        else {
          link.setValueAsObject(value);
        }
      }
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    String value = getText();
    if (link != null && link.isRequired() && !link.isGenKey() && value.equals("")) {
      requestFocus();
      e.setCompFailed(this);
      return false;
    }
    else
      return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerm = e.getPermissionForField(fieldPermName);
  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equals(field)) {
      link = e.getLink();
      if (link instanceof pt.inescporto.template.elements.TplString && ((TplString)link).getLength() != -1) {
        ((AbstractDocument) getDocument()).setDocumentFilter(new FixedSizeFilter(((TplString)link).getLength()));
      }
    }
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
