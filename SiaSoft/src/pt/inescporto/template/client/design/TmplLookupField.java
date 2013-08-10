package pt.inescporto.template.client.design;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.drmi.TmplRMILocater;

public class TmplLookupField extends JTextField implements DataSourceListener, TemplateComponentListener, TmplGetter {
  private int commType = 0;
  private Object remote = null;
  private boolean validValue = true;
  private String url;
  private TmplHttpMessageSender httpSender = null;
  private JTextField[] jtfRef = new JTextField[] {};
  private int index = 0;
  private String linkCondition = null;

  public TmplLookupField() {
    super();
    // set some look
    setEnabled(false);
    setOpaque(true);
    setFont(new Font("Dialog", Font.ITALIC, 10));
  }

  public TmplLookupField(String url, JTextField[] jtfRef) {
    this(url, jtfRef, 0);
  }

  public TmplLookupField(String url, JTextField[] jtfRef, int index) {
    this();

    this.index = index;
    this.url = url;
    this.jtfRef = jtfRef;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setDefaultRefField(JTextField ref) {
    jtfRef = new JTextField[] {
        ref};
  }

  public JTextField getDefaultRefField() {
    return jtfRef[0];
  }

  public void setRefFieldList(JTextField ref[]) {
    jtfRef = ref;
  }

  public JTextField[] getRefFieldList() {
    return jtfRef;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public void setLinkCondition(String linkCondition) {
    this.linkCondition = linkCondition;
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
	Method execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
	execMethod.invoke(remote, new Object[] {linkCondition, (linkCondition == null ? null : new Vector())});
      }
      catch (InvocationTargetException ex) {
        ex.printStackTrace();
      }
      catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
      catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
      catch (SecurityException ex) {
        ex.printStackTrace();
      }
      catch (NoSuchMethodException ex) {
        ex.printStackTrace();
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

  private void hook() {
    // hook changes to jtfRef
    for (int i = 0; i < this.jtfRef.length; i++) {
      if (this.jtfRef[i] != null) {
        jtfRef[i].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            valueChanged(e);
          }
        });

        jtfRef[i].addFocusListener(new FocusListener() {
          public void focusLost(FocusEvent l) {
            valueChanged(l);
          }

          public void focusGained(FocusEvent l) {
          }
        });
      }
    }
  }

  public void valueChanged(ActionEvent e) {
    for (int i = 0; i < jtfRef.length; i++) {
      if (jtfRef[i].getText().equals("")) {
        setText("");
        validValue = true;
        return;
      }
    }
    doGetDescription();
  }

  public void valueChanged(FocusEvent l) {
    for (int i = 0; i < jtfRef.length; i++) {
      if (jtfRef[i].getText().equals("")) {
        setText("");
        validValue = true;
        return;
      }
    }
    doGetDescription();
  }

  // send message get description
  protected void doGetDescription() {
    ArrayList ar = new ArrayList();
    String listDesc[] = null;

    for (int i = 0; i < jtfRef.length; i++)
      ar.add(jtfRef[i].getText());
    Object[] pkKeys = ar.toArray();

    setCursor(new Cursor(Cursor.WAIT_CURSOR));
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
      try {
        Method execMethod = remote.getClass().getMethod("lookupDesc", new Class[] {Object[].class});
        listDesc = (String[])execMethod.invoke(remote, new Object[] {pkKeys});
      }
      catch (InvocationTargetException ex1) {
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    if (listDesc == null) {
      setText("#########");
      validValue = false;
    }
    else {
      setText(listDesc[index]);
      validValue = true;
    }
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    String sLabel = "";

    for (int i = 0; i < jtfRef.length; i++) {
      if (jtfRef[i] != null && this.jtfRef[i] instanceof pt.inescporto.template.client.design.TmplJTextField) {
        ((TmplJTextField)jtfRef[i]).requestFocus();
        sLabel = sLabel + ((TmplJTextField)jtfRef[i]).getLabelName() + ", ";
      }
    }

    if (sLabel == "")
      sLabel = getClass().getName();
    else
      sLabel = sLabel.substring(0, sLabel.length() - 2);

    return sLabel;
  }

  //
  // Methods from TemplateListsner interface
  //

  public void tmplInitialize(TemplateEvent e) {
    connect();
    hook();
  }

  public void tmplMode(TemplateEvent e) {
    this.setEnabled(false);
    switch (e.getMode()) {
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_FIND:
        if (jtfRef[0] == null ||
            !(jtfRef[0] instanceof pt.inescporto.template.client.design.TmplJTextField) ||
            !(((TmplJTextField)jtfRef[0]).getLink().isLinkKey()))
          setText("");
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    return;
  }

  public void tmplSave(TemplateEvent e) {
    return;
  }

  public boolean tmplRequired(TemplateEvent e) {
    if (!validValue)
      e.setCompFailed(this);

    return validValue;
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
