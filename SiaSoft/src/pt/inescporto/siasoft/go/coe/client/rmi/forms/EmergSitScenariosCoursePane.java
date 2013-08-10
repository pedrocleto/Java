package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.Vector;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
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
public class EmergSitScenariosCoursePane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  private JScrollPane jScrollPane1 = new JScrollPane();
  private FW_JTable jtblCourses = null;
  private FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public EmergSitScenariosCoursePane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    navBarDetail.setFW_ComponentListener(jtblCourses);
    navBarDetail.setActionListener(jtblCourses);

    fwCListener.addFWComponentListener(jtblCourses);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("emergSitscen.label.courses")));

    // set linkcondition binds dinamically
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("emergSitdef.label.code"),
					       "emergPlanCourseID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("emergSitdef.label.description"),
					       "emergPlanDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    TmplComboBoxEditor cbeForm =  new TmplComboBoxEditor("pt.inescporto.siasoft.form.ejb.session.CoursePlan",new Integer[] {new Integer(1), new Integer(0)}, "fkEnterpriseID = ?", binds);
    cbeForm.setWatcherSubject(SyncronizerSubjects.courseFORM);
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("emergSitScenCourses.label.course"),
					       "fkCoursePlanID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.form.ejb.session.CoursePlan"),
                                               cbeForm));

    try {
      jtblCourses = new FW_JTable(datasource.getDataSourceByName("EmergencyPlanCourses"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblCourses.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblCourses);
    content.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
