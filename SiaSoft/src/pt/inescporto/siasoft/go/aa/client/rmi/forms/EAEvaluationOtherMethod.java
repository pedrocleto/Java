package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import java.awt.event.ActionListener;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJComboBox;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pt.inescporto.template.client.util.TmplFormModes;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class EAEvaluationOtherMethod extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnvAsp = new TmplLookupButton();
  TmplJTextField jtfldEnvAspID = new TmplJTextField();
  TmplLookupField jlfldEnvAspDescription = new TmplLookupField();

  TmplLookupButton jlbtnCategory1 = new TmplLookupButton();
  TmplJTextField jtfldCategory1 = new TmplJTextField();
  TmplJTextField jtfldCategory1ID = new TmplJTextField();
  TmplLookupField jlfldCategory1Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory2 = new TmplLookupButton();
  TmplJTextField jtfldCategory2 = new TmplJTextField();
  TmplJTextField jtfldCategory2ID = new TmplJTextField();
  TmplLookupField jlfldCategory2Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory3 = new TmplLookupButton();
  TmplJTextField jtfldCategory3 = new TmplJTextField();
  TmplJTextField jtfldCategory3ID = new TmplJTextField();
  TmplLookupField jlfldCategory3Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory4 = new TmplLookupButton();
  TmplJTextField jtfldCategory4 = new TmplJTextField();
  TmplJTextField jtfldCategory4ID = new TmplJTextField();
  TmplLookupField jlfldCategory4Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory5 = new TmplLookupButton();
  TmplJTextField jtfldCategory5 = new TmplJTextField();
  TmplJTextField jtfldCategory5ID = new TmplJTextField();
  TmplLookupField jlfldCategory5Description = new TmplLookupField();

  TmplJComboBox jcmbIsMaxOrMin = new TmplJComboBox();
  TmplJLabel jlblConditionMax = new TmplJLabel();
  TmplJTextField jtfldConditionMax = new TmplJTextField();

  TmplJLabel jlblConditionMin = new TmplJLabel();
  // TmplJTextField jtfldConditionMin = new TmplJTextField();

  TmplJLabel jlblOperator = new TmplJLabel();
  TmplJComboBox jcmbOperator = new TmplJComboBox();

  TmplJLabel jlblSecundOperator = new TmplJLabel();
  TmplJComboBox jcmbSecundOperator = new TmplJComboBox();

  TmplJLabel jlblThirdOperator = new TmplJLabel();
  TmplJComboBox jcmbThirdOperator = new TmplJComboBox();

  TmplJLabel jlblForthOperator = new TmplJLabel();
  TmplJComboBox jcmbForthOperator = new TmplJComboBox();

  TmplJLabel jlblSignificance = new TmplJLabel();
  TmplJTextField jtfldSignificanceDescription = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };
  TmplJTextField jtfldSignificance = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };
  TmplJButton jbtnCalc = new TmplJButton() {
    public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
	case TmplFormModes.MODE_SELECT:
	  this.setEnabled(false);
	  break;
	case TmplFormModes.MODE_INSERT:
	case TmplFormModes.MODE_UPDATE:
	case TmplFormModes.MODE_FIND:
	  this.setEnabled(true);
	  break;
      }
    }
  };

  public EAEvaluationOtherMethod(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnEnvAsp);
    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jlfldEnvAspDescription);
    datasource.addDatasourceListener(jlbtnCategory1);
    datasource.addDatasourceListener(jtfldCategory1ID);
    datasource.addDatasourceListener(jtfldCategory1);
    datasource.addDatasourceListener(jlfldCategory1Description);
    datasource.addDatasourceListener(jcmbOperator);
    datasource.addDatasourceListener(jlbtnCategory2);
    datasource.addDatasourceListener(jtfldCategory2ID);
    datasource.addDatasourceListener(jtfldCategory2);
    datasource.addDatasourceListener(jlfldCategory2Description);
    datasource.addDatasourceListener(jcmbSecundOperator);
    datasource.addDatasourceListener(jlbtnCategory3);
    datasource.addDatasourceListener(jtfldCategory3ID);
    datasource.addDatasourceListener(jtfldCategory3);
    datasource.addDatasourceListener(jlfldCategory3Description);
    datasource.addDatasourceListener(jcmbThirdOperator);
    datasource.addDatasourceListener(jlbtnCategory4);
    datasource.addDatasourceListener(jtfldCategory4ID);
    datasource.addDatasourceListener(jtfldCategory4);
    datasource.addDatasourceListener(jlfldCategory4Description);
    datasource.addDatasourceListener(jcmbForthOperator);
    datasource.addDatasourceListener(jlbtnCategory5);
    datasource.addDatasourceListener(jtfldCategory5ID);
    datasource.addDatasourceListener(jtfldCategory5);
    datasource.addDatasourceListener(jlfldCategory5Description);
    datasource.addDatasourceListener(jcmbIsMaxOrMin);
    datasource.addDatasourceListener(jtfldConditionMax);
    datasource.addDatasourceListener(jtfldSignificanceDescription);
    datasource.addDatasourceListener(jtfldSignificance);

    fwCListener.addFWComponentListener(jlbtnEnvAsp);
    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jlfldEnvAspDescription);
    fwCListener.addFWComponentListener(jlbtnCategory1);
    fwCListener.addFWComponentListener(jtfldCategory1ID);
    fwCListener.addFWComponentListener(jtfldCategory1);
    fwCListener.addFWComponentListener(jlfldCategory1Description);
    fwCListener.addFWComponentListener(jcmbOperator);
    fwCListener.addFWComponentListener(jlbtnCategory2);
    fwCListener.addFWComponentListener(jtfldCategory2ID);
    fwCListener.addFWComponentListener(jtfldCategory2);
    fwCListener.addFWComponentListener(jlfldCategory2Description);
    fwCListener.addFWComponentListener(jcmbSecundOperator);
    fwCListener.addFWComponentListener(jlbtnCategory3);
    fwCListener.addFWComponentListener(jtfldCategory3ID);
    fwCListener.addFWComponentListener(jtfldCategory3);
    fwCListener.addFWComponentListener(jlfldCategory3Description);
    fwCListener.addFWComponentListener(jcmbThirdOperator);
    fwCListener.addFWComponentListener(jlbtnCategory4);
    fwCListener.addFWComponentListener(jtfldCategory4ID);
    fwCListener.addFWComponentListener(jtfldCategory4);
    fwCListener.addFWComponentListener(jlfldCategory4Description);
    fwCListener.addFWComponentListener(jcmbForthOperator);
    fwCListener.addFWComponentListener(jlbtnCategory5);
    fwCListener.addFWComponentListener(jtfldCategory5ID);
    fwCListener.addFWComponentListener(jtfldCategory5);
    fwCListener.addFWComponentListener(jlfldCategory5Description);
    fwCListener.addFWComponentListener(jcmbIsMaxOrMin);
    fwCListener.addFWComponentListener(jtfldConditionMax);
    fwCListener.addFWComponentListener(jtfldSignificanceDescription);
    fwCListener.addFWComponentListener(jtfldSignificance);
    fwCListener.addFWComponentListener(jbtnCalc);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEnvAspID.setField("envAspID");

    jlbtnEnvAsp.setText(res.getString("label.envAsp"));
    jlbtnEnvAsp.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlbtnEnvAsp.setTitle(res.getString("label.envAspList"));
    jlbtnEnvAsp.setDefaultFill(jtfldEnvAspID);

    jlfldEnvAspDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlfldEnvAspDescription.setDefaultRefField(jtfldEnvAspID);

    jtfldCategory1ID.setField("cat1ID");
    jtfldCategory1.setField("cat1");

    jlbtnCategory1.setText(res.getString("eaevaluationOtherMethod.label.cat1"));
    jlbtnCategory1.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory1.setTitle(res.getString("eaevaluationOtherMethod.label.cat1"));
    jlbtnCategory1.setDefaultFill(jtfldCategory1);
    JTextField[] txt = new JTextField[2];
    txt[0] = jtfldCategory1ID;
    txt[1] = jtfldCategory1;
    jlbtnCategory1.setFillList(txt);

    jlfldCategory1Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory1Description.setRefFieldList(txt);

    jlblOperator.setText(res.getString("eaevaluationOtherMethod.label.op"));
    jcmbOperator.setField("operator1");
    jcmbOperator.setDataItems(new Object[] {"", "+", "-", "x"});
    jcmbOperator.setDataValues(new Object[] {null, "+", "-", "*"});
    jcmbOperator.setHolder(jlblOperator);

    jtfldCategory2ID.setField("cat2ID");
    jtfldCategory2.setField("cat2");

    jlbtnCategory2.setText(res.getString("eaevaluationOtherMethod.label.cat2"));
    jlbtnCategory2.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory2.setTitle(res.getString("eaevaluationOtherMethod.label.cat2"));
    JTextField[] txt2 = new JTextField[2];
    txt2[0] = jtfldCategory2ID;
    txt2[1] = jtfldCategory2;
    jlbtnCategory2.setFillList(txt2);

    jlfldCategory2Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory2Description.setRefFieldList(txt2);

    jlblSecundOperator.setText(res.getString("eaevaluationOtherMethod.label.op"));
    jcmbSecundOperator.setField("operator2");
    jcmbSecundOperator.setDataItems(new Object[] {"", "+", "-", "x"});
    jcmbSecundOperator.setDataValues(new Object[] {null, "+", "-", "*"});
    jcmbSecundOperator.setHolder(jlblSecundOperator);

    jtfldCategory3ID.setField("cat3ID");
    jtfldCategory3.setField("cat3");

    jlbtnCategory3.setText(res.getString("eaevaluationOtherMethod.label.cat3"));
    jlbtnCategory3.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory3.setTitle(res.getString("eaevaluationOtherMethod.label.cat3"));
    JTextField[] txt3 = new JTextField[2];
    txt3[0] = jtfldCategory3ID;
    txt3[1] = jtfldCategory3;
    jlbtnCategory3.setFillList(txt3);

    jlfldCategory3Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory3Description.setRefFieldList(txt3);

    jlblThirdOperator.setText(res.getString("eaevaluationOtherMethod.label.op"));
    jcmbThirdOperator.setField("operator3");
    jcmbThirdOperator.setDataItems(new Object[] {"", "+", "-", "x"});
    jcmbThirdOperator.setDataValues(new Object[] {null, "+", "-", "*"});
    jcmbThirdOperator.setHolder(jlblThirdOperator);

    jtfldCategory4ID.setField("cat3ID");
    jtfldCategory4.setField("cat3");

    jlbtnCategory4.setText(res.getString("eaevaluationOtherMethod.label.cat4"));
    jlbtnCategory4.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory4.setTitle(res.getString("eaevaluationOtherMethod.label.cat4"));
    JTextField[] txt4 = new JTextField[2];
    txt4[0] = jtfldCategory4ID;
    txt4[1] = jtfldCategory4;
    jlbtnCategory4.setFillList(txt4);

    jlfldCategory4Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory4Description.setRefFieldList(txt4);

    jlblForthOperator.setText(res.getString("eaevaluationOtherMethod.label.op"));
    jcmbForthOperator.setField("operator4");
    jcmbForthOperator.setDataItems(new Object[] {"", "+", "-", "x"});
    jcmbForthOperator.setDataValues(new Object[] {null, "+", "-", "*"});
    jcmbForthOperator.setHolder(jlblForthOperator);

    jtfldCategory5ID.setField("cat5ID");
    jtfldCategory5.setField("cat5");

    jlbtnCategory5.setText(res.getString("eaevaluationOtherMethod.label.cat5"));
    jlbtnCategory5.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory5.setTitle(res.getString("eaevaluationOtherMethod.label.cat5"));
    JTextField[] txt5 = new JTextField[2];
    txt5[0] = jtfldCategory5ID;
    txt5[1] = jtfldCategory5;
    jlbtnCategory5.setFillList(txt5);

    jlfldCategory5Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory5Description.setRefFieldList(txt5);

    jlblConditionMin.setText(res.getString("eaOtherMethod.label.SignIf"));
    // jtfldConditionMin.setField("minvalue");
    // jtfldConditionMin.setHolder(jlblConditionMin);

    jcmbIsMaxOrMin.setField("minvalue");
    jcmbIsMaxOrMin.setDataItems(new Object[] {"", ">", "<",">=","<="});
    jcmbIsMaxOrMin.setDataValues(new Object[] {null, "MX", "MN","MXE","MNE"});
    jcmbIsMaxOrMin.setHolder(jlblConditionMin);

    jlblConditionMax.setText(res.getString("eaOtherMethod.label.than"));
    jtfldConditionMax.setField("maxvalue");

    jlblSignificance.setText(res.getString("label.significance"));
    jtfldSignificance.setField("significance");
    jtfldSignificanceDescription.setField("significanceDescription");
    jtfldSignificanceDescription.setHolder(jlblSignificance);
    jbtnCalc.setText(res.getString("label.calc"));

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,50dlu,4dlu, 70dlu,4dlu, 50dlu,40dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,5px");

    FormLayout formLayout2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu, 5px",
					    "5px, pref, 5px");

    JPanel content = new JPanel();
    JPanel significantInterval = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    significantInterval.setOpaque(false);
    significantInterval.setLayout(formLayout2);

    CellConstraints cc = new CellConstraints();
    content.add(jlbtnEnvAsp, cc.xy(2, 2));
    content.add(jtfldEnvAspID, cc.xy(4, 2));
    content.add(jlfldEnvAspDescription, cc.xyw(6, 2, 6));

    content.add(jlbtnCategory1, cc.xy(2, 4));
    content.add(jtfldCategory1, cc.xy(4, 4));
    content.add(jlfldCategory1Description, cc.xyw(6, 4, 6));

    content.add(jlblOperator, cc.xy(2, 6));
    content.add(jcmbOperator, cc.xy(4, 6));

    content.add(jlbtnCategory2, cc.xy(2, 8));
    content.add(jtfldCategory2, cc.xy(4, 8));
    content.add(jlfldCategory2Description, cc.xyw(6, 8, 6));

    content.add(jlblSecundOperator, cc.xy(2, 10));
    content.add(jcmbSecundOperator, cc.xy(4, 10));

    content.add(jlbtnCategory3, cc.xy(2, 12));
    content.add(jtfldCategory3, cc.xy(4, 12));
    content.add(jlfldCategory3Description, cc.xyw(6, 12, 6));

    content.add(jlblThirdOperator, cc.xy(2, 14));
    content.add(jcmbThirdOperator, cc.xy(4, 14));

    content.add(jlbtnCategory4, cc.xy(2, 16));
    content.add(jtfldCategory4, cc.xy(4, 16));
    content.add(jlfldCategory4Description, cc.xyw(6, 16, 6));

    content.add(jlblForthOperator, cc.xy(2, 18));
    content.add(jcmbForthOperator, cc.xy(4, 18));

    content.add(jlbtnCategory5, cc.xy(2, 20));
    content.add(jtfldCategory5, cc.xy(4, 20));
    content.add(jlfldCategory5Description, cc.xyw(6, 20, 6));

    significantInterval.setBorder(BorderFactory.createTitledBorder(res.getString("label.significance")));
    ((TitledBorder)significantInterval.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    significantInterval.add(jlblConditionMin, cc.xy(2, 2));
    significantInterval.add(jcmbIsMaxOrMin, cc.xy(4, 2));
    significantInterval.add(jlblConditionMax, cc.xy(6, 2));
    significantInterval.add(jtfldConditionMax, cc.xy(8, 2));

    content.add(significantInterval, cc.xyw(4, 22, 8));

    content.add(jbtnCalc, cc.xy(4, 24));

    content.add(jlblSignificance, cc.xy(6, 24));
    content.add(jtfldSignificanceDescription, cc.xy(8, 24));
    content.add(jtfldSignificance, cc.xy(10, 24));

    jbtnCalc.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jtfldSignificance.setText("");
	jtfldSignificanceDescription.setText("");
	if ((!jtfldCategory1.getText().equals("") && !jtfldCategory2.getText().equals("") && !jtfldCategory3.getText().equals("") && !jtfldCategory4.getText().equals("") &&
	     !jtfldCategory5.getText().equals("")
	     && !jtfldConditionMax.getText().equals("") && !jcmbIsMaxOrMin.getSelectedItem().equals("")
	     && !jcmbOperator.getSelectedItem().equals("") && !jcmbSecundOperator.getSelectedItem().equals("") && !jcmbThirdOperator.getSelectedItem().equals("") &&
	     !jcmbForthOperator.getSelectedItem().equals(""))) {
	  try {
	    int cat1 = Integer.parseInt(jtfldCategory1.getText());
	    int cat2 = Integer.parseInt(jtfldCategory2.getText());
	    int cat3 = Integer.parseInt(jtfldCategory3.getText());
	    int cat4 = Integer.parseInt(jtfldCategory4.getText());
	    int cat5 = Integer.parseInt(jtfldCategory5.getText());
	    int max = Integer.parseInt(jtfldConditionMax.getText());
	    int result = 0;
	    String min = (String)jcmbIsMaxOrMin.getSelectedItem();
	    String op = (String)jcmbOperator.getTrueSelectItem();
	    String op2 = (String)jcmbSecundOperator.getTrueSelectItem();
	    String op3 = (String)jcmbThirdOperator.getTrueSelectItem();
	    String op4 = (String)jcmbForthOperator.getTrueSelectItem();

	    if (op.equals("+") && op2.equals("+") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 + cat2 + cat3 + cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 + cat2 + cat3 + cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 + cat2 + cat3 - cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 + cat2 - cat3 - cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 - cat2 - cat3 - cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 - cat2 - cat3 - cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 - cat2 - cat3 + cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 - cat2 + cat3 + cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 * cat2 * cat3 * cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 * cat2 * cat3 * cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 * cat2 * cat3 + cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 * cat2 + cat3 + cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 * cat2 * cat3 * cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 * cat2 * cat3 - cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 * cat2 - cat3 - cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 * cat2 + cat3 + cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 * cat2 + cat3 - cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 * cat2 - cat3 + cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 * cat2 - cat3 - cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 * cat2 + cat3 - cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 * cat2 - cat3 + cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 * cat2 - cat3 + cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 + cat2 - cat3 + cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 + cat2 - cat3 + cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 + cat2 - cat3 + cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 + cat2 + cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 + cat2 + cat3 - cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 + cat2 - cat3 + cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 - cat2 - cat3 + cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 - cat2 - cat3 + cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 - cat2 + cat3 - cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 - cat2 + cat3 - cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 - cat2 + cat3 - cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 - cat2 + cat3 + cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 + cat2 - cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 + cat2 * cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 - cat3 * cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 + cat2 * cat3 + cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 + cat2 * cat3 + cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 + cat2 + cat3 * cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 + cat2 + cat3 + cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 + cat2 * cat3 * cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 + cat2 * cat3 - cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 + cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 + cat2 - cat3 - cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 - cat2 * cat3 - cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 - cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 - cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("-") && op4.equals("-")) {
	      result = cat1 - cat2 * cat3 - cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 - cat2 * cat3 - cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 - cat2 - cat3 * cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 - cat2 - cat3 - cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 - cat2 * cat3 * cat4 * cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("+") && op4.equals("+")) {
	      result = cat1 - cat2 * cat3 + cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 - cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 - cat2 + cat3 + cat4 * cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 * cat3 * cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 - cat2 * cat3 * cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 - cat2 * cat3 * cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 + cat2 * cat3 * cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("-") && op4.equals("*")) {
	      result = cat1 * cat2 * cat3 - cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("+") && op4.equals("*")) {
	      result = cat1 * cat2 * cat3 + cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 * cat2 + cat3 * cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 * cat2 - cat3 * cat4 * cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 * cat2 - cat3 * cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 * cat2 + cat3 * cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 * cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 * cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 * cat2 * cat3 + cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("*") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 * cat2 * cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 - cat3 * cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 - cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 + cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("+") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 - cat2 + cat3 * cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 - cat2 * cat3 + cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("*") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 - cat2 * cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("*") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 + cat2 * cat3 + cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 + cat2 + cat3 * cat4 - cat5;
	    }
	    if (op.equals("+") && op2.equals("+") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 + cat2 + cat3 * cat4 + cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("*") && op4.equals("-")) {
	      result = cat1 - cat2 - cat3 * cat4 - cat5;
	    }
	    if (op.equals("-") && op2.equals("-") && op3.equals("*") && op4.equals("+")) {
	      result = cat1 - cat2 - cat3 * cat4 + cat5;
	    }
	    if (op.equals("*") && op2.equals("+") && op3.equals("+") && op4.equals("-")) {
	      result = cat1 * cat2 + cat3 + cat4 - cat5;
	    }
	    if (op.equals("*") && op2.equals("-") && op3.equals("-") && op4.equals("+")) {
	      result = cat1 * cat2 - cat3 - cat4 + cat5;
	    }
	    if (op.equals("+") && op2.equals("-") && op3.equals("*") && op4.equals("*")) {
	      result = cat1 + cat2 - cat3 * cat4 * cat5;
	    }

            if (min.equals("<")) {
                    if (result < max) {
                      jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                    else {
                      jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }

                  }
                  else
                    if (min.equals("<=")) {
                      if (result <= max) {
                        jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                      else {
                        jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                    }
                  else
                    if (min.equals(">")) {
                      if (result > max) {
                        jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                      else {
                        jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                    }
                    else
                      if (min.equals(">=")) {
                        if (result >= max) {
                          jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                          jtfldSignificance.setText(String.valueOf(result));
                        }
                        else {
                          jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                          jtfldSignificance.setText(String.valueOf(result));
                        }
                      }

	  }
	  catch (NumberFormatException ex) {
	  }
	}
	else
	  if ((!jtfldCategory1.getText().equals("") && !jtfldCategory2.getText().equals("") && !jtfldCategory3.getText().equals("") && !jtfldCategory4.getText().equals("")
	       && !jtfldConditionMax.getText().equals("") && !jcmbIsMaxOrMin.getSelectedItem().equals("")
	       && !jcmbOperator.getSelectedItem().equals("") && !jcmbSecundOperator.getSelectedItem().equals("") && !jcmbThirdOperator.getSelectedItem().equals(""))) {
	    try {
	      int cat1 = Integer.parseInt(jtfldCategory1.getText());
	      int cat2 = Integer.parseInt(jtfldCategory2.getText());
	      int cat3 = Integer.parseInt(jtfldCategory3.getText());
	      int cat4 = Integer.parseInt(jtfldCategory4.getText());
	      int max = Integer.parseInt(jtfldConditionMax.getText());
	      int result = 0;
	      String min = (String)jcmbIsMaxOrMin.getSelectedItem();
	      String op = (String)jcmbOperator.getTrueSelectItem();
	      String op2 = (String)jcmbSecundOperator.getTrueSelectItem();
	      String op3 = (String)jcmbThirdOperator.getTrueSelectItem();

	      if (op.equals("+") && op2.equals("+") && op3.equals("+")) {
		result = cat1 + cat2 + cat3 + cat4;
	      }
	      if (op.equals("+") && op2.equals("+") && op3.equals("-")) {
		result = cat1 + cat2 + cat3 - cat4;
	      }
	      if (op.equals("+") && op2.equals("-") && op3.equals("-")) {
		result = cat1 + cat2 - cat3 - cat4;
	      }
	      if (op.equals("-") && op2.equals("-") && op3.equals("-")) {
		result = cat1 - cat2 - cat3 - cat4;
	      }
	      if (op.equals("-") && op2.equals("-") && op3.equals("+")) {
		result = cat1 - cat2 - cat3 + cat4;
	      }
	      if (op.equals("-") && op2.equals("+") && op3.equals("+")) {
		result = cat1 - cat2 + cat3 + cat4;
	      }
	      if (op.equals("*") && op2.equals("*") && op3.equals("*")) {
		result = cat1 * cat2 * cat3 * cat4;
	      }
	      if (op.equals("*") && op2.equals("+") && op3.equals("+")) {
		result = cat1 * cat2 + cat3 + cat4;
	      }
	      if (op.equals("*") && op2.equals("+") && op3.equals("-")) {
		result = cat1 * cat2 + cat3 - cat4;
	      }
	      if (op.equals("*") && op2.equals("-") && op3.equals("-")) {
		result = cat1 * cat2 - cat3 - cat4;
	      }
	      if (op.equals("*") && op2.equals("-") && op3.equals("+")) {
		result = cat1 * cat2 - cat3 + cat4;
	      }
	      if (op.equals("*") && op2.equals("*") && op3.equals("+")) {
		result = cat1 * cat2 * cat3 + cat4;
	      }
	      if (op.equals("*") && op2.equals("*") && op3.equals("-")) {
		result = cat1 * cat2 * cat3 - cat4;
	      }
	      if (op.equals("*") && op2.equals("+") && op3.equals("*")) {
		result = cat1 * cat2 + cat3 * cat4;
	      }
	      if (op.equals("*") && op2.equals("-") && op3.equals("*")) {
		result = cat1 * cat2 - cat3 * cat4;
	      }
	      if (op.equals("+") && op2.equals("*") && op3.equals("*")) {
		result = cat1 + cat2 * cat3 * cat4;
	      }
	      if (op.equals("-") && op2.equals("*") && op3.equals("*")) {
		result = cat1 - cat2 * cat3 * cat4;
	      }
	      if (op.equals("+") && op2.equals("+") && op3.equals("*")) {
		result = cat1 + cat2 + cat3 * cat4;
	      }
	      if (op.equals("-") && op2.equals("-") && op3.equals("*")) {
		result = cat1 - cat2 - cat3 * cat4;
	      }
	      if (op.equals("+") && op2.equals("-") && op3.equals("*")) {
		result = cat1 + cat2 - cat3 * cat4;
	      }
	      if (op.equals("-") && op2.equals("+") && op3.equals("*")) {
		result = cat1 - cat2 + cat3 * cat4;
	      }
	      if (op.equals("-") && op2.equals("*") && op3.equals("-")) {
		result = cat1 * cat2 + cat3 * cat4;
	      }
	      if (op.equals("+") && op2.equals("*") && op3.equals("+")) {
		result = cat1 + cat2 * cat3 + cat4;
	      }
	      if (op.equals("-") && op2.equals("+") && op3.equals("-")) {
		result = cat1 - cat2 + cat3 - cat4;
	      }
	      if (op.equals("+") && op2.equals("-") && op3.equals("+")) {
		result = cat1 + cat2 - cat3 + cat4;
	      }
	      if (op.equals("+") && op2.equals("*") && op3.equals("-")) {
		result = cat1 + cat2 * cat3 - cat4;
	      }
	      if (op.equals("-") && op2.equals("*") && op3.equals("+")) {
		result = cat1 - cat2 * cat3 + cat4;
	      }

              if (min.equals("<")) {
                    if (result < max) {
                      jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                    else {
                      jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }

                  }
                  else
                    if (min.equals("<=")) {
                      if (result <= max) {
                        jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                      else {
                        jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                    }
                  else
                    if (min.equals(">")) {
                      if (result > max) {
                        jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                      else {
                        jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                    }
                    else
                      if (min.equals(">=")) {
                        if (result >= max) {
                          jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                          jtfldSignificance.setText(String.valueOf(result));
                        }
                        else {
                          jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                          jtfldSignificance.setText(String.valueOf(result));
                        }
                      }

	    }
	    catch (NumberFormatException ex) {
	    }
	  }
	  else
	    if ((!jtfldCategory1.getText().equals("") && !jtfldCategory2.getText().equals("") && !jtfldCategory3.getText().equals("")
		 && !jtfldConditionMax.getText().equals("") && !jcmbIsMaxOrMin.getSelectedItem().equals("")
		 && !jcmbOperator.getSelectedItem().equals("") && !jcmbSecundOperator.getSelectedItem().equals(""))) {
	      try {
		int cat1 = Integer.parseInt(jtfldCategory1.getText());
		int cat2 = Integer.parseInt(jtfldCategory2.getText());
		int cat3 = Integer.parseInt(jtfldCategory3.getText());
		int max = Integer.parseInt(jtfldConditionMax.getText());
		int result = 0;
		String min = (String)jcmbIsMaxOrMin.getSelectedItem();
		String op = (String)jcmbOperator.getTrueSelectItem();
		String op2 = (String)jcmbSecundOperator.getTrueSelectItem();
		if (op.equals("+") && op2.equals("+")) {
		  result = cat1 + cat2 + cat3;
		}
		if (op.equals("+") && op2.equals("-")) {
		  result = cat1 + cat2 - cat3;
		}
		if (op.equals("+") && op2.equals("*")) {
		  result = cat1 + cat2 * cat3;
		}
		if (op.equals("-") && op2.equals("-")) {
		  result = cat1 - cat2 - cat3;
		}
		if (op.equals("-") && op2.equals("+")) {
		  result = cat1 - cat2 + cat3;
		}
		if (op.equals("-") && op2.equals("*")) {
		  result = cat1 - cat2 * cat3;
		}
		if (op.equals("*") && op2.equals("*")) {
		  result = cat1 * cat2 * cat3;
		}
		if (op.equals("*") && op2.equals("+")) {
		  result = cat1 * cat2 + cat3;
		}
		if (op.equals("*") && op2.equals("-")) {
		  result = cat1 * cat2 - cat3;
		}

                if (min.equals("<")) {
                  if (result < max) {
                    jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                    jtfldSignificance.setText(String.valueOf(result));
                  }
                  else {
                    jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                    jtfldSignificance.setText(String.valueOf(result));
                  }

                }
                else
                  if (min.equals("<=")) {
                    if (result <= max) {
                      jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                    else {
                      jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                  }
                else
                  if (min.equals(">")) {
                    if (result > max) {
                      jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                    else {
                      jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                      jtfldSignificance.setText(String.valueOf(result));
                    }
                  }
                  else
                    if (min.equals(">=")) {
                      if (result >= max) {
                        jtfldSignificanceDescription.setText(res.getString("label.Significant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                      else {
                        jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
                        jtfldSignificance.setText(String.valueOf(result));
                      }
                    }

	      }
	      catch (NumberFormatException ex) {
	      }
	    }
	    else
	      if ((!jtfldCategory1.getText().equals("") && !jtfldCategory2.getText().equals("")
		   && !jtfldConditionMax.getText().equals("") && !jcmbIsMaxOrMin.getSelectedItem().equals("")
		   && !jcmbOperator.getSelectedItem().equals(""))) {
		try {
		  int cat1 = Integer.parseInt(jtfldCategory1.getText());
		  int cat2 = Integer.parseInt(jtfldCategory2.getText());
		  int max = Integer.parseInt(jtfldConditionMax.getText());
		  int result = 0;
		  String min = (String)jcmbIsMaxOrMin.getSelectedItem();
		  String op = (String)jcmbOperator.getTrueSelectItem();
		  if (op.equals("+")) {
		    result = cat1 + cat2;
		  }
		  if (op.equals("-")) {
		    result = cat1 + cat2;
		  }
		  if (op.equals("*")) {
		    result = cat1 * cat2;
		  }

		  if (min.equals("<")) {
		    if (result < max) {
		      jtfldSignificanceDescription.setText(res.getString("label.Significant"));
		      jtfldSignificance.setText(String.valueOf(result));
		    }
		    else {
		      jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
		      jtfldSignificance.setText(String.valueOf(result));
		    }

		  }
		  else
		    if (min.equals("<=")) {
		      if (result <= max) {
			jtfldSignificanceDescription.setText(res.getString("label.Significant"));
			jtfldSignificance.setText(String.valueOf(result));
		      }
		      else {
			jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
			jtfldSignificance.setText(String.valueOf(result));
		      }
		    }
		  else
		    if (min.equals(">")) {
		      if (result > max) {
			jtfldSignificanceDescription.setText(res.getString("label.Significant"));
			jtfldSignificance.setText(String.valueOf(result));
		      }
		      else {
			jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
			jtfldSignificance.setText(String.valueOf(result));
		      }
		    }
		    else
		      if (min.equals(">=")) {
			if (result >= max) {
			  jtfldSignificanceDescription.setText(res.getString("label.Significant"));
			  jtfldSignificance.setText(String.valueOf(result));
			}
			else {
			  jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
			  jtfldSignificance.setText(String.valueOf(result));
			}
		      }




		}
		catch (NumberFormatException ex) {
		}
	      }

      }
    });
    add(content, BorderLayout.NORTH);
  }
}
