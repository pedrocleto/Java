package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.drmi.TmplRMILocater;

public class TmplLookupRenderer extends JTextField implements TableCellRenderer {
  boolean isBordered = true;
  String url = null;
  int commType = 0;
  Object remote = null;
  boolean validValue = true;
  TmplHttpMessageSender httpSender = null;
  int index = 0;
  Object[] dataItems = null;
  Object[] dataValues = null;
  Integer[] pkKeys = null;

  public TmplLookupRenderer() {
    super();
    setFont(new Font("Dialog", Font.ITALIC, 10));
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public TmplLookupRenderer(Object[] dataItems, Object[] dataValues) {
    this();
    this.dataItems = dataItems;
    this.dataValues = dataValues;
  }

  public TmplLookupRenderer(String url, Integer[] pkKeys) {
    this();
    this.url = url;
    this.pkKeys = pkKeys;
    connect();
  }

  public TmplLookupRenderer(String url) {
    this();
    this.url = url;
    connect();
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row, int column) {
    if (isBordered) {
      if (isSelected) {
        Border selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
        setBackground(table.getSelectionBackground());
        setForeground(table.getSelectionForeground());
        setBorder(selectedBorder);
      }
      else {
        Border unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
        setBackground(table.getBackground());
        setForeground(table.getForeground());
        setBorder(unselectedBorder);
      }
    }

    if (pkKeys != null) {
      Vector keys = new Vector();
      for (int i = 0; i < pkKeys.length; i++) {
        keys.add(table.getValueAt(row, pkKeys[i].intValue()));
      }

      setText(getLookupValue(keys.toArray()));
    }
    else {
      if (value != null && !value.equals("")) {
        //System.out.println("TmplLookupRender value is <" + value.toString() + ">");
        setText(getLookupValue(value));
      }
      else {
        setText(null);
      }
    }

    return this;
  }

  private boolean connect() {
    if (url.startsWith("http")) {
      commType = 1;
      httpSender = new TmplHttpMessageSender(this.url);
    }
    else {
      commType = 2;
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
    }
    return true;
  }

  // send message get description
  private String getLookupValue(Object value) {
    String listDesc[] = null;
    Object[] pkKeys = getKeys(value);

    if (commType == 0) {
      for (int i = 0; i < dataValues.length; i++) {
        if (dataValues[i].equals(value)) {
          return (String)dataItems[i];
        }
      }
    }
    if (commType == 1) {
      try {
        TmplHttpMessage msg = new TmplHttpMessage(TmplMessages.MSG_GET_DESC, pkKeys);
        msg = (TmplHttpMessage)httpSender.doSendObject((Object)msg);
        listDesc = msg.getLookupDesc();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (commType == 2) {
      // test if any key has null value. If so return and set valid = true
      for (int i = 0; i < pkKeys.length; i++) {
        if (pkKeys[i] == null) {
          validValue = true;
          return null;
        }
      }
      try {
        Method execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
        execMethod.invoke(remote, new Object[] {null, null});

        execMethod = remote.getClass().getMethod("lookupDesc", new Class[] {Object[].class});
        listDesc = (String[])execMethod.invoke(remote, new Object[] {pkKeys});
      }
      catch (InvocationTargetException ex1) {
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    if (listDesc == null) {
      validValue = false;
      return ("#########");
    }
    else {
      validValue = true;
      return (listDesc[index]);
    }
  }

  public Object[] getKeys(Object value) {
    if (value instanceof Object[])
      return (Object[])value;
    else
      return new Object[] {value};
  }

  public void setIndex(int index) {
    this.index = index;
  }

  private void jbInit() throws Exception {
  }
}
