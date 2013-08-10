package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonDetailCancel extends TmplJButton {
  public TmplJButtonDetailCancel() {
    super();

    setText("cancel");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/cancel.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.cancel"));
    setActionCommand("CANCEL");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDetailCancel( Action a ) {
    super( a );
    setActionCommand("CANCEL");
  }

  public TmplJButtonDetailCancel( Icon icon ) {
    super( icon );

    setText(TmplResourceSingleton.getString("button.detail.label.cancel"));
    setToolTipText(TmplResourceSingleton.getString("button.detail.tooltip.cancel"));
    setActionCommand("CANCEL");
  }

  public TmplJButtonDetailCancel( String text ) {
    super( text );
    setActionCommand("CANCEL");
  }

  public TmplJButtonDetailCancel( String text, Icon icon ) {
    super( text, icon );
    setActionCommand("CANCEL");
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
