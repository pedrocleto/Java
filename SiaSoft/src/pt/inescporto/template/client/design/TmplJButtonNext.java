package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonNext extends TmplJButton {
  public TmplJButtonNext() {
    super();

    setText("next");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/forward.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.next"));
    setActionCommand("NEXT");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonNext(Action a) {
    super(a);
    setActionCommand("NEXT");
  }

  public TmplJButtonNext(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.next"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.next"));
    setActionCommand("NEXT");
  }

  public TmplJButtonNext(String text) {
    super(text);
    setActionCommand("NEXT");
  }

  public TmplJButtonNext(String text, Icon icon) {
    super(text, icon);
    setActionCommand("NEXT");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
