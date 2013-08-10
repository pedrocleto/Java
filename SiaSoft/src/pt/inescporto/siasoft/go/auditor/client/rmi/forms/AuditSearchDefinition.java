package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.thirdparty.ComponentTitledBorder;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import java.awt.Color;
import java.sql.Timestamp;
import pt.inescporto.template.client.design.TmplJDatePicker;

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
public class AuditSearchDefinition extends JPanel {

  JPanel jpnlDateSelector = null;
  JPanel jpnlStateSelector = null;
  JPanel jpnlEnterpriseCellSelector = null;
  JPanel jpnlActivitySelector = null;
  JPanel jpnlAuditPlanSelector = null;
  JPanel jpnlAuditTypeSelector = null;

  AuditSearchDataSource asDataSource = new AuditSearchDataSource();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJCheckBox jchbDate = new TmplJCheckBox();

  TmplJLabel jlblAuditDate = new TmplJLabel();
  TmplJDatePicker jtfldAuditDate = new TmplJDatePicker();

  TmplJLabel jlblAuditEndDate = new TmplJLabel();
  TmplJDatePicker jtfldAuditEndDate = new TmplJDatePicker();

  TmplJCheckBox jchbState = new TmplJCheckBox();

  TmplJLabel jlblState = new TmplJLabel();
  TmplJComboBox jcmbState = new TmplJComboBox();

  TmplJCheckBox jchbEnterpriseCell = new TmplJCheckBox();

  TmplLookupButton jlbtnEnterpriseCell = new TmplLookupButton();
  TmplJTextField jtfldEnterpriseCell = new TmplJTextField();
  TmplLookupField jlfldEnterpriseCell = new TmplLookupField();

  TmplJCheckBox jchbActivity = new TmplJCheckBox();

  TmplLookupButton jlbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivity = new TmplJTextField();
  TmplLookupField jlfldActivity = new TmplLookupField();

  TmplJCheckBox jchbAuditPlan = new TmplJCheckBox();

  TmplLookupButton jlbtnAuditPlan = new TmplLookupButton();
  TmplJTextField jtfldAuditPlan = new TmplJTextField();
  TmplLookupField jlfldAuditPlan = new TmplLookupField();

  TmplJCheckBox jchAuditType = new TmplJCheckBox();

  TmplJLabel jlblAuditType = new TmplJLabel();
  TmplJComboBox jcmbAuditType = new TmplJComboBox();

  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();

  public AuditSearchDefinition() {
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    //AuditDate
    jlblAuditDate.setText(res.getString("asDefinition.label.auditDate"));
    jtfldAuditDate.setField("auditDate");
    jtfldAuditDate.setHolder(jlblAuditDate);

    // AuditEndDate
    jlblAuditEndDate.setText(res.getString("asDefinition.label.auditEndDate"));
    jtfldAuditEndDate.setField("auditEndDate");
    jtfldAuditEndDate.setHolder(jlblAuditEndDate);

    // State
    jlblState.setText(res.getString("asDefinition.label.state"));

    jcmbState.setField("status");
    jcmbState.setDataItems(new Object[] {"", res.getString("asDefinition.label.waiting"), res.getString("asDefinition.label.running"), res.getString("asDefinition.label.delayed"),
			   res.getString("asDefinition.label.ended"), res.getString("asDefinition.label.schedule")});
    jcmbState.setDataValues(new Object[] {null, "W", "R", "D", "E", "S"});

    // EnterpriseCell
    jtfldEnterpriseCell.setLabel(res.getString("asDefinition.label.enterpriseCell"));
    jtfldEnterpriseCell.setField("fkEnterpriseCellID");

    jlbtnEnterpriseCell.setText(res.getString("asDefinition.label.enterpriseCell"));
    jlbtnEnterpriseCell.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    if (!MenuSingleton.isSupplier())
      jlbtnEnterpriseCell.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterpriseCell.setTitle(res.getString("asDefinition.label.enterpriseCellList"));
    jlbtnEnterpriseCell.setDefaultFill(jtfldEnterpriseCell);

    jlfldEnterpriseCell.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    if (!MenuSingleton.isSupplier())
      jlfldEnterpriseCell.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldEnterpriseCell.setDefaultRefField(jtfldEnterpriseCell);
    jlfldEnterpriseCell.tmplInitialize(new TemplateEvent(this));

    // Activity
    jtfldActivity.setLabel(res.getString("asDefinition.label.activity"));
    jtfldActivity.setField("fkActivityID");

    jlbtnActivity.setText(res.getString("asDefinition.label.activity"));
    jlbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivity.setTitle(res.getString("asDefinition.label.activityList"));
    jlbtnActivity.setDefaultFill(jtfldActivity);

    jlfldActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlfldActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldActivity.setDefaultRefField(jtfldActivity);
    jlfldActivity.tmplInitialize(new TemplateEvent(this));

    //AuditPlan
    jtfldAuditPlan.setLabel(res.getString("asDefinition.label.auditPlan"));
    jtfldAuditPlan.setField("auditPlanID");

    jlbtnAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnAuditPlan.setText(res.getString("asDefinition.label.auditPlan"));
    jlbtnAuditPlan.setTitle(res.getString("asDefinition.label.auditPlan"));
    jlbtnAuditPlan.setDefaultFill(jtfldAuditPlan);

    jlfldAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlfldAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldAuditPlan.setDefaultRefField(jtfldAuditPlan);
    jlfldAuditPlan.tmplInitialize(new TemplateEvent(this));

    // AuditType
    jlblAuditType.setText(res.getString("asDefinition.label.type"));
    jcmbAuditType.setField("auditType");
    jcmbAuditType.setDataItems(new Object[] {"", "Interna", "Externa"});
    jcmbAuditType.setDataValues(new Object[] {null, "I", "E"});

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    FormLayout fm = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				   "5px, pref, 5px");

