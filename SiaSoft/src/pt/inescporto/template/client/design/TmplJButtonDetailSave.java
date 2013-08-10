package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonDetailSave extends TmplJButton {
  public TmplJButtonDetailSave() {
    super();

    setText("save");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/save.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.save"));
    setActionCommand("SAVE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDetailSave( Action a ) {
    super( a );
    this.setActionCommand("SAVE");
  }

  public TmplJButtonDetailSave( Icon icon ) {
    super( icon );

    this.setText(TmplResourceSingleton.getString("button.detail.label.save"));
    this.setToolTipText(TmplResourceSingleton.getString("button.detail.tooltip.save"));
    this.setActionCommand("SAVE");
  }

  public TmplJButtonDetailSave( String text ) {
    super( text );
    this.setActionCommand("SAVE");
  }

  public TmplJButtonDetailSave( String text, Icon icon ) {
    super( text, icon );
    this.setActionCommand("SAVE");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() != TmplFormModes.MODE_SELECT && e.getMode() != TmplFormModes.MODE_LOCK)
      setEnabled(true);
    else {
      if (e.getMode() == TmplFormModes.MODE_LOCK && e.getModeForLocking() != TmplFormModes.MODE_SELECT && fwParent != null && e.getSource() == fwParent)
	setEnabled(true);
      else
	setEnabled(false);
    }
  }
}
