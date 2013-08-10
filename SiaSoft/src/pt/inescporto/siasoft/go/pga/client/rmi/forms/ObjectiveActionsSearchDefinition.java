package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import pt.inescporto.template.client.design.thirdparty.ComponentTitledBorder;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
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
public class ObjectiveActionsSearchDefinition extends JPanel {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  ObjectiveActionsSearchDataSource oaDS = new ObjectiveActionsSearchDataSource();

  JPanel jpnlObjectiveSelector = null;
  JPanel jpnlDateSelector = null;
  JPanel jpnlStateSelector = null;
  JPanel jpnlUserSelector = null;

  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();

  TmplJCheckBox jchbObjective = new TmplJCheckBox();

  TmplLookupButton jlbtnGoal = new TmplLookupButton();
  TmplJTextField jtfldGoal = new TmplJTextField();
  TmplLookupField jlfldGoal = new TmplLookupField();

  TmplLookupButton jlbtnObjective = new TmplLookupButton();
  TmplJTextField jtfldObjective = new TmplJTextField();
  TmplLookupField jlfldObjective = new TmplLookupField();

  TmplJCheckBox jchbDate = new TmplJCheckBox();

  TmplJLabel jlblActionDate = new TmplJLabel();
  TmplJDatePicker jtfldActionDate = new TmplJDatePicker();

  TmplJLabel jlblActionEndDate = new TmplJLabel();
  TmplJDatePicker jtfldActionEndDate = new TmplJDatePicker();

  TmplJCheckBox jchbState = new TmplJCheckBox();

  TmplJLabel jlblState = new TmplJLabel();
  TmplJComboBox jcmbState = new TmplJComboBox();

  TmplJCheckBox jchbUser = new TmplJCheckBox();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  public ObjectiveActionsSearchDefinition() {
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    // Goal
    jtfldGoal.setField("goalID");
    jtfldGoal.setLabel(res.getString("oa.label.goal"));

    jlbtnGoal.setText(res.getString("oa.label.goal"));
    jlbtnGoal.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Goals");
    if (!MenuSingleton.isSupplier())
      jlbtnGoal.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnGoal.setTitle(res.getString("oa.label.goalList"));
    jlbtnGoal.setDefaultFill(jtfldGoal);

    jlfldGoal.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Goals");
    jlfldGoal.setDefaultRefField(jtfldGoal);
    jlfldGoal.tmplInitialize(new TemplateEvent(this));

    // Objective
    jtfldObjective.setField("objectiveID");
    jtfldObjective.setLabel(res.getString("oa.label.objective"));

    jlbtnObjective.setText(res.getString("oa.label.objective"));
    jlbtnObjective.setTitle(res.getString("oa.label.objectiveList"));
    jlbtnObjective.setFillList(new JTextField[] {jtfldGoal, jtfldObjective});
    jlbtnObjective.setComponentLinkList(new JComponent[] {jtfldGoal});
    jlbtnObjective.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Objective");

    jlfldObjective.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Objective");
    jlfldObjective.setRefFieldList(new JTextField[] {jtfldGoal, jtfldObjective});
    jlfldObjective.tmplInitialize(new TemplateEvent(this));

    // Action Date
    jlblActionDate.setText(res.getString("oa.label.actionDate"));
    jtfldActionDate.setField("startDate");
    jtfldActionDate.setHolder(jlblActionDate);

    // Action End Date
    jlblActionEndDate.setText(res.getString("oa.label.actionEndDate"));
    jtfldActionEndDate.setField("endDate");
    jtfldActionEndDate.setHolder(jlblActionEndDate);

    // State
    jlblState.setText(res.getString("oa.label.state"));
    jcmbState.setField("status");
    jcmbState.setDataItems(new Object[] {"", res.getString("oa.label.waiting"), res.getString("oa.label.running"), res.getString("oa.label.delayed"), res.getString("oa.label.ended"),
			   res.getString("oa.label.schedule")});
    jcmbState.setDataValues(new Object[] {null, "W", "R", "D", "E", "S"});

    //User
    jlbtnUser.setText(res.getString("oa.label.user"));
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUser.setTitle(res.getString("oa.label.userList"));
    jlbtnUser.setDefaultFill(jtfldUser);

    jtfldUser.setField("fkUserID");
    jtfldUser.setLabel(res.getString("oa.label.user"));
    jlfldUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUser.setDefaultRefField(jtfldUser);
    jlfldUser.tmplInitialize(new TemplateEvent(this));

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,170dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    FormLayout fm = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				   "5px, pref, 5px");

