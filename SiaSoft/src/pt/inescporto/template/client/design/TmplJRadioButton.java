package pt.inescporto.template.client.design;

import java.awt.Font;
import javax.swing.JRadioButton;
import pt.inescporto.template.client.event.TemplateComponentListener;
import javax.swing.JComponent;
import pt.inescporto.template.client.event.TemplateEvent;
import java.awt.Component;
import pt.inescporto.template.client.util.TmplPerms;

public class TmplJRadioButton extends JRadioButton implements TemplateComponentListener {
  String accessPermitionID = "NOTDEF";
  int permValue = -1;
  boolean hasVisualPerms = true;
  String value;

  public TmplJRadioButton() {
    super();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setOpaque(false);
  }

  public TmplJRadioButton(String value) {
    this();
    this.value = value;
  }

  public TmplJRadioButton(String text, boolean selected) {
    super(text, selected);
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setOpaque(false);
  }

  public void setAccessPermissionID (String accessPermitionID) {
    this.accessPermitionID = accessPermitionID;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  /** ************************************************************************ *
   ** ***     Implementation of TemplateComponentListener methods          *** *
   ** ************************************************************************ */
  public void tmplPermissions(TemplateEvent e) {
    // i'm working as slave! See the <code>start</code> method for details!
    permValue = e.getPermissionForField(accessPermitionID);
    if (permValue != -1) {
      Component[] all = this.getComponents();

      if ((permValue & TmplPerms.PERM_SHOW) == 0) {
        setVisible(false);
      }
      else {
        hasVisualPerms = (permValue & TmplPerms.PERM_SHOW) != 0;
      }
    }
  }

  public void tmplMode(TemplateEvent e) {
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplEnable(TemplateEvent e) {
  }

  public boolean tmplRequired(TemplateEvent e) {
    return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
