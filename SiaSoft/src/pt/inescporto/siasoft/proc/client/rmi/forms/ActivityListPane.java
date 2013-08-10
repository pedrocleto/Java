package pt.inescporto.siasoft.proc.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

import pt.inescporto.siasoft.proc.client.rmi.editor.ProcessEditor;

import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.utils.TmplMessages;

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
public class ActivityListPane extends JPanel implements ActionListener{
  static int perc = 100;
  static int j = 1;

  FW_JTable fwActPartInList = null;
  FW_JTable fwActPartOutList = null;
  static String activityID = null;
  DataSourceRMI datasourceIn = null;
  DataSourceRMI datasourceOut = null;

  JToolBar  jtbarNav = new JToolBar();
  JToolBar jtbar = new JToolBar();
  TmplJButton jbtnOk = new TmplJButton();
  TmplJButton jbtnCal = new TmplJButton(new ImageIcon(ProcessEditor.class.getResource("icons/business.png")));
  TmplJTextField jtfldTotalLoss = new TmplJTextField();
  TmplJTextField jtfldTotal = new TmplJTextField();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ActivityListPane(String activityID){
    this.activityID = activityID;
    datasourceIn = new DataSourceRMI("ActivityHasPart") {
      protected void doSend(int toDo) throws TmplException {
	Method[] methodList = remote.getClass().getMethods();
	Method execMethod = null;

	try {
	  switch (toDo) {
	    case TmplMessages.MSG_LISTALL:
	      execMethod = remote.getClass().getMethod("getPartTree", new Class[] {String.class, String.class});
	      all = (Collection)execMethod.invoke(remote, new Object[] {ActivityListPane.activityID, "I"});
              return;
            default :
              super.doSend(toDo);
              return;
	  }
	}
	catch (InvocationTargetException ex) {
          ex.printStackTrace();
	}
        catch (IllegalAccessException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
          throw tmplex;
        }
        catch (NoSuchMethodException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
          throw tmplex;
        }
        catch (SecurityException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
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
              all = (Collection)execMethod.invoke(remote, new Object[] {ActivityListPane.activityID, "O"});
              return;
            default :
              super.doSend(toDo);
              return;
          }
        }
        catch (InvocationTargetException ex) {
          ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
          throw tmplex;
        }
        catch (NoSuchMethodException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
          throw tmplex;
        }
        catch (SecurityException ex) {
          TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
          tmplex.setDetail(ex);
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
    setBorder(BorderFactory.createTitledBorder("Balanço de Massa"));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    // nav bar
    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnOk, null);
    jtbarNav.add(jbtnCal, null);

    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);
    jbtnOk.setText("Ok");
    jbtnOk.setBorderPainted(false);
    jbtnOk.setOpaque(false);
    jbtnOk.setFocusable(false);

    jbtnCal.setActionCommand("CALCULAR");
    jbtnCal.setBorderPainted(false);
    jbtnCal.setOpaque(false);
    jbtnCal.setFocusable(false);
    jbtnCal.addActionListener(this);
    jbtnCal.setEnabled(false);

    add(jtbarNav, java.awt.BorderLayout.EAST);
    fwActPartInList = new FW_JTable(datasourceIn,
				    new String[] {"","", res.getString("activityListPane.label.code"), res.getString("activityListPane.label.quantity")},
				    new String[] {"partID", "qty"}) {
      public void valueChanged(ListSelectionEvent e){
	super.valueChanged(e);
	if (mode == TmplFormModes.MODE_SELECT && !e.getValueIsAdjusting() && getSelectedRow() != -1) {
	  Object attrs = tm.getAttrsAt(getSelectedRow());
	  try {
//	    System.out.println("Row selected changed!");
	    jbtnCal.setEnabled(true);
	    datasource.setAttrs(attrs);
	    datasource.refresh(getModel());
	  }
	  catch (DataSourceException ex) {
	    ex.printStackTrace();
	  }
	}
        if(getSelectedRow() == -1)
          jbtnCal.setEnabled(false);

      }
    };
    fwActPartOutList = new FW_JTable(datasourceOut,
				     new String[] {"","", res.getString("activityListPane.label.code"), res.getString("activityListPane.label.quantity")},
                                     new String[] {"partID", "qty"}){
      public void valueChanged(ListSelectionEvent e) {
	super.valueChanged(e);

        if (mode == TmplFormModes.MODE_SELECT && !e.getValueIsAdjusting() && getSelectedRow() != -1) {
	  Object attrs = tm.getAttrsAt(getSelectedRow());
	  try {
//	    System.out.println("Row selected changed!");
            jbtnCal.setEnabled(true);
            datasource.setAttrs(attrs);
	    datasource.refresh(getModel());
	  }
	  catch (DataSourceException ex) {
	    ex.printStackTrace();
	  }
	}
        if(getSelectedRow() == -1)
          jbtnCal.setEnabled(false);
      }
    };
    fwActPartInList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    fwActPartInList.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(e.getClickCount() == 2){
            fwActPartInList.clearSelection();
          }
        }
      });
    fwActPartInList.setRowHeight(25);
    fwActPartInList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwActPartInList.setShowGrid(false);
    fwActPartInList.setShowHorizontalLines(false);
    fwActPartInList.setShowVerticalLines(false);
    fwActPartInList.getTableHeader().setReorderingAllowed(false);
    fwActPartInList.getTableHeader().setResizingAllowed(false);

    fwActPartOutList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    fwActPartOutList.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(e.getClickCount() == 2){
            fwActPartOutList.clearSelection();
          }
        }
      });
    fwActPartOutList.setRowHeight(25);
    fwActPartOutList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwActPartOutList.setShowGrid(false);
    fwActPartOutList.setShowHorizontalLines(false);
    fwActPartOutList.setShowVerticalLines(false);
    fwActPartOutList.getTableHeader().setReorderingAllowed(false);
    fwActPartOutList.getTableHeader().setResizingAllowed(false);

    JScrollPane jsp = new JScrollPane(fwActPartInList);
    jsp.setBorder(BorderFactory.createTitledBorder(res.getString("activityListPane.label.entryBorder")));
    JScrollPane jsPanel = new JScrollPane(fwActPartOutList);
    jsPanel.setBorder(BorderFactory.createTitledBorder(res.getString("activityListPane.label.exitBorder")));

    MyPanel myPanel = new MyPanel();
    JScrollPane scrollPane = new JScrollPane(myPanel);

    JPanel panelIn = new JPanel();
    panelIn.add(jsp, BorderLayout.EAST);
    panelIn.add(jsPanel, BorderLayout.WEST);
    panelIn.add(scrollPane,BorderLayout.CENTER);
    add(panelIn, BorderLayout.CENTER);
    add(myPanel,BorderLayout.SOUTH);
  }


  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("OK")) {
     disposeWindow();

    }
    if(e.getActionCommand().equals("CALCULAR")){
      float actPartInTotal = 0;
      float actPartOutTotal = 0;
      float totalLoss = 0;
      float total = 0;
      int selRowsIn = fwActPartInList.getSelectedRows().length;
      int selRowsOut = fwActPartOutList.getSelectedRows().length;

      int [] rowIndex=fwActPartInList.getSelectedRows();
      for (int i = 0; i < selRowsIn; i++) {

	Float value = (Float)fwActPartInList.getValueAt(rowIndex[i], j);
	actPartInTotal = actPartInTotal + value;
//	System.out.println("Entrada: O valor e <" + value.floatValue() + ">");
      }
      int [] rowOutIndex = fwActPartOutList.getSelectedRows();
      for (int i = 0; i < selRowsOut; i++) {
	Float value = (Float)fwActPartOutList.getValueAt(rowOutIndex[i], j);
	actPartOutTotal = actPartOutTotal + value;
//	System.out.println("Saída: O valor e <" + value.floatValue() + ">");
      }
      totalLoss = ((actPartInTotal - actPartOutTotal) / actPartInTotal) * perc;
//      System.out.println("O total de perdas e <" + totalLoss + ">");
      jtfldTotalLoss.setText(Float.toString(totalLoss));

      total = (actPartOutTotal / actPartInTotal) * perc;
//      System.out.println("O total e <" + total + ">");
      jtfldTotal.setText(Float.toString(total));

      fwActPartInList.clearSelection();
      fwActPartOutList.clearSelection();
    }
  }
  public void disposeWindow(){
     Container parent = getParent();
     while (parent != null && !(parent instanceof JDialog))
      parent = parent.getParent();
    ((JDialog)parent).dispose();

  }
  public class MyPanel extends JPanel{
    TmplJLabel jlblTotalLoss = new TmplJLabel();
    TmplJLabel jlblPerc = new TmplJLabel("%");
    TmplJLabel jlblPerc1 = new TmplJLabel("%");
    TmplJLabel jlblTotal = new TmplJLabel();

    public MyPanel(){
      try {
	jbInit();
      }
      catch (Exception ex) {
      }
    }
    private void jbInit() throws Exception {
      setLayout(new BorderLayout());

      jlblTotalLoss.setText(res.getString("activityListPane.label.totalLoss"));
      jtfldTotalLoss.setLabel(res.getString("activityListPane.label.totalLoss"));
      jtfldTotalLoss.setEnabled(false);
      jlblTotal.setText(res.getString("activityListPane.label.total"));
      jtfldTotal.setEnabled(false);

      FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 150dlu:grow, 5px",
					     "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

      JPanel content = new JPanel();
      content.setOpaque(false);
      content.setLayout(formLayout);
      CellConstraints cc = new CellConstraints();

      content.add(jlblTotalLoss, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
      content.add(jtfldTotalLoss, cc.xy(4, 2));
      content.add(jlblPerc, cc.xy(6,2));

      content.add(jlblTotal, cc.xy(2, 4));
      content.add(jtfldTotal, cc.xy(4,4));
      content.add(jlblPerc1, cc.xy(6,4));

      add(content, BorderLayout.CENTER);
    }
  }
}
