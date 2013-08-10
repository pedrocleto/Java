package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.lang.reflect.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.utils.AttrsToVector;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.drmi.TmplRMILocater;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.template.client.event.EventSynchronizerWatcherRemover;

public class TmplComboBoxEditor extends JComboBox implements TableCellEditor, CellEditorListener, EventSynchronizerWatcher, EventSynchronizerWatcherRemover {
  protected transient Vector listeners = new Vector();
  protected transient Object originalValue;

  private String subject = null;
  private int commType = 0;
  private Object remote = null;
  protected Collection all;
  protected String url = null;
  private TmplHttpMessageSender httpSender = null;
  protected Object[] dataItems = null;
  protected Object[] dataValues = null;
  protected Integer[] showSave = null;
  protected String linkCondition = null;
  protected Vector binds = null;

  public TmplComboBoxEditor() {
    super();
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplComboBoxEditor(Object[] dataItems, Object[] dataValues) {
    super(dataItems);
    setFont(new Font("Dialog", Font.PLAIN, 10));
    this.dataItems = dataItems;
    this.dataValues = dataValues;
  }

  public TmplComboBoxEditor(String url, Integer[] showSave) {
    this(url, showSave, null, null);
  }

  public TmplComboBoxEditor(String url, Integer[] showSave, String linkCondition, Vector binds) {
    this();
    setUrl(url);
    this.showSave = showSave;
    this.httpSender = new TmplHttpMessageSender(this.url);
    this.linkCondition = linkCondition;
    this.binds = binds;
    connect();
    populate();
  }

  public void setWatcherSubject(String subject) {
    eventSynchronizerTriggered(this.subject);
    this.subject = subject;
    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value != null) {
      setSelection(value);
      originalValue = value;
      table.setRowSelectionInterval(row, row);
      table.setColumnSelectionInterval(column, column);
    }
    else
      setSelectedIndex(0);
    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    if (url == null)
      return this.dataValues[this.getSelectedIndex()];
    else {
      if (this.getSelectedIndex() == 0)
        return null;
      else {
        Vector v = (Vector)all.toArray()[this.getSelectedIndex() - 1];
        return v.elementAt(this.showSave[1].intValue());
      }
    }
  }

  public boolean isCellEditable(EventObject eo) {
    return true;
  }

  public boolean shouldSelectCell(EventObject eo) {
    return true;
  }

  public boolean stopCellEditing() {
    fireEditingStopped();
    return true;
  }

  public void addCellEditorListener(CellEditorListener cel) {
    listeners.addElement(cel);
  }

  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement(cel);
  }

  protected void fireEditingCanceled() {
    setSelection(originalValue);
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size(); i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
    }
  }

  protected void fireEditingStopped() {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
    }
  }

  // url
  public void setUrl(String url) {
    this.url = url;
    if (url.startsWith("http"))
      commType = 1;
    else
      commType = 2;
  }

  private boolean connect() {
    switch (commType) {
      case 1:
        httpSender = new TmplHttpMessageSender(url);
        break;
      case 2:
        try {
          remote = TmplEJBLocater.getInstance().getEJBRemote(url);
        }
        catch (Exception ex) {
          remote = TmplRMILocater.getInstance().getRMIHome(url);
          if (remote == null) {
            ex.printStackTrace();
            return false;
          }
        }
        break;
    }
    return true;
  }

  protected void populate() {
    removeAllItems();
    try {
      switch (commType) {
        case 1:
          TmplHttpMessage msg = new TmplHttpMessage(TmplMessages.MSG_LISTALL);
          if (linkCondition != null && binds != null) {
            msg.setLinkCondition(linkCondition);
            msg.setBinds(binds);
            msg.setAsMaster(false);
          }
          msg = (TmplHttpMessage)httpSender.doSendObject((Object)msg);
          all = AttrsToVector.ListConvert(msg.getAll());
          break;
        case 2:
          Method execMethod = null;

          if (linkCondition != null && binds != null) {
            execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
            execMethod.invoke(remote, new Object[] {linkCondition, binds});
          }

          execMethod = remote.getClass().getMethod("listAll");
          java.util.Collection list = (java.util.Collection)execMethod.invoke(remote);
          all = AttrsToVector.ListConvert(list);
          break;
      }

      Iterator it = all.iterator();
      addItem("");
      while (it.hasNext()) {
        Object test = ((Vector)it.next()).elementAt(showSave[0].intValue());
        addItem(test);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setSelection(Object value) {
    boolean found = false;
    if (url == null) {
      int i;
      for (i = 0; i < this.dataValues.length && !found; i++) {
        if (this.dataValues[i].equals(value))
          found = true;
      }
      if (found)
        this.setSelectedItem(this.dataItems[i - 1]);
      else
        this.setSelectedIndex(0);
    }
    else {
      int i;
      Object[] list = all.toArray();
      for (i = 0; i < list.length && !found; i++) {
        Vector v = (Vector)list[i];
        if (v.elementAt(this.showSave[1].intValue()).equals(value))
          found = true;
      }
      if (found)
        this.setSelectedItem(((Vector)list[i - 1]).elementAt(this.showSave[0].intValue()));
      else
        this.setSelectedIndex(0);
    }
  }

  /**
   * ***************************************************************************
   *                  Implementation of the CellEditorListener
   * ***************************************************************************
   */

  /**
   *
   * @param e ChangeEvent
   */
  public void editingCanceled(ChangeEvent e) {
    e = e;
  }

  public void editingStopped(ChangeEvent e) {
    String pkValue = (String)((CellEditor)e.getSource()).getCellEditorValue();
    binds = new Vector();
    binds.add(new TplString(pkValue));
    setSelectedIndex(0);
    populate();
  }

  /**
   * ***************************************************************************
   *                  Implementation of the EventSynchronizerWatcher
   * ***************************************************************************
   */
  public void eventSynchronizerTriggered(String subject) {
    if (this.subject != null && this.subject.equals(subject))
      populate();
  }

  /**
   * ***************************************************************************
   *               Implementation of the EventSynchronizerWatcherRemover
   * ***************************************************************************
   */
  public void removeEventSynchronizer() {
    if (subject != null) {
      EventSynchronizer.getInstance().removeEventSynchronizerWatcher(this, subject);
    }
  }
}
