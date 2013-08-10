package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonPrev extends TmplJButton {
  public TmplJButtonPrev() {
    super();

    setText("previous");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/previous.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.previous"));
    setActionCommand("PREV");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonPrev(Action a) {
    super(a);
    setActionCommand("PREV");
  }

  public TmplJButtonPrev(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.previous"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.previous"));
    setActionCommand("PREV");
  }

  public TmplJButtonPrev(String text) {
    super(text);
    setActionCommand("PREV");
  }

  public TmplJButtonPrev(String text, Icon icon) {
    super(text, icon);
    setActionCommand("PREV");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT)
      this.setEnabled(true);
    else
      this.setEnabled(false);
  }
}
