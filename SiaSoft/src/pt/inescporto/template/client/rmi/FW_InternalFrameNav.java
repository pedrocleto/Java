package pt.inescporto.template.client.rmi;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import javax.swing.JFrame;

import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.design.TmplDlgLookup2;
import pt.inescporto.template.client.design.TmplGetter;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.client.design.FormRecordSelector;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.template.reports.config.ReportsConfigurationLoader;
import pt.inescporto.template.reports.jasper.JasperReportData;
import pt.inescporto.template.reports.forms.FormsReportConfigurationLoader;
import java.util.Vector;
import pt.inescporto.template.reports.forms.ReportSelectorForm;
import javax.swing.SwingUtilities;

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
public class FW_InternalFrameNav extends FW_InternalFrameBasic implements ActionListener, FormRecordSelector {
  protected int mode;
  protected String staticLinkCondition = null;
  protected Collection lastFind = null;
  protected DataSource dataSource = null;
  public String[] allHeaders = new String[] {};

  public FW_InternalFrameNav() {
    super();
  }

  public void setDataSource(DataSource ds) {
    dataSource = ds;
  }

  public void start() {
    super.start();

    try {
      if (dataSource != null) {
	dataSource.initialize();
	if (dataSource.first()) {
	  // set mode to VIEW
	  setMode(TmplFormModes.MODE_SELECT);
	}
	else {
	  doInsert();
	}
      }
      else
	System.err.println("No data source defined!");
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected void refresh() {
    try {
      dataSource.refresh();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Response to normal navegational commands
   *
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("INSERT"))
      doInsert();
    if (e.getActionCommand().equals("UPDATE"))
      doUpdate();
    if (e.getActionCommand().equals("DELETE"))
      doDelete();
    if (e.getActionCommand().equals("SAVE"))
      doSave();
    if (e.getActionCommand().equals("CANCEL"))
      doCancel();
    if (e.getActionCommand().equals("NEXT"))
      doNext();
    if (e.getActionCommand().equals("PREV"))
      doPrevious();
    if (e.getActionCommand().equals("ALL"))
      doAll();
    if (e.getActionCommand().equals("FIND"))
      doFind();
    if (e.getActionCommand().equals("FINDRES"))
      doFindRes();
    if (e.getActionCommand().equals("REPORT"))
      printReport();
    if (e.getActionCommand().equals("EXIT")) {
      dispose();
    }
  }

  /**
   * Change the current state of the form.
   *
   * @param iMode int
   *
   * @see TmplFormModes
   */
  protected void setMode(int iMode) {
    mode = iMode;
    fireTemplateMode();
  }

  protected boolean insertBefore() {
    return true;
  }

  /**
   * Prepare the form for insert mode
   */
  protected void doInsert() {
    setMode(TmplFormModes.MODE_INSERT);
    try {
      dataSource.cleanUpAttrs();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Called after success of inserting current record
   */
  protected void insertAfter() {
  }

  /**
   * Called before the frame enter in update mode!
   * @return boolean false stops update
   */
  protected boolean updateBefore() {
    return true;
  }

  protected void doUpdate() {
    setMode(TmplFormModes.MODE_UPDATE);
  }

  /**
   * Called after success of updating current record
   */
  protected void updateAfter() {
  }

  protected boolean deleteBefore() {
    return true;
  }

  protected void doDelete() {
    int errorCode;
    if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"),
			    TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {
      try {
	if (deleteBefore()) {
	  dataSource.delete();
	  deleteAfter();
	  if (subject != null)
	    EventSynchronizer.getInstance().triggerEvent(subject);
	}
      }
      catch (DataSourceException dsex) {
	if (dsex.getCause() instanceof ConstraintViolatedException)
	  showErrorMessage(TmplResourceSingleton.getString("error.msg.constraint"));
	else
	  if (dsex.getCause() instanceof RowNotFoundException) {
	    if (subject != null)
	      EventSynchronizer.getInstance().triggerEvent(subject);
	    doInsert();
	  }
	  else
	    showErrorMessage(dsex.getCause().getMessage());
      }
    }
  }

  protected void deleteAfter() {
  }

  protected boolean doSave() {
    switch (mode) {
      case TmplFormModes.MODE_INSERT:
	TmplGetter compFailure = fireTemplateRequired();
	if (compFailure != null) {
	  showErrorMessage(TmplResourceSingleton.getString("error.msg.required.field") + compFailure.getLabelName() + TmplResourceSingleton.getString("error.msg.required.end"));
	  return false;
	}
	try {
	  if (insertBefore()) {
	    dataSource.insert();
	    insertAfter();
	    if (subject != null)
	      EventSynchronizer.getInstance().triggerEvent(subject);
	  }
	  else
	    return false;
	}
	catch (DataSourceException dsex) {
	  if (dsex.getCause() instanceof DupKeyException)
	    showErrorMessage(TmplResourceSingleton.getString("error.msg.dupkey"));
	  else
	    showErrorMessage(dsex.getException().getMessage());
	  return false;
	}
	break
	    ;
      case TmplFormModes.MODE_UPDATE:
	try {
	  if (updateBefore()) {
	    dataSource.update();
	    updateAfter();
	    if (subject != null)
	      EventSynchronizer.getInstance().triggerEvent(subject);
	  }
	  else
	    return false;
	}
	catch (DataSourceException dsex) {
	  showErrorMessage(dsex.getMessage());
	  return false;
	}
	break
	    ;
      case TmplFormModes.MODE_FIND:
	try {
	  dataSource.find();
	}
	catch (DataSourceException dsex) {
	  if (dsex.getStatusCode() == dsex.NOT_FOUND) {
	    showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.notFound"));
	    setMode(TmplFormModes.MODE_SELECT);
	    return true;
	  }
	  else
	    showInformationMessage(dsex.getCause().getMessage());
	  return false;
	}
	break
	    ;
    }

    setMode(TmplFormModes.MODE_SELECT);
    return true;
  }

  protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      try {
	if (dataSource.first()) {
	  // set mode to VIEW
	  setMode(TmplFormModes.MODE_SELECT);
	}
	else {
	  doInsert();
	}
      }
      catch (DataSourceException dsex) {
	showErrorMessage(dsex.getMessage());
      }

    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
  }

  protected void doNext() {
    try {
      dataSource.next();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.LAST_RECORD)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
      else
	showErrorMessage(dsex.getException().getMessage());
    }
  }

  protected void doPrevious() {
    try {
      dataSource.previous();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.FIRST_RECORD)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
      else
	showErrorMessage(dsex.getException().getMessage());
    }
  }

  protected void doAll() {
    Component parent = getParent();
    while (parent != null && !(parent instanceof JFrame))
      parent = parent.getParent();
    TmplDlgLookup2 dlgAll = new TmplDlgLookup2((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("dialog.header.list"), allHeaders, new AllDSFilter(dataSource));
  }

  protected void doFind() {
    setMode(TmplFormModes.MODE_FIND);
  }

  protected void doFindRes() {
    try {
      if (dataSource.getLastFind() != null) {
	Component parent = getParent();
	while (parent != null && !(parent instanceof JFrame))
	  parent = parent.getParent();
	TmplDlgLookup2 dlgAll = new TmplDlgLookup2((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("dialog.header.list"), allHeaders, new LastFindDSFilter(dataSource));
      }
      else {
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.resNotFound"));
      }
    }
    catch (IllegalArgumentException ex) {
    }
    catch (DataSourceException ex) {
    }
  }

  public void printReport() {
    try {
      Object repObj = ReportsConfigurationLoader.getReportData();

      if (repObj instanceof JasperReportData) {
	JasperReportData jrData = (JasperReportData)repObj;
//        java.net.URL repFile = pt.inescporto.wcontrol.common.reports.ReportsAnchor.class.getResource("jasper/fp_warehouse.jasper");

	Vector reportsList = FormsReportConfigurationLoader.getReportMapping(this.getClass().getName());

	ReportSelectorForm rsForm = new ReportSelectorForm(SwingUtilities.getWindowAncestor(this),
	    dataSource.listAll(), reportsList, jrData.parameters);

	if (reportsList.size() == 0) {
	  //There are no reports for this form
	  showInformationMessage("Não existem relatórios");
return;
	}
	if (reportsList.size() == 1) {
	  //Show this report immediatly
	  rsForm.selectFirstReport();
	  rsForm.open_report();
	  rsForm.dispose();
	  rsForm = null;
	}
	else {
	  //user must choose the report manually
	  rsForm.setVisible(true);
	}
      }

    }
    catch (Exception ex) {
      showErrorMessage("Não consigo criar o relatório");
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> mode events.
   * Creates the event based on current <code>mode</code>.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateMode() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
	TemplateEvent templateEvent = new TemplateEvent(this);
	templateEvent.setMode(mode);
	((TemplateComponentListener)listeners[i + 1]).tmplMode(templateEvent);
      }
    }
  }

  public boolean setPrimaryKey(Object keyValue) {
    return keyValue == null;
  }
}
