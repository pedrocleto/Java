package pt.inescporto.siasoft.asq.web.forms;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javax.naming.NamingException;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplTextAreaRender;
import pt.inescporto.template.client.design.table.TmplTextAreaEditor;
import pt.inescporto.template.client.design.table.TmplTimestampEditor;
import pt.inescporto.template.client.design.table.TmplTimestampRenderer;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.design.TmplJDatePicker;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.table.TmplTimestampPickerEditor;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHistoryDao;
import pt.inescporto.siasoft.asq.ejb.session.RegulatoryHistory;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class RegulatoryHistoryPanel extends JPanel implements ActionListener {
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");

  JPanel jpnlContent = new JPanel();

  TmplJTextField jtfldRegulatoryDescription = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  TmplJComboBox jcbActionType = new TmplJComboBox();
  TmplLookupButton jlbtnActionRegId = new TmplLookupButton();
  TmplJTextField jtfldActionRegId = new TmplJTextField();
  TmplLookupField jtfldActionRegIdLookup = new TmplLookupField();
  TmplJDatePicker jtfldActionDate = new TmplJDatePicker();
  TmplJTextArea jtafldActionObs = new TmplJTextArea();
  TmplJButton jbtnAdd = new TmplJButton();

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblActiveRef = null;
  FW_NavBarDetail nbDetail = new FW_NavBarDetail() {
    public void setActionListener(ActionListener al) {
      super.setActionListener(al);
      remove(jbtnInsert);
    }
  };

  JScrollPane jScrollPane2 = new JScrollPane();
  FW_JTable jtblPassiveRef = null;

  public RegulatoryHistoryPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public RegulatoryHistoryPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //table listener registration
    nbDetail.setFW_ComponentListener(jtblActiveRef);
    nbDetail.setActionListener(jtblActiveRef);

    fwCListener.addFWComponentListener(jtfldRegulatoryDescription);
    fwCListener.addFWComponentListener(jtblActiveRef);
    fwCListener.addFWComponentListener(jtblPassiveRef);

    datasource.addDatasourceListener(jtfldRegulatoryDescription);
  }

  private void jbInit() throws Exception {
    jtfldRegulatoryDescription.setField("name");

    jcbActionType.setUrl("pt.inescporto.siasoft.asq.ejb.session.HistoricType");
    jcbActionType.setShowSave(new Integer[] {new Integer(1), new Integer(0)});
    jcbActionType.setWatcherSubject(SyncronizerSubjects.historicType);
    jcbActionType.tmplInitialize(new TemplateEvent(this));

    jlbtnActionRegId.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlbtnActionRegId.setText(res.getString("regHis.label.regulatory"));
    jlbtnActionRegId.setFillList(new JTextField[] {jtfldActionRegId});
    jlbtnActionRegId.tmplInitialize(new TemplateEvent(this));

    jtfldActionRegIdLookup.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jtfldActionRegIdLookup.setDefaultRefField(jtfldActionRegId);
    jtfldActionRegIdLookup.tmplInitialize(new TemplateEvent(this));

    jbtnAdd.setActionCommand("ADD");
    jbtnAdd.setText(res.getString("regHis.label.add"));
    jbtnAdd.addActionListener(this);

    FW_ColumnManager cmActiveRef = new FW_ColumnManager();
    TmplComboBoxEditor cbHist = new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.HistoricType", new Integer[] {new Integer(1), new Integer(0)}, null, null);
    cbHist.setWatcherSubject(SyncronizerSubjects.historicType);
    cmActiveRef.addColumnNode(new FW_ColumnNode(0,
						res.getString("regHis.label.action"),
						"actionType",
						new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.HistoricType"),
						cbHist));
    cmActiveRef.addColumnNode(new FW_ColumnNode(1,
						res.getString("regHis.label.regulatoryCode"),
						"regIdSon",
						new TmplStringRenderer(),
						new TmplStringEditor()));
    cmActiveRef.addColumnNode(new FW_ColumnNode(2,
						res.getString("regHis.label.obs"),
						"obs",
						new TmplTextAreaRender(),
						new TmplTextAreaEditor()));
    cmActiveRef.addColumnNode(new FW_ColumnNode(3,
						res.getString("regHis.label.date"),
						"histDate",
						new TmplTimestampRenderer(),
						new TmplTimestampPickerEditor()));
    jtblActiveRef = new FW_JTable(datasource.getDataSourceByName("HistActiveRef"), null, cmActiveRef);
    jtblActiveRef.setAsMaster(false);
    jtblActiveRef.setRowHeight(50);

    FW_ColumnManager cmPassiveRef = new FW_ColumnManager();
    cmPassiveRef.addColumnNode(new FW_ColumnNode(0,
						 res.getString("regHis.label.regulatoryCode"),
						 "regIdFather",
						 new TmplStringRenderer(),
						 new TmplStringEditor()));
    TmplComboBoxEditor cbHist2 = new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.HistoricType", new Integer[] {new Integer(1), new Integer(0)}, null, null);
    cbHist2.setWatcherSubject(SyncronizerSubjects.historicType);
    cmPassiveRef.addColumnNode(new FW_ColumnNode(1,
						 res.getString("regHis.label.action"),
						 "actionType",
						 new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.HistoricType"),
						 cbHist2));
    cmPassiveRef.addColumnNode(new FW_ColumnNode(2,
						 res.getString("regHis.label.obs"),
						 "obs",
						 new TmplTextAreaRender(),
						 new TmplTextAreaEditor()));
    cmPassiveRef.addColumnNode(new FW_ColumnNode(3,
						 res.getString("regHis.label.date"),
						 "histDate",
						 new TmplTimestampRenderer(),
						 new TmplTimestampEditor()));
    jtblPassiveRef = new FW_JTable(datasource.getDataSourceByName("HistPassiveRef"), null, cmPassiveRef);
    jtblPassiveRef.setAsMaster(false);
    jtblPassiveRef.setRowHeight(50);

    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, 70dlu, 4dlu, 70dlu, 4dlu, 100dlu:grow, right:pref, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, 30dlu:grow, 2dlu, 50dlu:grow, 2dlu, pref, 2dlu, 50dlu:grow, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2, 4, 6, 12}
    });
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jtfldRegulatoryDescription, cc.xyw(2, 2, 6));
    jpnlContent.add(jlbtnActionRegId, cc.xy(2, 4));
    jpnlContent.add(jtfldActionRegId, cc.xy(4, 4));
    jpnlContent.add(jtfldActionRegIdLookup, cc.xyw(6, 4, 2));
    jpnlContent.add(jcbActionType, cc.xy(2, 6));
    jpnlContent.add(jtfldActionDate, cc.xy(4, 6));
    jpnlContent.add(jbtnAdd, cc.xy(7, 6));
    jpnlContent.add(new JScrollPane(jtafldActionObs), cc.xyw(2, 8, 6, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(new JScrollPane(jtblActiveRef), cc.xyw(2, 10, 6, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(nbDetail, cc.xyw(2, 12, 6, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(new JScrollPane(jtblPassiveRef), cc.xyw(2, 14, 6, CellConstraints.FILL, CellConstraints.FILL));

    setLayout(new BorderLayout());
    add(jpnlContent, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("ADD")) {
      try {
	if (((RegulatoryDao)datasource.getCurrentRecord()).regId.getValue() == null) {
	  showErrorMessage(res.getString("regHis.label.message1"));
	}
	else {
	  if (((String)jcbActionType.getSelectedItem()).equals("")) {
	    showErrorMessage(res.getString("regHis.label.message2"));
	  }
	  else {
	    if (jtfldActionRegId.getText().equals("")) {
	      showErrorMessage(res.getString("regHis.label.message3"));
	    }
	    else {
	      RegulatoryHistoryDao attrs = new RegulatoryHistoryDao();
	      attrs.actionType.setValue((String)jcbActionType.getTrueSelectItem());
	      attrs.regIdFather.setValue(((RegulatoryDao)datasource.getCurrentRecord()).regId.getValue());
	      attrs.regIdSon.setValue(jtfldActionRegId.getText());
	      if (!jtfldActionDate.getText().equals("")) {
		try {
		  Date dt = new Date(dateFormat.parse(jtfldActionDate.getText()).getTime());
		}
		catch (ParseException ex) {
		}
		attrs.histDate.setValue(Timestamp.valueOf(jtfldActionDate.getText() + " 00:00:00"));
	      }
	      if (!jtafldActionObs.getText().equals("")) {
		attrs.obs.setValue(jtafldActionObs.getText());
	      }

	      try {
		RegulatoryHistory remote = (RegulatoryHistory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.RegulatoryHistory");
		remote.insert(attrs);
	      }
	      catch (UserException ex1) {
		ex1.printStackTrace();
	      }
	      catch (DupKeyException ex1) {
		ex1.printStackTrace();
	      }
	      catch (RemoteException ex1) {
		ex1.printStackTrace();
	      }
	      catch (NamingException ex1) {
		ex1.printStackTrace();
	      }
	      datasource.refresh();
	    }
	  }
	}
      }
      catch (DataSourceException ex2) {
	ex2.printStackTrace();
      }
    }
  }

  private void showErrorMessage(String msg) {
    JOptionPane.showMessageDialog(this,
				  msg, TmplResourceSingleton.getString("error.dialog.header"),
				  JOptionPane.ERROR_MESSAGE);
  }
}
