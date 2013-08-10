package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonCancel extends TmplJButton {
  public TmplJButtonCancel() {
    super();

    setText("cancel");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/cancel.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.cancel"));
    setActionCommand("CANCEL");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonCancel(Action a) {
    super(a);
    setActionCommand("CANCEL");
  }

  public TmplJButtonCancel(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.cancel"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.cancel"));
    setActionCommand("CANCEL");
  }

  public TmplJButtonCancel(String text) {
    super(text);
    setActionCommand("CANCEL");
  }

  public TmplJButtonCancel(String text, Icon icon) {
    super(text, icon);
    setActionCommand("CANCEL");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() != TmplFormModes.MODE_SELECT && e.getMode() != TmplFormModes.MODE_LOCK)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
