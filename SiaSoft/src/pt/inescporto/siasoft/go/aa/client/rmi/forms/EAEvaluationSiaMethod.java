package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.util.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.TemplateEvent;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
public class EAEvaluationSiaMethod extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnvAsp = new TmplLookupButton();
  TmplJTextField jtfldEnvAspID = new TmplJTextField();
  TmplLookupField jlfldEnvAspDescription = new TmplLookupField();

  TmplJTextField jtfldGravityID = new TmplJTextField();
  TmplLookupButton jlbtnGravity = new TmplLookupButton();
  TmplJTextField jtfldGravity = new TmplJTextField();
  TmplLookupField jlfldGravityDescription = new TmplLookupField();

  TmplJTextField jtfldProbabilityID = new TmplJTextField();
  TmplLookupButton jlbtnProbability = new TmplLookupButton();
  TmplJTextField jtfldProbability = new TmplJTextField();
  TmplLookupField jlfldProbabilityDescription = new TmplLookupField();

  TmplJLabel jlblRisk = new TmplJLabel();
  TmplJTextField jtfldRisk = new TmplJTextField();
  TmplJTextField jtfldRiskID = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };

  TmplLookupField jlfldRiskDescription = new TmplLookupField();

  TmplJTextField jtfldControlID = new TmplJTextField();
  TmplLookupButton jlbtnControl = new TmplLookupButton();
  TmplJTextField jtfldControl = new TmplJTextField();
  TmplLookupField jlfldControlDescription = new TmplLookupField();

  TmplJLabel jlblSignificance = new TmplJLabel();
  TmplJTextField jtfldSignificanceDescription = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };

  TmplJTextField jtfldResult = new TmplJTextField() {
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

  public EAEvaluationSiaMethod(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
    try {
      datasource.refresh();
    }
    catch (DataSourceException ex) {
    }
    datasource.addDatasourceListener(jlbtnEnvAsp);
    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jlfldEnvAspDescription);
    datasource.addDatasourceListener(jtfldGravityID);
    datasource.addDatasourceListener(jlbtnGravity);
    datasource.addDatasourceListener(jtfldGravity);
    datasource.addDatasourceListener(jlfldGravityDescription);
    datasource.addDatasourceListener(jtfldProbabilityID);
    datasource.addDatasourceListener(jlbtnProbability);
    datasource.addDatasourceListener(jtfldProbability);
    datasource.addDatasourceListener(jlfldProbabilityDescription);
    datasource.addDatasourceListener(jtfldRisk);
    datasource.addDatasourceListener(jtfldRiskID);
    datasource.addDatasourceListener(jlfldRiskDescription);
    datasource.addDatasourceListener(jtfldControlID);
    datasource.addDatasourceListener(jlbtnControl);
    datasource.addDatasourceListener(jtfldControl);
    datasource.addDatasourceListener(jlfldControlDescription);
    datasource.addDatasourceListener(jtfldSignificanceDescription);
    datasource.addDatasourceListener(jtfldSignificance);

    fwCListener.addFWComponentListener(jlbtnEnvAsp);
    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jlfldEnvAspDescription);
    fwCListener.addFWComponentListener(jtfldGravityID);
    fwCListener.addFWComponentListener(jlbtnGravity);
    fwCListener.addFWComponentListener(jtfldGravity);
    fwCListener.addFWComponentListener(jlfldGravityDescription);
    fwCListener.addFWComponentListener(jtfldProbabilityID);
    fwCListener.addFWComponentListener(jlbtnProbability);
    fwCListener.addFWComponentListener(jtfldProbability);
    fwCListener.addFWComponentListener(jlfldProbabilityDescription);
    fwCListener.addFWComponentListener(jtfldRisk);
    fwCListener.addFWComponentListener(jtfldRiskID);
    fwCListener.addFWComponentListener(jlfldRiskDescription);
    fwCListener.addFWComponentListener(jtfldControlID);
    fwCListener.addFWComponentListener(jlbtnControl);
    fwCListener.addFWComponentListener(jtfldControl);
    fwCListener.addFWComponentListener(jlfldControlDescription);
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

    jtfldGravityID.setField("gravityID");
    jtfldGravity.setField("gravity");

    jlbtnGravity.setText(res.getString("eaevaluationSiaMethod.label.gravity"));
    jlbtnGravity.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnGravity.setTitle(res.getString("eaevaluationSiaMethod.label.gravity"));
    jlbtnGravity.setLinkCondition("evalCriterionID = 'G'");
    JTextField[] txt = new JTextField[2];
    txt[0] = jtfldGravityID;
    txt[1] = jtfldGravity;
    jlbtnGravity.setFillList(txt);

    jlfldGravityDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldGravityDescription.setRefFieldList(txt);

    jtfldProbabilityID.setField("probabilityID");
    jtfldProbability.setField("probability");

    jlbtnProbability.setText(res.getString("eaevaluationSiaMethod.label.probability"));
    jlbtnProbability.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnProbability.setTitle(res.getString("eaevaluationSiaMethod.label.probability"));
    jlbtnProbability.setLinkCondition("evalCriterionID = 'P' ");
    JTextField[] txt2 = new JTextField[2];
    txt2[0] = jtfldProbabilityID;
    txt2[1] = jtfldProbability;
    jlbtnProbability.setFillList(txt2);

    jlfldProbabilityDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldProbabilityDescription.setRefFieldList(txt2);

    jlblRisk.setText(res.getString("eaevaluationSiaMethod.label.risk"));
    jtfldRiskID.setField("riskID");
    jtfldRisk.setField("risk");
    jtfldRisk.setHolder(jlblRisk);
    JTextField[] txt3 = new JTextField[2];
    txt3[0] = jtfldRiskID;
    txt3[1] = jtfldRisk;

    jlfldRiskDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldRiskDescription.setRefFieldList(txt3);

    jtfldControlID.setField("controlID");
    jtfldControl.setField("control");

    jlbtnControl.setText(res.getString("eaevaluationSiaMethod.label.control"));
    jlbtnControl.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnControl.setTitle(res.getString("eaevaluationSiaMethod.label.control"));
    jlbtnControl.setLinkCondition("evalCriterionID = 'CCA'");
    JTextField[] txt4 = new JTextField[2];
    txt4[0] = jtfldControlID;
    txt4[1] = jtfldControl;
    jlbtnControl.setFillList(txt4);

    jlfldControlDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldControlDescription.setRefFieldList(txt4);

    jlblSignificance.setText(res.getString("label.significance"));
    jtfldSignificanceDescription.setHolder(jlblSignificance);
    jtfldSignificanceDescription.setField("significanceDescription");
    jtfldSignificance.setField("significance");

    jbtnCalc.setText(res.getString("label.calc"));
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,50dlu,4dlu, 70dlu,4dlu, 50dlu,40dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref,2dlu,pref,5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    CellConstraints cc = new CellConstraints();
    content.add(jlbtnEnvAsp, cc.xy(2, 2));
    content.add(jtfldEnvAspID, cc.xy(4, 2));
    content.add(jlfldEnvAspDescription, cc.xyw(6, 2, 6));

    content.add(jlbtnGravity, cc.xy(2, 4));
    content.add(jtfldGravity, cc.xy(4, 4));
    content.add(jlfldGravityDescription, cc.xyw(6, 4, 6));

    content.add(jlbtnProbability, cc.xy(2, 6));
    content.add(jtfldProbability, cc.xy(4, 6));
    content.add(jlfldProbabilityDescription, cc.xyw(6, 6, 6));

    content.add(jlblRisk, cc.xy(2, 8));
    content.add(jtfldRisk, cc.xy(4, 8));
    content.add(jlfldRiskDescription, cc.xyw(6, 8, 6));

    content.add(jlbtnControl, cc.xy(2, 10));
    content.add(jtfldControl, cc.xy(4, 10));
    content.add(jlfldControlDescription, cc.xyw(6, 10, 6));

    content.add(jbtnCalc, cc.xy(4, 12));

    content.add(jlblSignificance, cc.xy(6, 12));
    content.add(jtfldSignificanceDescription, cc.xy(8, 12));
    content.add(jtfldSignificance, cc.xy(10, 12));

    jbtnCalc.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if ((!jtfldGravity.getText().equals("") && !jtfldProbability.getText().equals("") && !jtfldControl.getText().equals(""))) {
	  int[][] aaSiaRisk = new int[5][4];
	  int rsk = 0;
	  int grav = Integer.parseInt(jtfldGravity.getText());
	  int prob = Integer.parseInt(jtfldProbability.getText());
	  aaSiaRisk[0][0] = 1;
	  aaSiaRisk[0][1] = 1;
	  aaSiaRisk[0][2] = 2;
	  aaSiaRisk[0][3] = 3;
	  aaSiaRisk[1][0] = 1;
	  aaSiaRisk[1][1] = 1;
	  aaSiaRisk[1][2] = 2;
	  aaSiaRisk[1][3] = 3;
	  aaSiaRisk[2][0] = 1;
	  aaSiaRisk[2][1] = 2;
	  aaSiaRisk[2][2] = 3;
	  aaSiaRisk[2][3] = 4;
	  aaSiaRisk[3][0] = 2;
	  aaSiaRisk[3][1] = 3;
	  aaSiaRisk[3][2] = 3;
	  aaSiaRisk[3][3] = 4;
	  aaSiaRisk[4][0] = 3;
	  aaSiaRisk[4][1] = 3;
	  aaSiaRisk[4][2] = 3;
	  aaSiaRisk[4][3] = 4;
	  rsk = aaSiaRisk[prob - 1][grav - 1];
	  jtfldRiskID.setText("RIA");
	  jtfldRisk.setText(String.valueOf(rsk));

	  int[][] aaSiaEvaluation = new int[4][4];
	  aaSiaEvaluation[0][0] = 1;
	  aaSiaEvaluation[0][1] = 1;
	  aaSiaEvaluation[0][2] = 3;
	  aaSiaEvaluation[0][3] = 5;
	  aaSiaEvaluation[1][0] = 1;
	  aaSiaEvaluation[1][1] = 2;
	  aaSiaEvaluation[1][2] = 4;
	  aaSiaEvaluation[1][3] = 5;
	  aaSiaEvaluation[2][0] = 2;
	  aaSiaEvaluation[2][1] = 3;
	  aaSiaEvaluation[2][2] = 5;
	  aaSiaEvaluation[2][3] = 5;
	  aaSiaEvaluation[3][0] = 3;
	  aaSiaEvaluation[3][1] = 4;
	  aaSiaEvaluation[3][2] = 5;
	  aaSiaEvaluation[3][3] = 5;

	  int ctrl = Integer.parseInt(jtfldControl.getText());
	  int result = 0;
	  result = aaSiaEvaluation[ctrl - 1][rsk - 1];
	  if (result >= 1 && result <= 3) {
	    jtfldSignificanceDescription.setText(res.getString("label.Significant"));
	    jtfldSignificance.setText(String.valueOf(result));
	  }
	  else {
	    jtfldSignificanceDescription.setText(res.getString("label.NonSignificant"));
	    jtfldSignificance.setText(String.valueOf(result));
	  }
	}
      }
    });

    add(content, BorderLayout.NORTH);
  }
}
