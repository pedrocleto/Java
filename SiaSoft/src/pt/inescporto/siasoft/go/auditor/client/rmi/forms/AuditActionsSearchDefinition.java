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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.thirdparty.ComponentTitledBorder;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import java.awt.Color;
import pt.inescporto.template.client.design.TmplJDatePicker;
import java.sql.Timestamp;

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
public class AuditActionsSearchDefinition extends JPanel {

  JPanel jpnlActionsSelector = null;
  JPanel jpnlDateSelector = null;

  JPanel jpnlStateSelector = null;
  JPanel jpnlUserSelector = null;

  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();
  AuditActionsSearchDataSource aaDS = new AuditActionsSearchDataSource();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJCheckBox jchbAuditPlan = new TmplJCheckBox();

  TmplLookupButton jlbtnAuditPlan = new TmplLookupButton();
  TmplJTextField jtfldAuditPlan = new TmplJTextField();
  TmplLookupField jlfldAuditPlan = new TmplLookupField();

  TmplLookupButton jlbtnAudit = new TmplLookupButton();
  TmplJDateField jtfldAudit = new TmplJDateField();

  TmplLookupButton jlbtnAuditAction = new TmplLookupButton();
  TmplJTextField jtfldAuditAction = new TmplJTextField();

  TmplJCheckBox jchbDate = new TmplJCheckBox();

  TmplJLabel jlblActionDate = new TmplJLabel();
  TmplJDatePicker jtfldActionDate = new TmplJDatePicker();

  TmplJLabel jlblActionEndDate = new TmplJLabel();
  TmplJDatePicker jtfldActionEndDate = new TmplJDatePicker();

  TmplJCheckBox jchbActions = new TmplJCheckBox();

  TmplJLabel jlblActions = new TmplJLabel();
  TmplJComboBox jcmbActions = new TmplJComboBox();

  TmplJCheckBox jchbState = new TmplJCheckBox();

  TmplJLabel jlblState = new TmplJLabel();
  TmplJComboBox jcmbState = new TmplJComboBox();