    CellConstraints cc = new CellConstraints();

    final JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    jpnlDateSelector = new JPanel();
    jpnlDateSelector.setOpaque(false);
    jpnlDateSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyDate = new JPanel(fm);
    jpnlDummyDate.setOpaque(false);
    jpnlDummyDate.setVisible(false);

    jpnlStateSelector = new JPanel();
    jpnlStateSelector.setOpaque(false);
    jpnlStateSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyState = new JPanel(fm);
    jpnlDummyState.setOpaque(false);
    jpnlDummyState.setVisible(false);

    jpnlEnterpriseCellSelector = new JPanel();
    jpnlEnterpriseCellSelector.setOpaque(false);
    jpnlEnterpriseCellSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyEntCell = new JPanel(fm);
    jpnlDummyEntCell.setOpaque(false);
    jpnlDummyEntCell.setVisible(false);

    jpnlActivitySelector = new JPanel();
    jpnlActivitySelector.setOpaque(false);
    jpnlActivitySelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyAct = new JPanel(fm);
    jpnlDummyAct.setOpaque(false);
    jpnlDummyAct.setVisible(false);

    jpnlAuditPlanSelector = new JPanel();
    jpnlAuditPlanSelector.setOpaque(false);
    jpnlAuditPlanSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyPlan = new JPanel(fm);
    jpnlDummyPlan.setOpaque(false);
    jpnlDummyPlan.setVisible(false);

    jpnlAuditTypeSelector = new JPanel();
    jpnlAuditTypeSelector.setOpaque(false);
    jpnlAuditTypeSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyType = new JPanel(fm);
    jpnlDummyType.setOpaque(false);
    jpnlDummyType.setVisible(false);

    jpnlDummyDate.add(jlblAuditDate, cc.xy(2, 2));
    jpnlDummyDate.add(jtfldAuditDate, cc.xy(4, 2));
    jlblAuditDate.setEnabled(false);
    jtfldAuditDate.setEnabled(false);

    jpnlDummyDate.add(jlblAuditEndDate, cc.xy(6, 2));
    jlblAuditEndDate.setEnabled(false);
    jpnlDummyDate.add(jtfldAuditEndDate, cc.xy(8, 2));
    jtfldAuditEndDate.setEnabled(false);

    jpnlDummyState.add(jlblState, cc.xy(2, 2));
    jlblState.setEnabled(false);
    jpnlDummyState.add(jcmbState, cc.xy(4, 2));
    jcmbState.setEnabled(false);

    jpnlDummyEntCell.add(jlbtnEnterpriseCell, cc.xy(2, 2));
    jlbtnEnterpriseCell.setEnabled(false);
    jpnlDummyEntCell.add(jtfldEnterpriseCell, cc.xy(4, 2));
    jtfldAuditEndDate.setEnabled(false);
    jpnlDummyEntCell.add(jlfldEnterpriseCell, cc.xyw(6, 2, 3));
    jlfldEnterpriseCell.setEnabled(false);

