/*
 * @(#)TmplJTable.java            0.01 22/10/2001
 *
 * Copyright 2001 INESC Porto. All Rights Reserved.
 *
 */

package pt.inescporto.template.client.design;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.design.table.*;
import pt.inescporto.template.client.design.table.TmplTableModel;
import pt.inescporto.template.client.design.*;

/**
 * This is an implementation of <code>TmplJTable</code> that
 * uses a <code>TmplTableModel</code> as a model for driving data from
 * <code>TmplProxie</code>. Implements <code>Template</code> interface.
 * <p>
 *
 * @version 0.01 22/10/2001
 * @author Jorge Pereira
 *
 * @see TmplTableModel
 * @see TmplColumnModel
 * @see TmplColumnString
 * @see TmplColumnInteger
 * @see Template
 */

public class TmplJTable extends JTable implements DataSourceListener, TemplateComponentListener {
  //
  // private attributes
  //
  private String url;
  private String[] headers;
  private String[] fields;
  private String staticLinkCondition;
  private int msgCode = TmplMessages.MSG_LISTALL;

  private String linkCondition = null;
  private Vector<TplObject> binds = null;
  private String[][] bindsMap = null;
  private Vector controlButtons = new Vector();

  //
  // protected attributes
  //

  protected int mode = TmplFormModes.MODE_SELECT;

  //
  // Constructores
  //

  /**
   * null constructor for Designer use
   */
  public TmplJTable() {
    super();
//    setPreferredSize(getPreferredScrollableViewportSize());
  }

  /**
   * Constructs a <code>TmplJTable</code> that is initialized with
   * <code>TmplDataModel</code> as the data model, <code>TmplColumnModel</code>
   * as the column model, and a default selection model. The initial state is set
   * to SELECT mode.
   * Only one operation of INSERT, UPDATE or DELETE can be done at a time and
   * only can affect one row.
   *
   * @param url       used for TmplTableModel
   * @param headers   used for TmplTableModel
   * @param fields    used for TmplTableModel
   * @see TmplTableModel
   */
  public TmplJTable(String url, String[] headers, String[] fields) {
    this(url, headers, fields, null, null, TmplMessages.MSG_LISTALL);
  }

  public TmplJTable(String url, String[] headers, String[] fields, int msgCode) {
    this(url, headers, fields, null, null, msgCode);
  }

  public TmplJTable(String url, String[] headers, String[] fields, Vector binds) {
    this(url, headers, fields, binds, null, TmplMessages.MSG_LISTALL);
  }

  public TmplJTable(String url, String[] headers, String[] fields, Vector binds, int msgCode) {
    this(url, headers, fields, binds, null, msgCode);
  }

  public TmplJTable(String url, String[] headers, String[] fields, String staticLinkCondition) {
    this(url, headers, fields, null, staticLinkCondition, TmplMessages.MSG_LISTALL);
  }

  public TmplJTable(String url, String[] headers, String[] fields, Vector binds,
                    String staticLinkCondition) {
    this(url, headers, fields, null, staticLinkCondition, TmplMessages.MSG_LISTALL);
  }

  public TmplJTable(String url, String[] headers, String[] fields, Vector binds,
                    String staticLinkCondition, int msgCode) {
    super();

    this.url = url;
    this.headers = headers;
    this.fields = fields;
    this.binds = binds;
    this.staticLinkCondition = staticLinkCondition;
    this.msgCode = msgCode;
//    setPreferredSize(getPreferredScrollableViewportSize());
  }

  //
  // management of JavaBeans properties
  //

  /**
   * Definition of data source
   * @param url the url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  /**
   * Definition of headers for the table columns
   * @param headers list of names
   */
  public void setHeaders(String headers[]) {
    this.headers = headers;
  }

  public String[] getHeaders() {
    return headers;
  }

  /**
   * Definition of the list of field names
   * @param fields list of fields
   */
  public void setFields(String[] fields) {
    this.fields = fields;
  }

  public String[] getFields() {
    return fields;
  }