    FormLayout fm2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				    "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, 5px");

    CellConstraints cc = new CellConstraints();

    final JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    jpnlObjectiveSelector = new JPanel();
    jpnlObjectiveSelector.setOpaque(false);
    jpnlObjectiveSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyObjective = new JPanel(fm2);
    jpnlDummyObjective.setOpaque(false);
    jpnlDummyObjective.setVisible(false);

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

    jpnlUserSelector = new JPanel();
    jpnlUserSelector.setOpaque(false);
    jpnlUserSelector.setLayout(new BorderLayout());

    final JPanel jpnlDummyUser = new JPanel(fm);
    jpnlDummyUser.setOpaque(false);
    jpnlDummyUser.setVisible(false);

    jpnlDummyObjective.add(jlbtnGoal, cc.xy(2, 2));
    jlbtnGoal.setEnabled(false);
    jpnlDummyObjective.add(jtfldGoal, cc.xy(4, 2));
    jtfldGoal.setEnabled(false);
    jpnlDummyObjective.add(jlfldGoal, cc.xyw(6, 2, 4));
    jlfldGoal.setEnabled(false);

    jpnlDummyObjective.add(jlbtnObjective, cc.xy(2, 4));
    jlbtnObjective.setEnabled(false);
    jpnlDummyObjective.add(jtfldObjective, cc.xy(4, 4));
    jtfldObjective.setEnabled(false);
    jpnlDummyObjective.add(jlfldObjective, cc.xyw(6, 4, 4));
    jlfldObjective.setEnabled(false);

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

    jpnlDummyUser.add(jlbtnUser, cc.xy(2, 2));
    jlbtnUser.setEnabled(false);
    jpnlDummyUser.add(jtfldUser, cc.xy(4, 2));
    jtfldUser.setEnabled(false);
    jpnlDummyUser.add(jlfldUser, cc.xyw(6, 2, 4));

    jchbObjective.setText(res.getString("oa.label.objective"));
    jchbObjective.setBackground(new Color(200,221,244));
    jchbObjective.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  Component[] component = jpnlDummyObjective.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < component.length; i++) {
	    component[i].setEnabled(true);
	    jlfldGoal.setEnabled(false);
	    jlfldObjective.setEnabled(false);
	  }
	  jpnlDummyObjective.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jtfldGoal.setText("");
	  jlfldGoal.setText("");
	  jtfldObjective.setText("");
	  jlfldObjective.setText("");
	  Component[] component = jpnlDummyObjective.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(false);
	  jpnlDummyObjective.setVisible(false);
	  content.invalidate();
	  content.repaint();
	}
      }
    });
    ComponentTitledBorder borderObjective = new ComponentTitledBorder(jchbObjective, jpnlObjectiveSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlObjectiveSelector.setBorder(borderObjective);
    jpnlObjectiveSelector.add(jpnlDummyObjective, BorderLayout.NORTH);

    jchbDate.setText(res.getString("oa.label.date"));
    jchbDate.setBackground(new Color(200,221,244));
    jchbDate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  Component[] component = jpnlDummyDate.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);
	  jpnlDummyDate.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
          jtfldActionDate.setSelectedDate(null);
          jtfldActionEndDate.setSelectedDate(null);
	  Component[] component = jpnlDummyDate.getComponents();
	  oaDS.setUp(false);
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

    jchbState.setText(res.getString("oa.label.state"));
    jchbState.setBackground(new Color(200,221,244));
    jchbState.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  Component[] component = jpnlDummyState.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
	    component[i].setEnabled(true);

	  jpnlDummyState.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jcmbState.setSelectedIndex(0);
	  Component[] component = jpnlDummyState.getComponents();
	  oaDS.setUp(false);
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

    jchbUser.setText(res.getString("oa.label.user"));
    jchbUser.setBackground(new Color(200,221,244));
    jchbUser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  Component[] component = jpnlDummyUser.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < (component.length) - 1; i++)
	    component[i].setEnabled(true);

	  jpnlDummyUser.setVisible(true);
	  content.invalidate();
	  content.repaint();
	}
	else {
	  jlbtnUser.setText("");
	  jlfldUser.setText("");
	  jtfldUser.setText("");
	  Component[] component = jpnlDummyUser.getComponents();
	  oaDS.setUp(false);
	  for (int i = 0; i < component.length; i++)
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

    jbtnUpdate.setText(res.getString("label.update"));
    jbtnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	oaDS.setNullValues(false);

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
	    oaDS.setUp(false);

	  }
	  else {
	    try {
	      oaDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
				 jtfldGoal.getText().equals("") ? null : jtfldGoal.getText(),
				 jtfldObjective.getText().equals("") ? null : jtfldObjective.getText(),
                                 jtfldActionDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionDate.getSelectedDate(true).getTime()),
                                jtfldActionEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionEndDate.getSelectedDate(true).getTime()),
				 jtfldUser.getText().equals("") ? null : jtfldUser.getText(),
				 jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem());
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
	    oaDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
			       jtfldGoal.getText().equals("") ? null : jtfldGoal.getText(),
			       jtfldObjective.getText().equals("") ? null : jtfldObjective.getText(),
                               jtfldActionDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionDate.getSelectedDate(true).getTime()),
                               jtfldActionEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldActionEndDate.getSelectedDate(true).getTime()),
			       jtfldUser.getText().equals("") ? null : jtfldUser.getText(),
			       jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem());
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

    content.add(jpnlObjectiveSelector, cc.xyw(2, 2, 8));
    content.add(jpnlDateSelector, cc.xyw(2, 4, 8));
    content.add(jpnlStateSelector, cc.xyw(2, 6, 8));
    content.add(jpnlUserSelector, cc.xyw(2, 8, 8));
    add(content, BorderLayout.NORTH);
    add(new ObjectiveActionsSearchTable(oaDS, jbtnUpdate), BorderLayout.CENTER);
  }
}
