package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplResourceSingleton;

public class TmplJButtonAll extends TmplJButton {
  public TmplJButtonAll() {
    super();

    setText("all");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/all.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.all"));
    setActionCommand("ALL");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonAll(Action a) {
    super(a);
    setActionCommand("ALL");
  }

  public TmplJButtonAll(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.all"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.all"));
    setActionCommand("ALL");
  }

  public TmplJButtonAll(String text) {
    super(text);
    setActionCommand("ALL");
  }

  public TmplJButtonAll(String text, Icon icon) {
    super(text, icon);
    setActionCommand("ALL");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
