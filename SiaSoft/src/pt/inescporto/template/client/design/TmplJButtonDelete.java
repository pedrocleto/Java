package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonDelete extends TmplJButton {
  public TmplJButtonDelete() {
    super();

    setText("delete");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/delete.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.delete"));
    setActionCommand("DELETE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDelete(Action a) {
    super(a);
    this.setActionCommand("DELETE");
  }

  public TmplJButtonDelete(Icon icon) {
    super(icon);

    this.setText(TmplResourceSingleton.getString("button.label.delete"));
    this.setToolTipText(TmplResourceSingleton.getString("button.tooltip.delete"));
    this.setActionCommand("DELETE");
  }

  public TmplJButtonDelete(String text) {
    super(text);
    this.setActionCommand("DELETE");
  }

  public TmplJButtonDelete(String text, Icon icon) {
    super(text, icon);
    this.setActionCommand("DELETE");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(this.formPerms & TmplPerms.PERM_DELETE) != 0)
      this.setEnabled(true);
    else
      this.setEnabled(false);
  }

  public void tmplPermissions(TemplateEvent e) {
    formPerms = e.getPermission();
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(this.formPerms & TmplPerms.PERM_DELETE) != 0)
      setEnabled(true);
    else
      setEnabled(false);

    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerms = e.getPermissionForField(fieldPermName);
  }
}
