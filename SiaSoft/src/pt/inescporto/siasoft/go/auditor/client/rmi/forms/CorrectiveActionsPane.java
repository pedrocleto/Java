package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.design.table.TmplTimestampPickerEditor;
import pt.inescporto.template.client.design.TmplJLabel;
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
public class CorrectiveActionsPane extends JPanel {

  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_JTable fwCorrectiveActionsList = null;
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  TmplJLabel jlblAuditActionName = null;
  public CorrectiveActionsPane(DataSource datasource, FW_ComponentListener fwCListener, TmplJLabel jlblAuditActionName) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.jlblAuditActionName = jlblAuditActionName;
    initialize();

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwCorrectiveActionsList);
    navBarDetail.setActionListener(fwCorrectiveActionsList);

    fwCListener.addFWComponentListener(fwCorrectiveActionsList);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));


    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                               res.getString("label.code"),
                                               "correctiveActionID",
                                               new TmplStringRenderer(),
                                               new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
                                               res.getString("label.desc"),
                                               "correctiveActionDescription",
                                               new TmplStringRenderer(),
                                               new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
                                               res.getString("correctiveAction.label.planStartDate"),
                                               "planStartDate",
                                               new TmplDateRenderer(),
                                               new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
                                               res.getString("correctiveAction.label.planEndDate"),
                                               "planEndDate",
                                               new TmplDateRenderer(),
                                               new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
                                               res.getString("correctiveAction.label.startDate"),
                                               "startDate",
                                               new TmplDateRenderer(),
                                               new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
                                               res.getString("correctiveAction.label.endDate"),
                                               "endDate",
                                               new TmplDateRenderer(),
                                               new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(6,
                                               res.getString("correctiveAction.label.obs"),
                                               "obs",
                                               new TmplStringRenderer(),
                                               new TmplStringEditor()));
    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User",new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);
    colManager.addColumnNode(new FW_ColumnNode(7,
                                               res.getString("correctiveAction.label.user"),
                                               "fkUserID",
                                              new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
                                              cbeUser));

    try {
      fwCorrectiveActionsList = new FW_JTable(datasource.getDataSourceByName("CorrectiveActions"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    fwCorrectiveActionsList.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, pref,4dlu,50dlu:grow, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwCorrectiveActionsList);
    content.add(jsp, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.FILL));
    content.add(jlblAuditActionName, cc.xy(2,2));
    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }

}
