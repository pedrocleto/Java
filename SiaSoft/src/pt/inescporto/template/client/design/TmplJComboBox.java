package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.lang.reflect.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.utils.AttrsToVector;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;

public class TmplJComboBox extends JComboBox implements DataSourceListener, TemplateComponentListener, TmplGetter, EventSynchronizerWatcher {
  protected String subject = null;

  private int commType = 0;
  private Object remote = null;
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = null;
  protected Collection all;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  protected String url = null;
  private TmplHttpMessageSender httpSender = null;
  protected Object[] dataItems = null;
  protected Object[] dataValues = null;
  protected Integer[] showSave = null;
  protected String linkCondition = null;

  public TmplJComboBox() {
    super();
    commType = 0;
    setOpaque(false);
    setOpaque(true);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplJComboBox(JLabel jlblHolder, String field, Object[] dataItems, Object[] dataValues) {
    super(dataItems);
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.dataItems = dataItems;
    this.dataValues = dataValues;
    setOpaque(false);
    setOpaque(true);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplJComboBox(JLabel jlblHolder, String field, String url, Integer[] showSave) {
    super();
    this.jlblHolder = jlblHolder;
    this.field = field;
    setUrl(url);
    this.showSave = showSave;
    setOpaque(false);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplJComboBox(String label, String field, Object[] dataItems, Object[] dataValues) {
    super(dataItems);
    this.label = label;
    this.field = field;
    this.dataItems = dataItems;
    this.dataValues = dataValues;
    setOpaque(false);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplJComboBox(String label, String field, String url, Integer[] showSave) {
    super();
    this.label = label;
    this.field = field;
    setUrl(url);
    this.showSave = showSave;
    this.httpSender = new TmplHttpMessageSender(this.url);
    setOpaque(false);
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  // url
  public void setUrl(String url) {
    this.url = url;
    if (url.startsWith("http"))
      commType = 1;
    else
      commType = 2;
  }

  public String getUrl() {
    return url;
  }

  // link condition
  public void setLinkCondition (String linkCondition) {
    this.linkCondition = linkCondition;
  }

  //holder
  public void setHolder(JLabel holder) {
    jlblHolder = holder;
  }

  public JLabel getHolder() {
    return jlblHolder;
  }

  //label
  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  //field
  public void setField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  //data items
  public void setDataItems(Object[] dataItems) {
    this.dataItems = dataItems;
    setModel(new DefaultComboBoxModel(dataItems));
  }

  public Object[] getDataItems() {
    return dataItems;
  }

  //data values
  public void setDataValues(Object[] dataValues) {
    this.dataValues = dataValues;
  }

  public Object getDataValues() {
    return dataValues;
  }

  //show save
  public void setShowSave(Integer[] showSave) {
    this.showSave = showSave;
  }

  public Integer[] getShowSave() {
    return showSave;
  }

  public void setWatcherSubject(String subject) {
    this.subject = subject;
  }

  public Object getTrueSelectItem() {
    if (url == null) {
        return this.dataValues[this.getSelectedIndex()];
    }
    else {
      if (this.getSelectedIndex() == 0) {
        return null;
      }
      else {
        Vector v = (Vector)all.toArray()[this.getSelectedIndex() - 1];
          return v.elementAt(this.showSave[1].intValue());
      }
    }
  }

  private boolean connect() {
    switch (commType) {
      case 1:
        httpSender = new TmplHttpMessageSender(url);
        break;
      case 2:
        try {
          remote = TmplEJBLocater.getInstance().getEJBRemote(url);
          Method execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
          execMethod.invoke(remote, new Object[] {linkCondition, (linkCondition == null ? null : new Vector())});
        }
        catch (Exception ex) {
          ex.printStackTrace();
          return false;
        }
        break;
    }
    return true;
  }

  public void setUp() {
    addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        valueChanged(e);
      }
    });
  }

  public void valueChanged(ItemEvent e) {
  }

  protected void populate() {
    ((DefaultComboBoxModel)getModel()).removeAllElements();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));

    try {
      switch (commType) {
        case 1:
          TmplHttpMessage msg = new TmplHttpMessage(TmplMessages.MSG_LISTALL);
          msg = (TmplHttpMessage)httpSender.doSendObject((Object)msg);
          all = AttrsToVector.ListConvert(msg.getAll());
          break;
        case 2:
          Method execMethod = remote.getClass().getMethod("listAll");
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
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); ;
  }

  public void setLink(TplObject link) {
    this.link = link;

    if (this.jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return this.link;
  }

  public void eventSynchronizerTriggered(String subject) {
    populate();
    if (isEditable()) {
      invalidate();
      repaint();
    }
  }

  public String getLabelName() {
    if (this.jlblHolder != null)
      return this.jlblHolder.getText();
    else
      return label;
  }

  public void tmplInitialize(TemplateEvent e) {
    if (commType != 0) {
      connect();
      populate();
    }

    if (subject != null)
      EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        this.setEnabled(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if (this.link != null && (int)(this.fieldPerm & TmplPerms.PERM_INSERT) != 0) {
          if ((this.link.isLinkKey() && this.link.isPrimaryKey()) || this.link.isGenKey())
            this.setEnabled(false);
          else {
            this.setEnabled(true);
            this.setSelectedIndex(0);
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (this.link != null && !this.link.isPrimaryKey() && (int)(this.fieldPerm & TmplPerms.PERM_CHANGE) != 0)
          this.setEnabled(true);
        break;
      case TmplFormModes.MODE_FIND:
        this.setEnabled(true);
        if (this.link != null)
          this.link.resetValue();
        this.setSelectedIndex(0);
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    this.setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    if (link != null) {
      boolean found = false;
      if (url == null) {
        int i;
        for (i = 0; i < dataValues.length && !found; i++) {
          try {
            if (dataValues[i].equals(link.getValueAsObject()))
              found = true;
          }
          catch (Exception ex) {
          }
        }
        if (found)
          setSelectedItem(this.dataItems[i - 1]);
        else
          setSelectedIndex(0);
      }
      else {
        int i;
        Object[] list = all.toArray();
        for (i = 0; i < list.length && !found; i++) {
          Vector v = (Vector)list[i];
          if (v.elementAt(this.showSave[1].intValue()).equals((link.getValueAsObject())))
            found = true;
        }
        if (found)
          this.setSelectedItem(((Vector)list[i - 1]).elementAt(this.showSave[0].intValue()));
        else
          this.setSelectedIndex(0);
      }
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (this.link != null) {
      if (url == null) {
	link.setValueAsObject(dataValues[getSelectedIndex()]);
      }
      else {
        if (this.getSelectedIndex() == 0) {
          link.resetValue();
        }
        else {
          Vector v = (Vector)all.toArray()[this.getSelectedIndex() - 1];
	  link.setValueAsObject(v.elementAt(this.showSave[1].intValue()));
        }
      }
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    if (this.link != null && this.link.isRequired() && this.getSelectedIndex() == 0) {
      e.setCompFailed(this);
      return false;
    }
    else
      return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerm = e.getPermissionForField(fieldPermName);
  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equals(field))
      link = e.getLink();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public Object getValue(int i) {
    return dataValues[i];
  }

  public void finalize() throws Throwable {
    if (subject != null)
      EventSynchronizer.getInstance().removeEventSynchronizerWatcher(this, subject);
    super.finalize();
  }

  public void tmplDispose(TemplateEvent e) {
    try {
      finalize();
    }
    catch (Throwable ex) {
    }
  }
}
