package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJRadioButton;
import pt.inescporto.template.client.design.TmplButtonGroup;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJComboBox;

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
public class EAEvaluationOtherSignificance extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnvAsp = new TmplLookupButton();
  TmplJTextField jtfldEnvAspID = new TmplJTextField();
  TmplLookupField jlfldEnvAspDescription = new TmplLookupField();

  TmplLookupButton jlbtnRegulatory = new TmplLookupButton();
  TmplJTextField jtfldRegulatoryID = new TmplJTextField();
  TmplLookupField jlfldRegulatoryDescription = new TmplLookupField();

  TmplJLabel jlblOtherSignificance = new TmplJLabel();
  TmplJRadioButton jrbLegalReq = new TmplJRadioButton();
  TmplJRadioButton jrbOther = new TmplJRadioButton();
  TmplButtonGroup bgSignificance = new TmplButtonGroup();

  TmplJComboBox jcmbSignificanceDescription = new TmplJComboBox();

  public EAEvaluationOtherSignificance(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnRegulatory);
    datasource.addDatasourceListener(jtfldRegulatoryID);
    datasource.addDatasourceListener(jlfldRegulatoryDescription);
    datasource.addDatasourceListener(jlbtnEnvAsp);
    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jlfldEnvAspDescription);
    datasource.addDatasourceListener(bgSignificance);
    datasource.addDatasourceListener(jcmbSignificanceDescription);

    fwCListener.addFWComponentListener(jlbtnEnvAsp);
    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jlfldEnvAspDescription);
    fwCListener.addFWComponentListener(jlbtnRegulatory);
    fwCListener.addFWComponentListener(jtfldRegulatoryID);
    fwCListener.addFWComponentListener(jlfldRegulatoryDescription);
    fwCListener.addFWComponentListener(bgSignificance);
    fwCListener.addFWComponentListener(jcmbSignificanceDescription);
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

    jtfldRegulatoryID.setField("regID");

    jlbtnRegulatory.setText(res.getString("eaevaluationOtherMethod.label.regulatory"));
    jlbtnRegulatory.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlbtnRegulatory.setTitle(res.getString("eaevaluationOtherMethod.label.regulatoryList"));
    jlbtnRegulatory.setDefaultFill(jtfldRegulatoryID);

    jlfldRegulatoryDescription.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlfldRegulatoryDescription.setDefaultRefField(jtfldRegulatoryID);


    jlblOtherSignificance.setText(res.getString("eaevaluationOtherMethod.label.otherSign"));

    jrbLegalReq.setText(res.getString("eaevaluationOtherMethod.label.legalReq"));
    jrbLegalReq.setValue("V");
    jrbOther.setText(res.getString("eaevaluationOtherMethod.label.other"));
    jrbOther.setValue("N");
    bgSignificance.setField("type");
    bgSignificance.add(jrbLegalReq);
    bgSignificance.add(jrbOther);

    jcmbSignificanceDescription.setField("significance");
    jcmbSignificanceDescription.setDataItems(new Object[] {"", res.getString("label.Significant"), res.getString("label.NonSignificant")});
    jcmbSignificanceDescription.setDataValues(new Object[] {null, "S", "NS"});

    /*jrbLegalReq.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      if (((TmplJRadioButton)e.getSource()).isSelected()) {
      }
      }
      });

      jrbOther.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      if (((TmplJRadioButton)e.getSource()).isSelected()) {
      }
      }
      });*/

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,50dlu,4dlu, 70dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref,2dlu,pref,2dlu,pref,5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    CellConstraints cc = new CellConstraints();
    content.add(jlbtnEnvAsp, cc.xy(2, 2));
    content.add(jtfldEnvAspID, cc.xy(4, 2));
    content.add(jlfldEnvAspDescription, cc.xyw(6, 2, 3));
    content.add(jlbtnRegulatory, cc.xy(2, 4));
    content.add(jtfldRegulatoryID, cc.xy(4, 4));
    content.add(jlfldRegulatoryDescription, cc.xyw(6, 4, 3));


    content.add(jlblOtherSignificance, cc.xy(2, 6));
    content.add(jcmbSignificanceDescription, cc.xy(4, 6));
    content.add(jrbLegalReq, cc.xy(2, 8));
    content.add(jrbOther, cc.xy(2, 10));

    add(content, BorderLayout.NORTH);
  }
}
