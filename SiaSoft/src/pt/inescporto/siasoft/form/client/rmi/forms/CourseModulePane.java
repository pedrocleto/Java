package pt.inescporto.siasoft.form.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
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
public class CourseModulePane extends JPanel {
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_JTable fwModuleList = null;
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  public CourseModulePane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
    //table listener registration
    navBarDetail.setFW_ComponentListener(fwModuleList);
    navBarDetail.setActionListener(fwModuleList);
    fwCListener.addFWComponentListener(fwModuleList);
  }

  public FW_ComponentListener getMasterListener() {
    return fwModuleList;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("courseModule.label.code"),
					       "courseModuleID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("courseModule.label.name"),
					       "courseModuleDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("courseModule.label.desc"),
					       "courseModuleResume",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("courseModule.label.type"),
					       "courseType",
					       new TmplLookupRenderer(new Object[] {null, res.getString("courseModule.label.type.internal"), res.getString("courseModule.label.type.external")},
        new Object[] {"", "I", "E"}),
					       new TmplComboBoxEditor(new Object[] {null, res.getString("courseModule.label.type.internal"), res.getString("courseModule.label.type.external")},
	new Object[] {"", "I", "E"})));

    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("courseModule.label.startDate"),
					       "courseModuleStartDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("courseModule.label.endDate"),
					       "courseModuleEndDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);

    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("courseModule.label.user"),
					       "fkUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       cbeUser));

    TmplComboBoxEditor cbeTeach = new TmplComboBoxEditor("pt.inescporto.siasoft.form.ejb.session.TeachingEntity", new Integer[] {new Integer(1), new Integer(0)});
    cbeTeach.setWatcherSubject(SyncronizerSubjects.teachENTITY);

    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("courseModule.label.teachingEntity"),
					       "fkTeachingEntityID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.form.ejb.session.TeachingEntity"),
					       cbeTeach));

    try {
      fwModuleList = new FW_JTable(datasource.getDataSourceByName("CourseModule"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    fwModuleList.setAsMaster(false);
    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwModuleList);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