    jpnlDummyAct.add(jlbtnActivity, cc.xy(2, 2));
    jlbtnActivity.setEnabled(false);
    jpnlDummyAct.add(jtfldActivity, cc.xy(4, 2));
    jtfldActivity.setEnabled(false);
    jpnlDummyAct.add(jlfldActivity, cc.xyw(6, 2, 3));
    jlfldActivity.setEnabled(false);

    jpnlDummyPlan.add(jlbtnAuditPlan, cc.xy(2, 2));
    jlbtnAuditPlan.setEnabled(false);
    jpnlDummyPlan.add(jtfldAuditPlan, cc.xy(4, 2));
    jtfldAuditPlan.setEnabled(false);
    jpnlDummyPlan.add(jlfldAuditPlan, cc.xyw(6, 2, 3));
    jlfldAuditPlan.setEnabled(false);

    jpnlDummyType.add(jlblAuditType, cc.xy(2, 2));
    jlblAuditType.setEnabled(false);
    jpnlDummyType.add(jcmbAuditType, cc.xy(4, 2));
    jcmbAuditType.setEnabled(false);

    // Date
    jchbDate.setText(res.getString("asDefinition.label.date"));
    jchbDate.setBackground(new Color(200,221,244));
    jchbDate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyDate.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);

	  jpnlDummyDate.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
          jtfldAuditDate.setSelectedDate(null);
            jtfldAuditEndDate.setSelectedDate(null);
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);

	  jpnlDummyDate.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    jtfldAuditDate.setSelectedDate(null);
    jtfldAuditEndDate.setSelectedDate(null);

    ComponentTitledBorder borderDate = new ComponentTitledBorder(jchbDate, jpnlDateSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlDateSelector.setBorder(borderDate);
    jpnlDateSelector.add(jpnlDummyDate, BorderLayout.NORTH);

    //State
    jchbState.setText(res.getString("asDefinition.label.state"));
    jchbState.setBackground(new Color(200,221,244));
    jchbState.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyState.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);

	  jpnlDummyState.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jcmbState.setSelectedIndex(0);
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);

	  jpnlDummyState.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderState = new ComponentTitledBorder(jchbState, jpnlStateSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlStateSelector.setBorder(borderState);
    jpnlStateSelector.add(jpnlDummyState, BorderLayout.NORTH);

    // Activity
    jchbActivity.setText(res.getString("asDefinition.label.activity"));
    jchbActivity.setBackground(new Color(200,221,244));
    jchbActivity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyAct.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  jchbEnterpriseCell.setEnabled(false);
	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(true);

	  jpnlDummyAct.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jchbEnterpriseCell.setEnabled(true);
	  jtfldActivity.setText("");
	  jlfldActivity.setText("");
	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(false);

	  jpnlDummyAct.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });

    ComponentTitledBorder borderActivity = new ComponentTitledBorder(jchbActivity, jpnlActivitySelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlActivitySelector.setBorder(borderActivity);
    jpnlActivitySelector.add(jpnlDummyAct, BorderLayout.NORTH);

    jchbEnterpriseCell.setText(res.getString("asDefinition.label.enterpriseCell"));
    jchbEnterpriseCell.setBackground(new Color(200,221,244));
    jchbEnterpriseCell.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyEntCell.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  jchbActivity.setEnabled(false);

	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(true);

	  jpnlDummyEntCell.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jchbActivity.setEnabled(true);
	  jtfldEnterpriseCell.setText("");
	  jlfldEnterpriseCell.setText("");
	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(false);

	  jpnlDummyEntCell.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderEnterpriseCell = new ComponentTitledBorder(jchbEnterpriseCell, jpnlEnterpriseCellSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlEnterpriseCellSelector.setBorder(borderEnterpriseCell);
    jpnlEnterpriseCellSelector.add(jpnlDummyEntCell, BorderLayout.NORTH);

    // AuditPlan
    jchbAuditPlan.setText(res.getString("asDefinition.label.auditPlan"));
    jchbAuditPlan.setBackground(new Color(200,221,244));
    jchbAuditPlan.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyPlan.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(true);

	  jpnlDummyPlan.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jtfldAuditPlan.setText("");
	  jlfldAuditPlan.setText("");
	  asDataSource.setUp(false);
	  for (int i = 0; i < (component.length - 1); i++)
	    component[i].setEnabled(false);

	  jpnlDummyPlan.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });

    ComponentTitledBorder borderPlan = new ComponentTitledBorder(jchbAuditPlan, jpnlAuditPlanSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlAuditPlanSelector.setBorder(borderPlan);
    jpnlAuditPlanSelector.add(jpnlDummyPlan, BorderLayout.NORTH);

    // AuditType
    jchAuditType.setText(res.getString("asDefinition.label.type"));
    jchAuditType.setBackground(new Color(200,221,244));
    jchAuditType.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyType.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);

	  jpnlDummyType.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jcmbAuditType.setSelectedIndex(0);
	  asDataSource.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);

	  jpnlDummyType.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderType = new ComponentTitledBorder(jchAuditType, jpnlAuditTypeSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlAuditTypeSelector.setBorder(borderType);
    jpnlAuditTypeSelector.add(jpnlDummyType, BorderLayout.NORTH);

    jbtnUpdate.setText(res.getString("label.update"));

    jbtnUpdate.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
	asDataSource.setNullValues(false);
        if ((!jtfldAuditDate.getText().equals(" - - ") && !jtfldAuditEndDate.getText().equals(" - - "))) {
	  if ((jtfldAuditEndDate.getSelectedDate(true).before(jtfldAuditDate.getSelectedDate(true))) || (jtfldAuditEndDate.getSelectedDate(true).equals(jtfldAuditDate.getSelectedDate(true)))) {
            jtfldAuditDate.setSelectedDate(null);
            jtfldAuditEndDate.setSelectedDate(null);
	    JFrame frame = new JFrame();
	    JOptionPane.showMessageDialog(frame,
                                          res.getString("label.invalidDate"),
					  res.getString("label.warning"),
					  JOptionPane.WARNING_MESSAGE);

	    jtfldAuditDate.requestFocus();
	    asDataSource.setUp(false);
	  }
	  else {
	    try {
              asDataSource.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                               jtfldAuditDate.getText().equals(" - - ") ? null : new Timestamp(jtfldAuditDate.getSelectedDate(true).getTime()),
                               jtfldAuditEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldAuditEndDate.getSelectedDate(true).getTime()),
                               jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
                               jtfldEnterpriseCell.getText().equals("") ? null : jtfldEnterpriseCell.getText(),
                               jtfldActivity.getText().equals("") ? null : jtfldActivity.getText(),
                               jtfldAuditPlan.getText().equals("") ? null : jtfldAuditPlan.getText(),
                               jcmbAuditType.getTrueSelectItem() == null ? null : (String)jcmbAuditType.getTrueSelectItem());

	    }
	    catch (NullPointerException ex) {
	      JFrame frame = new JFrame();
	      JOptionPane.showMessageDialog(frame,
					    res.getString("label.chooseEnterprise"),
					    res.getString("label.warning"),
					    JOptionPane.WARNING_MESSAGE);
	    }

	  }
	}

	else {
	  try {
            asDataSource.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                               jtfldAuditDate.getText().equals(" - - ") ? null : new Timestamp(jtfldAuditDate.getSelectedDate(true).getTime()),
                               jtfldAuditEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldAuditEndDate.getSelectedDate(true).getTime()),
                               jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
                               jtfldEnterpriseCell.getText().equals("") ? null : jtfldEnterpriseCell.getText(),
                               jtfldActivity.getText().equals("") ? null : jtfldActivity.getText(),
                               jtfldAuditPlan.getText().equals("") ? null : jtfldAuditPlan.getText(),
                               jcmbAuditType.getTrueSelectItem() == null ? null : (String)jcmbAuditType.getTrueSelectItem());

	  }
	  catch (NullPointerException ex) {
	    JFrame frame = new JFrame();
	    JOptionPane.showMessageDialog(frame,
					  res.getString("label.chooseEnterprise"),
					  res.getString("label.warning"),
					  JOptionPane.WARNING_MESSAGE);
	  }

	}
      }

    });
    content.add(jpnlDateSelector, cc.xyw(2, 2, 8));
    content.add(jpnlStateSelector, cc.xyw(2, 4, 8));
    content.add(jpnlEnterpriseCellSelector, cc.xyw(2, 6, 8));
    content.add(jpnlActivitySelector, cc.xyw(2, 8, 8));
    content.add(jpnlAuditPlanSelector, cc.xyw(2, 10, 8));
    content.add(jpnlAuditTypeSelector, cc.xyw(2, 12, 8));

    add(content, BorderLayout.NORTH);
    add(new AuditSearchTable(asDataSource, jbtnUpdate), BorderLayout.CENTER);
  }
}
