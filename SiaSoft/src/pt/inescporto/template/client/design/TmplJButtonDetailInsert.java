package pt.inescporto.template.client.design;

import java.awt.Dimension;
import javax.swing.*;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonDetailInsert extends TmplJButton {
  public TmplJButtonDetailInsert() {
    super();

    setText("insert");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/insert.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.insert"));
    setActionCommand("INSERT");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonDetailInsert(Action a) {
    super(a);
    this.setActionCommand("INSERT");
    this.setMinimumSize(new Dimension(60, 22));
    this.setMaximumSize(new Dimension(60, 22));
  }

  public TmplJButtonDetailInsert(Icon icon) {
    super(icon);

    this.setText(TmplResourceSingleton.getString("button.detail.label.insert"));
    this.setToolTipText(TmplResourceSingleton.getString("button.detail.tooltip.insert"));
    this.setActionCommand("INSERT");
    this.setMinimumSize(new Dimension(60, 22));
    this.setMaximumSize(new Dimension(60, 22));
  }

  public TmplJButtonDetailInsert(String text) {
    super(text);
    this.setActionCommand("INSERT");
    this.setMinimumSize(new Dimension(60, 22));
    this.setMaximumSize(new Dimension(60, 22));
  }

  public TmplJButtonDetailInsert(String text, Icon icon) {
    super(text, icon);
    this.setActionCommand("INSERT");
    this.setMinimumSize(new Dimension(60, 22));
    this.setMaximumSize(new Dimension(60, 22));
  }

  public void tmplMode(TemplateEvent e) {
    if ((int)(formPerms & TmplPerms.PERM_INSERT) == 0) {
//      System.out.println("Disabling INSERT due to permissions!");
      setEnabled(false);
      return;
    }
    if (e.getMode() == TmplFormModes.MODE_SELECT) {
//      System.out.println("Enabling INSERT due to SELECT MODE!");
      setEnabled(true);
      return;
    }

    if (e.getMode() == TmplFormModes.MODE_LOCK) {
      if (fwParent != null && e.getSource() == fwParent && e.getModeForLocking() != TmplFormModes.MODE_INSERT && e.getModeForLocking() != TmplFormModes.MODE_UPDATE) {
//        System.out.println("Enabling INSERT due to LOCK MODE && Message came from parent && Form is not Inserting! mode is<" + e.getModeForLocking() + ">");
	setEnabled(true);
      }
      else {
//        System.out.println("Disabling INSERT due to LOCK MODE! mode is<" + e.getModeForLocking() + ">");
	setEnabled(false);
      }
      return;
    }

//    System.out.println("Disabling INSERT due to nothing!");
    setEnabled(false);
  }
}
