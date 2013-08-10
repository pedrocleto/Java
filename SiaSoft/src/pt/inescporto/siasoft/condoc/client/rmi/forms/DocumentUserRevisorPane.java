package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
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
public class DocumentUserRevisorPane extends JPanel{
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblRevisors = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public DocumentUserRevisorPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource=datasource;
    this.fwCListener= fwCListener;
    initialize();

    //table listener registration
    navBarDetail.setFW_ComponentListener(jtblRevisors);
    navBarDetail.setActionListener(jtblRevisors);


    fwCListener.addFWComponentListener(jtblRevisors);
  }


  private void initialize() {

    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("revisorsapp.label.revisions")));

    // set linkcondition binds dinamically
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                                res.getString("docuserapprev.label.user"),
                                                "userID",
                                                 new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
                                                 cbeUser));
    try {
      jtblRevisors = new FW_JTable(datasource.getDataSourceByName("DocumentUserRevisors"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblRevisors.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                           "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblRevisors);
    content.add(jScrollPane1, cc.xy(2,2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);

  }
}