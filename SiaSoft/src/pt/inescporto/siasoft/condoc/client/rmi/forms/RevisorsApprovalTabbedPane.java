package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.util.ResourceBundle;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class RevisorsApprovalTabbedPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane jtpRevisorsApp = new JTabbedPane(JTabbedPane.BOTTOM);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  public RevisorsApprovalTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("resvisorapp.label.listrevapp")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    DocumentHistoryTab dochistTab =new DocumentHistoryTab(datasource, fwCListener);
    dochistTab.setWatcherSubject(SyncronizerSubjects.documentHIST);
    jtpRevisorsApp.addTab(res.getString("revisorsapp.label.revisions"), new DocumentUserRevisorPane(datasource, fwCListener));
    jtpRevisorsApp.addTab(res.getString("revisorsapp.label.approvals"), new DocumentUserApprovalPane(datasource, fwCListener));
    jtpRevisorsApp.addTab(res.getString("revisorsapp.label.history"), dochistTab);

    add(jtpRevisorsApp, BorderLayout.CENTER);
  }
}
