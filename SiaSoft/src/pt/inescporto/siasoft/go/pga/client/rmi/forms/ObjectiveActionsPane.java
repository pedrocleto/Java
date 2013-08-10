package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import javax.swing.BorderFactory;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Vector;
import pt.inescporto.template.client.design.table.TmplTimestampPickerEditor;

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
public class ObjectiveActionsPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblObjectiveActions = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ObjectiveActionsPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    // table listener registration
    navBarDetail.setFW_ComponentListener(jtblObjectiveActions);
    navBarDetail.setActionListener(jtblObjectiveActions);

    //
    fwCListener.addFWComponentListener(jtblObjectiveActions);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("obj.label.actions")));
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                                res.getString("objActions.label.code"),
                                                "actionID",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
                                                res.getString("objActions.label.desc"),
                                                "actionDescription",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);

    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("objActions.label.user"),
                                               "fkUserID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
                                               cbeUser));
    colManager.addColumnNode(new FW_ColumnNode(3,
                                                res.getString("objActions.label.planStartDate"),
                                                "planStartDate",
                                                new TmplDateRenderer(),
                                                new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
                                                res.getString("objActions.label.planEndDate"),

                                                "planEndDate",
                                                new TmplDateRenderer(),
                                                new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
                                                res.getString("objActions.label.startDate"),
                                                "startDate",
                                                new TmplDateRenderer(),
                                                new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(6,
                                                res.getString("objActions.label.endDate"),
                                                "endDate",
                                                new TmplDateRenderer(),
                                                new TmplTimestampPickerEditor()));
    try {
      jtblObjectiveActions = new FW_JTable(datasource.getDataSourceByName("ObjectiveActions"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblObjectiveActions.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                           "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblObjectiveActions);
    content.add(jScrollPane1, cc.xy(2,2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

    add(navBarDetail, BorderLayout.SOUTH);
  }
}
