package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import com.jgoodies.forms.layout.*;
import pt.inescporto.siasoft.events.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.design.table.*;
import pt.inescporto.template.client.rmi.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.elements.*;
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
public class PreventiveActionsPane extends JPanel{

  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_JTable fwPreventiveActionsList = null;
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  private TmplJLabel jlblAuditActionName = new TmplJLabel();

  public PreventiveActionsPane(DataSource datasource, FW_ComponentListener fwCListener, TmplJLabel jlblAuditActionName) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.jlblAuditActionName = jlblAuditActionName;
    initialize();
    //table listener registration
    navBarDetail.setFW_ComponentListener(fwPreventiveActionsList);
    navBarDetail.setActionListener(fwPreventiveActionsList);
    fwCListener.addFWComponentListener(fwPreventiveActionsList);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("label.code"),
					       "preventiveActionID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("label.desc"),
					       "preventiveActionDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("preventiveAction.label.planStartDate"),
					       "planStartDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("preventiveAction.label.planEndDate"),
					       "planEndDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("preventiveAction.label.startDate"),
					       "startDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("preventiveAction.label.endDate"),
					       "endDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("preventiveAction.label.obs"),
					       "obs",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User",new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);
    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("preventiveAction.label.user"),
					       "fkUserID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
                                               cbeUser));

    try {
      fwPreventiveActionsList = new FW_JTable(datasource.getDataSourceByName("PreventiveActions"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    fwPreventiveActionsList.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                            "5px, pref,4dlu,50dlu:grow, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwPreventiveActionsList);

    content.add(jsp, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.FILL));
    content.add(jlblAuditActionName, cc.xy(2,2));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
