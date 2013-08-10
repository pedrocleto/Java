package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.Container;
import java.awt.Font;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityTypeDao;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.siasoft.proc.client.rmi.forms.InterLevelActivityPane;

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
public class ActivityTable extends JPanel implements ActionListener, MouseListener {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private Hashtable<String, ActivityTypeDao> actTypeList = null;
  protected DataSourceRMI datasource = null;
  protected DataSourceRMI dataSource = null;
  protected FW_JTable fwActivityList = null;

  JToolBar jtbarNav = new JToolBar();
  TmplJButton jbtnOk = new TmplJButton("Ok");
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();

  Graph graph = null;
  String fkEnterpriseID = null;
  private InterLevelActivityPane interLevelActivityPane = null;

  public ActivityTable(String fkEnterpriseID, Graph graph, InterLevelActivityPane interLevelActivityPane) {

    datasource = new DataSourceRMI("Activity");
    datasource.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");

    dataSource = new DataSourceRMI("ActivityType");
    dataSource.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityType");

    this.fkEnterpriseID = fkEnterpriseID;
    this.graph = graph;
    this.interLevelActivityPane=interLevelActivityPane;

    try {
      datasource.initialize();
      Vector binds = new Vector();
      binds.add(new TplString(fkEnterpriseID));
      datasource.setLinkCondition("fkEnterpriseID = ?", binds);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    try {
      dataSource.initialize();

    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {

    setLayout(new BorderLayout());
    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("activityTable.label.code"),
					       "activityID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("activityTable.label.desc"),
					       "activityDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));


    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("activityTable.label.father"),
					       "activityFatherID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    // nav bar
    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnOk, null);
    jtbarNav.add(jbtnCancel, null);
    add(jtbarNav, java.awt.BorderLayout.EAST);

    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);

    jbtnOk.setBorderPainted(false);
    jbtnOk.setOpaque(false);
    jbtnOk.setFocusable(false);

    jbtnCancel.addActionListener(this);

    fwActivityList = new FW_JTable(datasource, null, colManager);
    fwActivityList.start();
    fwActivityList.addMouseListener(this);
    fwActivityList.setRowHeight(25);
    fwActivityList.setFont(new Font("Dialog", Font.PLAIN, 10));

    JScrollPane jsp = new JScrollPane(fwActivityList);

    add(jsp, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("CANCEL")) {
      disposeWindow();
    }
    if (e.getActionCommand().equals("OK")) {
      if (fwActivityList.getSelectedRow() != -1) {
	ActivityDao activityDao = (ActivityDao)((FW_TableModel)fwActivityList.getModel()).getAttrsAt(fwActivityList.getSelectedRow());
        interLevelActivityPane.getJtfldActivityLinkId().setText(activityDao.activityID.getValue());
        interLevelActivityPane.getJlfldActivityLinkDesc().setText(activityDao.activityDescription.getValue());
      }
      disposeWindow();
    }
  }

 public void mouseClicked(MouseEvent e){
   if(e.getClickCount()== 2){
     if (fwActivityList.getSelectedRow() != -1) {
       ActivityDao activityDao = (ActivityDao)((FW_TableModel)fwActivityList.getModel()).getAttrsAt(fwActivityList.getSelectedRow());
       interLevelActivityPane.getJtfldActivityLinkId().setText(activityDao.activityID.getValue());
       interLevelActivityPane.getJlfldActivityLinkDesc().setText(activityDao.activityDescription.getValue());
     }
     disposeWindow();

   }
 }
 public void disposeWindow(){
   Container parent = getParent();
   while (parent != null && !(parent instanceof JDialog))
       parent = parent.getParent();
     ((JDialog)parent).dispose();

 }
 public void mousePressed(MouseEvent e){}
 public void mouseReleased(MouseEvent e){}
 public void mouseEntered(MouseEvent e){}

 public void mouseExited(MouseEvent e){}
}
