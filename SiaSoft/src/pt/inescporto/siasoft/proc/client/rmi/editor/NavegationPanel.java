package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JPanel;

import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.TmplFormModes;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class NavegationPanel extends JPanel implements TemplateComponentListener{

  private MyTabbedPane tabbedPane = null;
  private ActivityHasPartTree actHasPartTree = null;
  private ActHasPartPane actHasPartPane = null;
  private EnvAspectPane envAspectPane = null;
  private GraphPanel graphPanel = null;
  private ActivityHasLinksPane activityHasLinksPane = null;
  private ProcessEditor pEditor = null;

  public NavegationPanel(GraphPanel graphPanel, ProcessEditor pEditor) {
    super(new BorderLayout());
    this.graphPanel = graphPanel;
    this.pEditor = pEditor;
    try {
    jbInit();
   }
  catch (Exception ex) {
    ex.printStackTrace();
  }

  }
  private void jbInit() throws Exception{

    actHasPartTree = new ActivityHasPartTree();
    actHasPartTree.setWatcherSubject(SyncronizerSubjects.partIdFORM);
    actHasPartTree.setWatcherSubject(SyncronizerSubjects.partIdDetailFORM);

    actHasPartPane = new ActHasPartPane(actHasPartTree);

    envAspectPane = new EnvAspectPane(graphPanel);
    envAspectPane.setWatcherSubject(SyncronizerSubjects.envAspClassFORM);

    activityHasLinksPane = new ActivityHasLinksPane(pEditor);

    tabbedPane = new MyTabbedPane(actHasPartPane, envAspectPane, activityHasLinksPane);
    tabbedPane.setFont(new Font("Dialog", Font.PLAIN, 10));

    add(tabbedPane, BorderLayout.CENTER);
    setPreferredSize(new Dimension(220,220));
  }

  public void tmplPermissions(TemplateEvent e) {}

  public void tmplMode(TemplateEvent e) {
    actHasPartTree.setEnabled(e.getMode() != TmplFormModes.MODE_UPDATE && e.getMode() != TmplFormModes.MODE_INSERT && e.getMode() != TmplFormModes.MODE_LOCK);
    actHasPartTree.clearSelection();

    envAspectPane.getFwEASList().setEnabled(e.getMode() != TmplFormModes.MODE_UPDATE && e.getMode() != TmplFormModes.MODE_INSERT && e.getMode() != TmplFormModes.MODE_LOCK);
    envAspectPane.getFwEASList().clearSelection();
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

  public EnvAspectPane getEnvAspectPane() {
    return envAspectPane;
  }

  public MyTabbedPane getTabbedPane() {
    return tabbedPane;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
