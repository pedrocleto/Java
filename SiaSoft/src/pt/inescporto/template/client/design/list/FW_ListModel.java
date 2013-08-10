package pt.inescporto.template.client.design.list;

import java.util.Collection;
import java.util.Vector;

import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.thirdparty.DefaultMutableListModel;
import pt.inescporto.template.elements.TplObject;
import java.lang.reflect.Field;

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
public class FW_ListModel extends DefaultMutableListModel implements DataSourceListener {
  private DataSource datasource = null;
  private Vector delegate = new Vector();
  private Vector<Integer> newRows = null;
  private Vector<Integer> editableRows = null;

  public FW_ListModel(DataSource datasource) {
    this.datasource = datasource;
    this.datasource.addDatasourceListener(this);
  }

  private void populate() {
    try {
      Collection all = datasource.listAll();
      delegate = new Vector(all);
      fireContentsChanged(this, 0, all.size()-1);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public void setDataVector(Vector delegate) {
    this.delegate = delegate;
    fireContentsChanged(this, 0, delegate.size() - 1);
  }

  public Object getAttrsAt(int row) {
    if (delegate != null && row <= delegate.size())
      return delegate.elementAt(row);
    return null;
  }

  public void addCleanRow() throws DataSourceException {
    datasource.cleanUpAttrs();
    delegate.add(datasource.getAttrs());
    newRows = new Vector<Integer>();
    newRows.add(new Integer(delegate.size() - 1));
    fireIntervalAdded(this, delegate.size() - 1, delegate.size() - 1);
  }

  /**
   * @todo Must return a list of rows
   */
  public Object saveRows() throws DataSourceException {
    if (newRows != null) {
      for (Integer newRow : newRows) {
        datasource.setAttrs(delegate.elementAt(newRow));
        datasource.insert();
        datasource.getCurrentRecord();
      }
      newRows = null;
      return datasource.getCurrentRecord();
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
        datasource.setAttrs(delegate.elementAt(row));
        datasource.update();
      }
      editableRows = null;
      return datasource.getCurrentRecord();
    }

    return null;
  }

  public void cancelRows() {
    if (newRows != null) {
      for (Integer newRow : newRows) {
        delegate.removeElementAt(newRow.intValue());
        fireIntervalRemoved(this, newRow.intValue(), newRow.intValue());
      }
      newRows = null;
    }

    if (editableRows != null) {
      /*      for (Integer row : editableRows) {
              datasource.setAttrs(delegate.elementAt(row));
              datasource.update();
            }*/
      editableRows = null;
    }
  }

  public Object deleteRow(int rowIndex) throws DataSourceException {
    Object attrs = delegate.elementAt(rowIndex);
    datasource.setAttrs(delegate.elementAt(rowIndex));
    datasource.delete();
    fireIntervalRemoved(this, rowIndex, rowIndex);
    return attrs;
  }

  public void letEditRow(int rowIndex) {
    if (editableRows == null)
      editableRows = new Vector<Integer>();

    editableRows.add(new Integer(rowIndex));
  }

  public Vector<Integer> getInsertingRows() {
    return newRows;
  }

  public boolean canSave() {
    if (newRows != null) {
      for (Integer row : newRows) {
        if (!isRowReadyForSave(delegate.elementAt(row)))
          return false;
      }
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
        if (!isRowReadyForSave(delegate.elementAt(row)))
          return false;
      }
    }

    return true;
  }

  /** @todo ONLY THE LINKED ATTRS TO COLUMNS MUST BE CHECKED!
   * CORRECT THIS ASAP - the best way is to delegate each type column's and make them test*/
  private boolean isRowReadyForSave(Object rowAttrs) {
    Field[] fld;

    fld = rowAttrs.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
        test = fld[i].get(rowAttrs);
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      if (test instanceof pt.inescporto.template.elements.TplObject) {
        TplObject obj = (TplObject)test;
        if (obj.isRequired() && !obj.isGenKey() && obj.getValueAsObject() == null)
          return false;
      }
    }

    return true;
  }

  public int getRowCount() {
    return delegate == null ? 0 : delegate.size();
  }

  public Object getElementAt(int index) {
    return delegate.elementAt(index);
  }

  public int getSize() {
    return delegate.size();
  }

  public boolean isCellEditable(int index) {
    if (newRows != null) {
      for (Integer newRow : newRows) {
        if (newRow.intValue() == index)
          return true;
      }
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
        if (row.intValue() == index) {
          return true;
        }
      }
    }

    return false;
  }

  public void setValueAt(Object value, int index) {
    if (value != null)
      delegate.setElementAt(value, index);
  }

  /**
   * Impementation of the <code>DataSourceListener<\code> Interface
   */
  public void tmplInitialize(TemplateEvent e) {
//    System.out.println("listmodel tmplInitialize");
    try {
      populate();
    }
    catch (Exception ex) {
    }
  }

  public void tmplRefresh(TemplateEvent e) {
//    System.out.println("listmodel tmplRefresh");
    populate();
  }

  public void tmplSave(TemplateEvent e) {
//    System.out.println("listmodel tmplSave");
  }

  public void tmplLink(TemplateEvent e) {
//    System.out.println("listmodel tmplLink");
  }
}
