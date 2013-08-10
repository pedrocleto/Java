package pt.inescporto.siasoft.form.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

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
public class CourseModuleUsersPane extends JPanel {

  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_JTable fwCourseModuleUsersList = new FW_JTable();
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  public CourseModuleUsersPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
    //table listener registration
    navBarDetail.setFW_ComponentListener(fwCourseModuleUsersList);
    navBarDetail.setActionListener(fwCourseModuleUsersList);
    fwCListener.addFWComponentListener(fwCourseModuleUsersList);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("courseUser.label.border")));

    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);

    colManager.addColumnNode(new FW_ColumnNode(res.getString("courseUser.label.user"),
					       "fkUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       cbeUser));
    try {
      fwCourseModuleUsersList = new FW_JTable(datasource.getDataSourceByName("CourseModuleUsers"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    fwCourseModuleUsersList.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwCourseModuleUsersList);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
