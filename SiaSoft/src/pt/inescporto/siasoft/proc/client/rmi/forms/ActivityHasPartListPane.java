package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.design.TmplJButtonDelete;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasPartDao;

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

public class ActivityHasPartListPane extends JPanel implements ActionListener {

  protected FW_JTable fwActPartInList = null;
  protected FW_JTable fwActPartOutList = null;
  protected DataSourceRMI datasourceIn = null;
  protected DataSourceRMI datasourceOut = null;
  static String activityID = null;
  JToolBar jtbarNav = new JToolBar();
  TmplJButton jbtnOk = new TmplJButton();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ActivityHasPartListPane(String activityID) {
    this.activityID = activityID;
    datasourceIn = new DataSourceRMI("ActivityHasPart") {
      protected void doSend(int toDo) throws TmplException {
	Method[] methodList = remote.getClass().getMethods();
	Method execMethod = null;

	try {
	  switch (toDo) {
	    case TmplMessages.MSG_LISTALL:
	      execMethod = remote.getClass().getMethod("getPartTree", new Class[] {String.class, String.class});
	      all = (Collection)execMethod.invoke(remote, new Object[] {ActivityHasPartListPane.activityID, "I"});
              return;
	    default:
	      super.doSend(toDo);
              return;
	  }
	}
	catch (InvocationTargetException ex1) {
	  ex1.printStackTrace();
	}
	catch (IllegalAccessException ex1) {
	  TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
	  tmplex.setDetail(ex1);
	  throw tmplex;
	}
	catch (NoSuchMethodException ex2) {
	  TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
	  tmplex.setDetail(ex2);
	  throw tmplex;
	}
	catch (SecurityException ex2) {
	  TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
	  tmplex.setDetail(ex2);
	  throw tmplex;
	}
      }
    };
    datasourceIn.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityHasPart");

    datasourceOut = new DataSourceRMI("ActivityHasPart") {
      protected void doSend(int toDo) throws TmplException {
        Method[] methodList = remote.getClass().getMethods();
        Method execMethod = null;

        try {
          switch (toDo) {
            case TmplMessages.MSG_LISTALL:
              execMethod = remote.getClass().getMethod("getPartTree", new Class[] {String.class, String.class});
              all = (Collection)execMethod.invoke(remote, new Object[] {ActivityHasPartListPane.activityID, "O"});
              return;
            default :
              super.doSend(toDo);
              return;
          }
        }
        catch (InvocationTargetException ex1) {
          ex1.printStackTrace();
        }
        catch (IllegalAccessException ex1) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex1);
          throw tmplex;
        }
        catch (NoSuchMethodException ex2) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex2);
          throw tmplex;
        }
        catch (SecurityException ex2) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex2);
          throw tmplex;
        }
      }
    };
    datasourceOut.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityHasPart");

    try {
      datasourceIn.initialize();
      datasourceOut.initialize();
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
    fwActPartInList.start();
    fwActPartOutList.start();
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("activityHasPartListPane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    // nav bar
    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnOk, null);
    add(jtbarNav, BorderLayout.EAST);

    jbtnOk.setText("Ok");
    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);
    jbtnOk.setBorderPainted(false);
    jbtnOk.setOpaque(false);
    jbtnOk.setFocusable(false);

    fwActPartInList = new FW_JTable(datasourceIn,
				    new String[] {"", res.getString("activityHasPartListPane.label.partClass"), res.getString("activityHasPartListPane.label.code"), res.getString("activityHasPartListPane.label.quantity")},
				    new String[] {"partClassID", "partID", "qty"}) {
      public void valueChanged(ListSelectionEvent e) {
	super.valueChanged(e);
//	System.out.println("Row selected changed!");
      }
    };

    fwActPartOutList = new FW_JTable(datasourceOut,
				     new String[] {"", res.getString("activityHasPartListPane.label.partClass"), res.getString("activityHasPartListPane.label.code"), res.getString("activityHasPartListPane.label.quantity")},
				     new String[] {"partID", "partClassID", "qty"}) {
      public void valueChanged(ListSelectionEvent e) {
	super.valueChanged(e);
	System.out.println("Row selected changed!");
      }
    };
    fwActPartInList.setRowHeight(25);
    fwActPartInList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwActPartInList.setShowGrid(false);
    fwActPartInList.setShowHorizontalLines(false);
    fwActPartInList.setShowVerticalLines(false);
    fwActPartInList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fwActPartInList.getTableHeader().setReorderingAllowed(false);
    fwActPartInList.getTableHeader().setResizingAllowed(false);

    fwActPartOutList.setRowHeight(25);
    fwActPartOutList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwActPartOutList.setShowGrid(false);
    fwActPartOutList.setShowHorizontalLines(false);
    fwActPartOutList.setShowVerticalLines(false);
    fwActPartOutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fwActPartOutList.getTableHeader().setReorderingAllowed(false);
    fwActPartOutList.getTableHeader().setResizingAllowed(false);

    JScrollPane jsp = new JScrollPane(fwActPartInList);
    jsp.setBorder(BorderFactory.createTitledBorder(res.getString("activityHasPartListPane.label.entryBorder")));
    JScrollPane jsPanel = new JScrollPane(fwActPartOutList);
    jsPanel.setBorder(BorderFactory.createTitledBorder(res.getString("activityHasPartListPane.label.exitBorder")));
    JPanel panel = new JPanel();
    panel.add(jsp, BorderLayout.EAST);
    panel.add(jsPanel, BorderLayout.WEST);
    add(panel, BorderLayout.CENTER);
  }
  public void actionPerformed(ActionEvent e) {
    Container parent = getParent();
    if (e.getActionCommand().equals("OK")) {
      while (parent != null && !(parent instanceof JDialog))
	parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
    if(e.getActionCommand().equals("DELETE")){
      try {
        ActivityHasPartDao actHasPartDao = (ActivityHasPartDao) datasourceIn.getCurrentRecord();
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }

    }
  }
}
