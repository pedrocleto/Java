package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
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
public class AuditActionsPane extends JPanel {
  DataSource datasource = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_ComponentListener fwCListener = null;
  FW_JTable fwAuditActionsList = null;
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  TmplJLabel jlblAuditActionName = new TmplJLabel();
  public AuditActionsPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwAuditActionsList);
    navBarDetail.setActionListener(fwAuditActionsList);

    fwCListener.addFWComponentListener(fwAuditActionsList);
  }

  public FW_ComponentListener getMasterListener() {
    return fwAuditActionsList;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("label.code"),
					       "auditActionID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("label.desc"),
					       "auditActionDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("auditAction.label.startPlanDate"),
					       "startPlanDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));

    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("auditAction.label.endPlanDate"),
					       "endPlanDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));

    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("auditAction.label.startDate"),
					       "startDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));

    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("auditAction.label.endDate"),
					       "endDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));

    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("auditAction.label.ncValue"),
					       "ncValue",
					       new TmplCheckBoxRender(),
					       new TmplCheckBoxEditor()));

    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("auditAction.label.obs"),
					       "obs",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User",new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
     cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);
     colManager.addColumnNode(new FW_ColumnNode(8,
                                                res.getString("preventiveAction.label.user"),
                                                "fkUserID",
                                                new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
                                                cbeUser));

    try {
      fwAuditActionsList = new FW_JTable(datasource.getDataSourceByName("AuditActions"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    fwAuditActionsList.setAsMaster(false);
    fwAuditActionsList.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
//        System.out.println(fwAuditActionsList.getModel().getValueAt(fwAuditActionsList.getSelectedRow(),0));
        jlblAuditActionName.setText((String)fwAuditActionsList.getModel().getValueAt(fwAuditActionsList.getSelectedRow(),0));
      }
    });

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwAuditActionsList);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }

  public TmplJLabel getJlblAuditActionName() {
    return jlblAuditActionName;
  }
}
