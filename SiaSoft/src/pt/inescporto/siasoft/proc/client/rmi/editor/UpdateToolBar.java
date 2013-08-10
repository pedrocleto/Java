package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import java.awt.Dimension;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */
public class UpdateToolBar extends JPanel implements TemplateComponentListener{
  private ItemTypeList itemTypeList = null;
  private JScrollPane jsp = null;

  public UpdateToolBar() {
    super();

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    itemTypeList = new ItemTypeList();
    itemTypeList.setWatcherSubject(SyncronizerSubjects.activityTypeFORM);
    jsp = new JScrollPane(itemTypeList);

    setBorder(BorderFactory.createRaisedBevelBorder());
    setPreferredSize(new Dimension(200,150));
    add(jsp, BorderLayout.CENTER);
  }

  public void tmplPermissions(TemplateEvent e) {}

  public void tmplMode(TemplateEvent e) {
    itemTypeList.getFwActList().setEnabled(e.getMode() != TmplFormModes.MODE_SELECT && e.getMode() != TmplFormModes.MODE_LOCK);
    itemTypeList.getFwActList().clearSelection();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public boolean tmplRequired(TemplateEvent e) {
    return false;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }
  public void tmplDispose(TemplateEvent e) {
  }
}
