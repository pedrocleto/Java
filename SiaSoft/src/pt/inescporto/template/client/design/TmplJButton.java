package pt.inescporto.template.client.design;

import java.awt.Insets;
import javax.swing.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.util.TmplFormModes;

public class TmplJButton extends JButton implements DataSourceListener, TemplateComponentListener {
  protected int formPerms;
  protected int fieldPerms = 15;
  protected String fieldPermName = null;
  protected FW_ComponentListener fwParent = null;

  // constructers
  public TmplJButton() {
    super();
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  public TmplJButton(Action a) {
    super(a);
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  public TmplJButton(Icon icon) {
    super(icon);
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  public TmplJButton(String text) {
    super(text);
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  public TmplJButton(String text, Icon icon) {
    super(text, icon);
    this.setMargin(new Insets(2, 2, 2, 2));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  //
  // Methods from DatasourceListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
  }

  public void tmplSave(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
  }

  //
  // Methods from TemplateComponentListener interface
  //

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

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public boolean tmplRequired(TemplateEvent e) {
    return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
    // save parent listener!
    if (fwParent == null && e.getSource() instanceof FW_ComponentListener)
      fwParent = (FW_ComponentListener)e.getSource();

    // save permissions
    formPerms = e.getPermission();
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerms = e.getPermissionForField(fieldPermName);
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