  /**
   * Definition of static filter on data source
   * @param staticLinkCondition sql statement definition where clause
   */
  public void setStaticLinkCondition(String staticLinkCondition) {
    this.staticLinkCondition = staticLinkCondition;
  }

  /**
   * Get static sql statement where clause
   * @return sql statement
   */
  public String getStaticLinkCondition() {
    return staticLinkCondition;
  }

  /**
   * Definition of the code message to send to data source for populate
   * this table.
   * @param msgCode the code message based on <code>TmplMessages</code>
   * @see TmplMessages
   */
  public void setMsgCode(int msgCode) {
    this.msgCode = msgCode;
  }

  /**
   * Get Message code provided for table populate
   * @return <code>TmplMessages</code> message code
   * @see <code>TmplMessages</code>
   */
  public int getMsgCode() {
    return msgCode;
  }

  //
  // management of linkconditions
  //

  /**
   * Definition of components from master data source that provides values
   * for link condition
   * @param links list of <code>TmplJTextField</code> components
   * @see TmplJTextField
   */
  public void setBinds(JComponent[] links) {
    linkCondition = this.getLinkConditionStmt(links);
  }

  public void setBindsMap(String[][] bindsMap) {
    this.bindsMap = bindsMap;
  }

  public void setBinds(JComponent[] links, String[] fields) {
    linkCondition = this.getLinkConditionStmt(links, fields);
  }

