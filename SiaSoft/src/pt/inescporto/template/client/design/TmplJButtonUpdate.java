package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonUpdate extends TmplJButton {
  public TmplJButtonUpdate() {
    super();

    setText("update");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/update.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.update"));
    setActionCommand("UPDATE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonUpdate(Action a) {
    super(a);
    setActionCommand("UPDATE");
  }

  public TmplJButtonUpdate(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.update"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.update"));
    setActionCommand("UPDATE");
  }

  public TmplJButtonUpdate(String text) {
    super(text);
    setActionCommand("UPDATE");
  }

  public TmplJButtonUpdate(String text, Icon icon) {
    super(text, icon);
    setActionCommand("UPDATE");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(this.formPerms & TmplPerms.PERM_CHANGE) != 0)
      setEnabled(true);
    else
      setEnabled(false);
  }

  public void tmplPermissions(TemplateEvent e) {
    formPerms = e.getPermission();
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(this.formPerms & TmplPerms.PERM_CHANGE) != 0)
      setEnabled(true);
    else
      setEnabled(false);

    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerms = e.getPermissionForField(fieldPermName);
  }
}
