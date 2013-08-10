package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonDetailUpdate extends TmplJButton {
  public TmplJButtonDetailUpdate() {
    super();

    setText("update");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/update.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.update"));
    setActionCommand("UPDATE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDetailUpdate( Action a ) {
    super( a );
    this.setActionCommand("UPDATE");
  }

  public TmplJButtonDetailUpdate( Icon icon ) {
    super( icon );

    this.setText(TmplResourceSingleton.getString("button.detail.label.update"));
    this.setToolTipText(TmplResourceSingleton.getString("button.detail.tooltip.update"));
    this.setActionCommand("UPDATE");
  }

  public TmplJButtonDetailUpdate( String text ) {
    super( text );
    this.setActionCommand("UPDATE");
  }

  public TmplJButtonDetailUpdate( String text, Icon icon ) {
    super( text, icon );
    this.setActionCommand("UPDATE");
  }

  public void tmplMode(TemplateEvent e) {
    // Disabling due to permissions
    if ((int)(formPerms & TmplPerms.PERM_CHANGE) == 0) {
      setEnabled(false);
      return;
    }

    // Enabling due to SELECT MODE
    if (e.getMode() == TmplFormModes.MODE_SELECT) {
      setEnabled(true);
      return;
    }

    // Enabling due to LOCK MODE && Message came from parent && Form is not Inserting or Updating or Deleting
    if (e.getMode() == TmplFormModes.MODE_LOCK) {
      if (fwParent != null && e.getSource() == fwParent && e.getModeForLocking() != TmplFormModes.MODE_UPDATE && e.getModeForLocking() != TmplFormModes.MODE_INSERT && e.getModeForLocking() != TmplFormModes.MODE_UPDATE && e.getModeForLocking() != TmplFormModes.MODE_DELETE)
        setEnabled(true);
      else
        // Disabling due to LOCK MODE
        setEnabled(false);
      return;
    }

    // Disabling due to non of the above
    setEnabled(false);
  }

  public void tmplPermissions(TemplateEvent e) {
    super.tmplPermissions(e);

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