  /**
   * Constructs sql where clause based on <code>TmplJTextField</code> list
   * @param links list of <code>TmplJTextField</code> components
   * @return dinamic link condition statement
   * @see TmplJTextField
   */
  protected String getLinkConditionStmt(JComponent[] links) {
    String dinamicLinkCondition = "";

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField)
        dinamicLinkCondition += ((TmplJTextField)links[i]).getField() + " = ? ";
      dinamicLinkCondition += " AND ";
    }

    // trim string to a valid where statment
    dinamicLinkCondition = dinamicLinkCondition.substring(0, dinamicLinkCondition.length() - 5);

    return dinamicLinkCondition;
  }

  protected String getLinkConditionStmt(JComponent[] links, String[] fields) {
    String dinamicLinkCondition = "";

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField)
        dinamicLinkCondition += fields[i] + " = ? ";
      dinamicLinkCondition += " AND ";
    }

    // trim string to a valid where statment
    dinamicLinkCondition = dinamicLinkCondition.substring(0, dinamicLinkCondition.length() - 5);

    return dinamicLinkCondition;
  }
  //
  // other stuff
  //

  /**
   * Create default TmplColumns from <code>TmplTableModel</code> based on
   * attributes class type. For each type is added a TmplColumnXXX for best
   * rendering
   */
  protected void createTmplColumnsFromModel() {
    for (int i = 0; i < dataModel.getColumnCount(); i++) {
      Class type = dataModel.getColumnClass(i);
      if (type == TplString.class)
        addColumn(new TmplTableColumnText(i, ((TmplTableModel)dataModel).getColumnFieldName(i)));
      else if (type == TplInt.class)
        addColumn(new TmplTableColumnInteger(i, ((TmplTableModel)dataModel).getColumnFieldName(i)));
      else if (type == TplLong.class)
        addColumn(new TmplTableColumnLong(i, ((TmplTableModel)dataModel).getColumnFieldName(i)));
      else if (type == TplFloat.class)
        addColumn(new TmplTableColumnFloat(i, ((TmplTableModel)dataModel).getColumnFieldName(i)));
      else if (type == TplTimestamp.class)
        addColumn(new TmplTableColumnTimestamp(i, ((TmplTableModel)dataModel).getColumnFieldName(i)));
      else
        addColumn(new TableColumn(i));
    }
  }

  /**
   * <code>TmplJTable</code> will reponde to actions like INSERT, UPDATE, DELETE,
   * SAVE and CANCEL that can be generate from Navigation Template Buttons. Register
   * them here to control <code>TmplJTable</code> state.
   *
   * @param   btn   the button to be registered
   */
  public void registerBtnActionListener(JButton btn) {
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        executeCommand(e);
      }
    });

    this.controlButtons.add(btn);
  }

  /**
   * Actions from registered buttons in <code>registerBtnActionListener</code> will
   * be send to here. <code>executeCommand</code> will change the state of
   * <code>mode</mode> and perform the respective method.
   *
   * @param   e   action event generated from button
   */
  protected void executeCommand(ActionEvent e) {
    if (e.getActionCommand() == "INSERT")
      doInsert();
    if (e.getActionCommand() == "UPDATE")
      doUpdate();
    if (e.getActionCommand() == "DELETE")
      doDelete();
    if (e.getActionCommand() == "SAVE")
      doSave();
    if (e.getActionCommand() == "CANCEL")
      doCancel();
  }

  public void doInsert() {
    if (insertBefore()) {
      setMode(TmplFormModes.MODE_INSERT);
      ((TmplTableModel)dataModel).addRow(null);
      this.setRowSelectionInterval(((TmplTableModel)dataModel).getRowCount() - 1, ((TmplTableModel)dataModel).getRowCount() - 1);
    }
  }

  public void doUpdate() {
    if (updateBefore()) {
      if (getSelectedRow() >= 0) {
        setMode(TmplFormModes.MODE_UPDATE);
        ((TmplTableModel)dataModel).setRowMode(getSelectedRow());
        setRowSelectionInterval(getSelectedRow(), getSelectedRow());
      }
      else {
        JOptionPane.showMessageDialog(this,
                                      TmplResourceSingleton.getString("warning.dialog.msg.notSelected"),
                                      TmplResourceSingleton.getString("warning.dialog.header"),
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  public void doDelete() {
    if (deleteBefore() && deleteDoit()) {
      if (this.getSelectedRow() >= 0) {
        ((TmplTableModel)dataModel).setRowMode(getSelectedRow());
        this.setRowSelectionInterval(getSelectedRow(), getSelectedRow());
        try {
          ((TmplTableModel)dataModel).saveRow();
        }
        catch (TmplException tmplex) {
          JOptionPane.showMessageDialog(this,
                                        tmplex.getMessage(),
                                        TmplResourceSingleton.getString("error.dialog.header"),
                                        JOptionPane.ERROR_MESSAGE);
        }
        ((TmplTableModel)dataModel).populate(linkCondition, binds);
        deleteAfter(); ;
      }
      else {
        JOptionPane.showMessageDialog(this,
                                      TmplResourceSingleton.getString("warning.dialog.msg.notSelected"),
                                      TmplResourceSingleton.getString("warning.dialog.header"),
                                      JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  public void doSave() {
    switch (mode) {
      case TmplFormModes.MODE_INSERT:
        if (insertDoit()) {
          editingStopped(null);
          try {
            ((TmplTableModel)dataModel).saveRow();
          }
          catch (TmplException tmplex) {
            JOptionPane.showMessageDialog(this,
                                          tmplex.getMessage(),
                                          TmplResourceSingleton.getString("error.dialog.header"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
          }
          setMode(TmplFormModes.MODE_SELECT);
          ((TmplTableModel)dataModel).populate(linkCondition, binds);
          insertAfter();
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (updateDoit()) {
          editingStopped(null);
          try {
            ((TmplTableModel)dataModel).saveRow();
          }
          catch (TmplException tmplex) {
            JOptionPane.showMessageDialog(this,
                                          tmplex.getMessage(),
                                          TmplResourceSingleton.getString("error.dialog.header"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
          }
          setMode(TmplFormModes.MODE_SELECT);
          ((TmplTableModel)dataModel).populate(linkCondition, binds);
          updateAfter();
        }
        break;
    }
  }

  public void doCancel() {
    editingCanceled(null);
    setMode(TmplFormModes.MODE_SELECT);
    ((TmplTableModel)dataModel).populate(linkCondition, binds);
  }

  protected boolean insertBefore() {
    return true;
  }

  protected boolean insertDoit() {
    return ((TmplTableModel)dataModel).validateRow();
  }

  protected void insertAfter() {
  }

  protected boolean updateBefore() {
    return true;
  }

  protected boolean updateDoit() {
    return ((TmplTableModel)dataModel).validateRow();
  }

  protected void updateAfter() {
  }

  protected boolean deleteBefore() {
    return true;
  }

  protected boolean deleteDoit() {
    return true;
  }

  public void deleteAfter() {
  }

  protected void setMode(int mode) {
    this.mode = mode;

    switch (mode) {
      case TmplFormModes.MODE_SELECT:
        setModeForControlButtons("INSERT", true);
        setModeForControlButtons("UPDATE", true);
        setModeForControlButtons("DELETE", true);
        setModeForControlButtons("SAVE", false);
        setModeForControlButtons("CANCEL", false);
        break;
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_UPDATE:
        setModeForControlButtons("INSERT", false);
        setModeForControlButtons("UPDATE", false);
        setModeForControlButtons("DELETE", false);
        setModeForControlButtons("SAVE", true);
        setModeForControlButtons("CANCEL", true);
        break;
    }

    ((TmplTableModel)dataModel).setFormMode(mode);
  }

  private void setModeForControlButtons(String command, boolean enabled) {
    for (int i = 0; i < controlButtons.size(); i++) {
      JButton btn = (JButton)controlButtons.elementAt(i);
      if (btn.getActionCommand().equals(command)) {
        btn.setEnabled(enabled);
        break;
      }
    }
  }

  //
  // Methods from Template Interface
  //

  public void tmplInitialize(TemplateEvent e) {
    //set some look
    setAutoCreateColumnsFromModel(false);
    setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    getTableHeader().setFont(new Font("Dialog", 0, 10));

    //set column model
    setColumnModel(new TmplTableColumnModel());
    setModel(new TmplTableModel(url, headers, fields, staticLinkCondition, msgCode));

    //force model to refresh
    createTmplColumnsFromModel();

    //initialize model
    ((TmplTableModel)dataModel).initialize();
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_FIND:
        setModeForControlButtons("INSERT", false);
        setModeForControlButtons("UPDATE", false);
        setModeForControlButtons("DELETE", false);
        setModeForControlButtons("SAVE", false);
        setModeForControlButtons("CANCEL", false);
        setEnabled(false);
        ((TmplTableModel)dataModel).clearValues();
        break;
      case TmplFormModes.MODE_SELECT:
        setModeForControlButtons("INSERT", true);
        setModeForControlButtons("UPDATE", true);
        setModeForControlButtons("DELETE", true);
        setModeForControlButtons("SAVE", false);
        setModeForControlButtons("CANCEL", false);
        setEnabled(true);
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    this.setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    binds = (Vector<TplObject>)e.getBinds();
    String linkCondConverted = linkCondition;
    Vector<TplObject> newBinds = new Vector<TplObject>();

    if (bindsMap != null) {
      for (TplObject bindObj : binds) {
	for (int i = 0; i < bindsMap.length; i++) {
	  if (bindObj.getField().equals(bindsMap[i][0])) {
            TplObject newBindObj = null;
            if (bindObj instanceof TplString) {
	      newBindObj = new TplString(bindsMap[i][1], bindObj.getKeyType(), bindObj.isRequired());
              if (((TplString)bindObj).getValue() != null)
              ((TplString)newBindObj).setValue(new String(((TplString)bindObj).getValue()));
              newBinds.add(newBindObj);
	    }
	  }
          else
            newBinds.add(bindObj);
	}
      }
    }
    else
      newBinds = binds;

    ((TmplTableModel)dataModel).populate(linkCondConverted, newBinds);
  }

  public void tmplSave(TemplateEvent e) {
  }

  public boolean tmplRequired(TemplateEvent e) {
    return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
  }

  public JComponent tmplGetComponent() {
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
