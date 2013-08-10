package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.TmplPerms;

public class TmplJButtonDetailDelete extends TmplJButton {
  public TmplJButtonDetailDelete() {
    super();

    setText("delete");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/delete.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.delete"));
    setActionCommand("DELETE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDetailDelete( Action a ) {
    super( a );
    setActionCommand("DELETE");
  }

  public TmplJButtonDetailDelete( Icon icon ) {
    super( icon );

    setText(TmplResourceSingleton.getString("button.detail.label.delete"));
    setToolTipText(TmplResourceSingleton.getString("button.detail.tooltip.delete"));
    setActionCommand("DELETE");
  }

  public TmplJButtonDetailDelete( String text ) {
    super( text );
    setActionCommand("DELETE");
  }

  public TmplJButtonDetailDelete( String text, Icon icon ) {
    super( text, icon );
    setActionCommand("DELETE");
  }

  public void tmplMode(TemplateEvent e) {
    // Disabling due to permissions
    if ((int)(formPerms & TmplPerms.PERM_DELETE) == 0) {
      setEnabled(false);
      return;
    }

    if (e.getMode() == TmplFormModes.MODE_SELECT) {
      setEnabled(true);
      return;
    }

    if (e.getMode() == TmplFormModes.MODE_LOCK) {
      if (fwParent != null && e.getSource() == fwParent && e.getModeForLocking() != TmplFormModes.MODE_DELETE && e.getModeForLocking() != TmplFormModes.MODE_INSERT && e.getModeForLocking() != TmplFormModes.MODE_UPDATE && e.getModeForLocking() != TmplFormModes.MODE_DELETE)
        setEnabled(true);
      else
        setEnabled(false);
      return;
    }

    setEnabled(false);
  }

  public void tmplPermissions(TemplateEvent e) {
    super.tmplPermissions(e);

    // form permissions
    formPerms = e.getPermission();
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(formPerms & TmplPerms.PERM_DELETE) != 0)
      setEnabled(true);
    else
      setEnabled(false);

    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerms = e.getPermissionForField(fieldPermName);
  }
}