  TmplJCheckBox jchbUser = new TmplJCheckBox();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  public AuditActionsSearchDefinition() {
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    // Action Date
    jlblActionDate.setText(res.getString("aaDefinition.label.actionDate"));
    jtfldActionDate.setField("planStartDate");
    jtfldActionDate.setHolder(jlblActionDate);

    // Action End Date
    jlblActionEndDate.setText(res.getString("aaDefinition.label.actionEndDate"));
    jtfldActionEndDate.setField("planEndDate");
    jtfldActionEndDate.setHolder(jlblActionEndDate);

    // State
    jlblState.setText(res.getString("aaDefinition.label.state"));
    jcmbState.setField("status");
    jcmbState.setDataItems(new Object[] {"", res.getString("aaDefinition.label.waiting"), res.getString("aaDefinition.label.running"), res.getString("aaDefinition.label.delayed"),
			   res.getString("aaDefinition.label.ended"), res.getString("aaDefinition.label.schedule")});
    jcmbState.setDataValues(new Object[] {null, "W", "R", "D", "E", "S"});

    // Audit Plan
    jtfldAuditPlan.setLabel(res.getString("aaDefinition.label.auditPlan"));
    jtfldAuditPlan.setField("auditPlanID");

    jlbtnAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnAuditPlan.setText(res.getString("aaDefinition.label.auditPlan"));
    jlbtnAuditPlan.setTitle(res.getString("aaDefinition.label.auditPlanList"));
    jlbtnAuditPlan.setDefaultFill(jtfldAuditPlan);

    jlfldAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlfldAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldAuditPlan.setDefaultRefField(jtfldAuditPlan);
    jlfldAuditPlan.tmplInitialize(new TemplateEvent(this));

    //Audit
    jtfldAudit.setField("auditDate");
    jtfldAudit.setLabel(res.getString("aaDefinition.label.audit"));

    jlbtnAudit.setText(res.getString("aaDefinition.label.audit"));
    jlbtnAudit.setTitle(res.getString("aaDefinition.label.auditList"));
    jlbtnAudit.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAudit});
    jlbtnAudit.setComponentLinkList(new JComponent[] {jtfldAuditPlan});
    jlbtnAudit.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");

    //Audit Action
    jtfldAuditAction.setField("auditActionID");
    jtfldAuditAction.setLabel(res.getString("aaDefinition.label.auditAction"));

    jlbtnAuditAction.setText(res.getString("aaDefinition.label.auditAction"));
    jlbtnAuditAction.setTitle(res.getString("aaDefinition.label.auditActionList"));
    jlbtnAuditAction.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAuditAction});
    jlbtnAuditAction.setComponentLinkList(new JComponent[] {jtfldAuditPlan});
    jlbtnAuditAction.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActions");

    // User
    jlbtnUser.setText(res.getString("aaDefinition.label.user"));
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUser.setTitle(res.getString("aaDefinition.label.userList"));
    jlbtnUser.setDefaultFill(jtfldUser);

    jtfldUser.setField("fkUserID");
    jtfldUser.setLabel(res.getString("aaDefinition.label.user"));
    jlfldUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUser.setDefaultRefField(jtfldUser);
    jlfldUser.tmplInitialize(new TemplateEvent(this));

    // Actions
    jlblActions.setText(res.getString("aaDefinition.label.actions"));
    jcmbActions.setField("type");
    jcmbActions.setDataItems(new Object[] {"", res.getString("aaDefinition.label.preventiveActions"), res.getString("aaDefinition.label.correctiveActions")});
    jcmbActions.setDataValues(new Object[] {null, "P", "C"});

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    FormLayout fm = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				   "5px, pref, 5px");

    FormLayout fm2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				    "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, 5px");

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

    jpnlActionsSelector = new JPanel();
    jpnlActionsSelector.setOpaque(false);
    jpnlActionsSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyActions = new JPanel(fm2);
    jpnlDummyActions.setOpaque(false);
    jpnlDummyActions.setVisible(false);

    jpnlUserSelector = new JPanel();
    jpnlUserSelector.setOpaque(false);
    jpnlUserSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyUser = new JPanel(fm);
    jpnlDummyUser.setOpaque(false);
    jpnlDummyUser.setVisible(false);

    jpnlDummyDate.add(jlblActionDate, cc.xy(2, 2));
    jlblActionDate.setEnabled(false);
    jpnlDummyDate.add(jtfldActionDate, cc.xy(4, 2));
    jtfldActionDate.setEnabled(false);

    jpnlDummyDate.add(jlblActionEndDate, cc.xy(6, 2));
    jlblActionEndDate.setEnabled(false);
    jpnlDummyDate.add(jtfldActionEndDate, cc.xy(8, 2));
    jtfldActionEndDate.setEnabled(false);

    jpnlDummyState.add(jlblState, cc.xy(2, 2));
    jlblState.setEnabled(false);
    jpnlDummyState.add(jcmbState, cc.xy(4, 2));
    jcmbState.setEnabled(false);

    jpnlDummyActions.add(jlbtnAuditPlan, cc.xy(2, 2));
    jlbtnAuditPlan.setEnabled(false);
    jpnlDummyActions.add(jtfldAuditPlan, cc.xy(4, 2));
    jtfldAuditPlan.setEnabled(false);
    jpnlDummyActions.add(jlfldAuditPlan, cc.xyw(6, 2, 3));
    jlfldAuditPlan.setEnabled(false);

    jpnlDummyActions.add(jlbtnAudit, cc.xy(2, 4));
    jlbtnAudit.setEnabled(false);
    jpnlDummyActions.add(jtfldAudit, cc.xy(4, 4));
    jtfldAudit.setEnabled(false);

    jpnlDummyActions.add(jlbtnAuditAction, cc.xy(2, 6));
    jlbtnAuditAction.setEnabled(false);
    jpnlDummyActions.add(jtfldAuditAction, cc.xy(4, 6));

    jpnlDummyActions.add(jlblActions, cc.xy(2, 8));
    jlblActions.setEnabled(false);
    jpnlDummyActions.add(jcmbActions, cc.xy(4, 8));
    jcmbActions.setEnabled(false);

    jpnlDummyUser.add(jlbtnUser, cc.xy(2, 2));
    jlbtnUser.setEnabled(false);
    jpnlDummyUser.add(jtfldUser, cc.xy(4, 2));
    jtfldUser.setEnabled(false);
    jpnlDummyUser.add(jlfldUser, cc.xyw(6, 2, 3));
    jlfldUser.setEnabled(false);

    jchbDate.setText(res.getString("aaDefinition.label.date"));
    jchbDate.setBackground(new Color(200,221,244));
    jchbDate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyDate.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  aaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);
	  jpnlDummyDate.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
          jtfldActionDate.setSelectedDate(null);
          jtfldActionEndDate.setSelectedDate(null);
	  aaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);
	  jpnlDummyDate.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    jtfldActionDate.setSelectedDate(null);
    jtfldActionEndDate.setSelectedDate(null);

    ComponentTitledBorder borderDate = new ComponentTitledBorder(jchbDate, jpnlDateSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlDateSelector.setBorder(borderDate);
    jpnlDateSelector.add(jpnlDummyDate, BorderLayout.NORTH);

    jchbState.setText(res.getString("aaDefinition.label.state"));
    jchbState.setBackground(new Color(200,221,244));
    jchbState.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyState.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  aaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);

	  jpnlDummyState.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jcmbState.setSelectedIndex(0);
	  aaDS.setUp(false);
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

    jchbActions.setText(res.getString("aaDefinition.label.actions"));
    jchbActions.setBackground(new Color(200,221,244));
    jchbActions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyActions.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  aaDS.setUp(false);
	  for (int i = 0; i < component.length; i++) {
	    component[i].setEnabled(true);
	    jlfldAuditPlan.setEnabled(false);
	  }
	  jpnlDummyActions.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jcmbActions.setSelectedIndex(0);
	  jtfldAuditPlan.setText("");
	  jlfldAuditPlan.setText("");
	  jtfldAudit.setText("");
	  jtfldAuditAction.setText("");
	  aaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);

	  jpnlDummyActions.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderActions = new ComponentTitledBorder(jchbActions, jpnlActionsSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlActionsSelector.setBorder(borderActions);
    jpnlActionsSelector.add(jpnlDummyActions, BorderLayout.NORTH);

    jchbUser.setBackground(new Color(200,221,244));
    jchbUser.setText(res.getString("aaDefinition.label.user"));
    jchbUser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Component[] component = jpnlDummyUser.getComponents();
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  aaDS.setUp(false);
	  for (int i = 0; i < (component.length) - 1; i++)
	    component[i].setEnabled(true);

	  jpnlDummyUser.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jtfldUser.setText("");
	  jlfldUser.setText("");
	  aaDS.setUp(false);
	  for (int i = 0; i < (component.length) - 1; i++)
	    component[i].setEnabled(false);

	  jpnlDummyUser.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderUser = new ComponentTitledBorder(jchbUser, jpnlUserSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlUserSelector.setBorder(borderUser);
    jpnlUserSelector.add(jpnlDummyUser, BorderLayout.NORTH);

    // Update
    jbtnUpdate.setText(res.getString("label.update"));
    jbtnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	aaDS.setNullValues(false);

        if ((!jtfldActionDate.getText().equals(" - - ") && !jtfldActionEndDate.getText().equals(" - - "))) {
	  if ((jtfldActionEndDate.getSelectedDate(true).before(jtfldActionDate.getSelectedDate(true))) || (jtfldActionEndDate.getSelectedDate(true).equals(jtfldActionDate.getSelectedDate(true)))) {
            jtfldActionDate.setSelectedDate(null);
            jtfldActionEndDate.setSelectedDate(null);
	    JFrame frame = new JFrame();
	    JOptionPane.showMessageDialog(frame,
                                          res.getString("label.invalidDate"),
					  res.getString("label.warning"),
					  JOptionPane.WARNING_MESSAGE);

	    jtfldActionDate.requestFocus();
	    aaDS.setUp(false);

	  }
	  else {
	    try {
	      aaDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                                 jtfldActionDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionDate.getSelectedDate(true).getTime()),
                                 jtfldActionEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionEndDate.getSelectedDate(true).getTime()),
				 jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
				 jtfldAuditPlan.getText().equals("") ? null : jtfldAuditPlan.getText(),
				 jtfldAudit.getText().equals("") ? null : jtfldAudit.getFormatedText(),
				 jtfldAuditAction.getText().equals("") ? null : jtfldAuditAction.getText(),
				 jcmbActions.getTrueSelectItem() == null ? null : (String)jcmbActions.getTrueSelectItem(),
				 jtfldUser.getText().equals("") ? null : jtfldUser.getText());

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
	    aaDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                               jtfldActionDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionDate.getSelectedDate(true).getTime()),
                               jtfldActionEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionEndDate.getSelectedDate(true).getTime()),
			       jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
			       jtfldAuditPlan.getText().equals("") ? null : jtfldAuditPlan.getText(),
			       jtfldAudit.getText().equals("") ? null : jtfldAudit.getFormatedText(),
			       jtfldAuditAction.getText().equals("") ? null : jtfldAuditAction.getText(),
			       jcmbActions.getTrueSelectItem() == null ? null : (String)jcmbActions.getTrueSelectItem(),
			       jtfldUser.getText().equals("") ? null : jtfldUser.getText());
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
    content.add(jpnlActionsSelector, cc.xyw(2, 2, 8));
    content.add(jpnlDateSelector, cc.xyw(2, 4, 8));
    content.add(jpnlStateSelector, cc.xyw(2, 6, 8));
    content.add(jpnlUserSelector, cc.xyw(2, 8, 8));

    add(content, BorderLayout.NORTH);
    add(new AuditActionsSearchTable(aaDS, jbtnUpdate), BorderLayout.CENTER);
  }
}
