package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import javax.swing.JPanel;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplJRadioButton;
import pt.inescporto.template.client.design.TmplButtonGroup;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JFrame;
import java.awt.Container;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.template.client.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import javax.swing.JComponent;
import pt.inescporto.template.client.design.thirdparty.ComponentTitledBorder;
import javax.swing.BorderFactory;
import java.awt.SystemColor;

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
public class EAStabbedPane extends JPanel{
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  TmplJLabel jlblEnvAspID = new TmplJLabel();
  TmplJTextField jtfldEnvAspID = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  TmplJLabel jlblEnvAspName = new TmplJLabel();
  TmplJTextField jtfldEnvAspName = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  TmplJLabel jlblSignificance = new TmplJLabel();
  TmplJRadioButton jrbNormal = new TmplJRadioButton();
  TmplJRadioButton jrbNotNormal = new TmplJRadioButton();
  TmplJRadioButton jrbEmergency = new TmplJRadioButton();
  TmplButtonGroup bgOperation = new TmplButtonGroup();
  TmplJRadioButton jrbDirect = new TmplJRadioButton();
  TmplJRadioButton jrbIndirect = new TmplJRadioButton();
  TmplButtonGroup bgApplicability = new TmplButtonGroup();

  TmplJLabel jlblEvalSignificanceSIA = new TmplJLabel();

  TmplJLabel jlblEvalSignOtherMethod = new TmplJLabel();

  TmplJLabel jlblEvalSignLegalReq = new TmplJLabel();

  TmplJLabel jlblSignResult = new TmplJLabel();
  TmplJTextField jtfldSignResult = new TmplJTextField();

  FW_JPanelBasic content2 = new FW_JPanelBasic();

  TmplJRadioButton jrbSiaMethod = new TmplJRadioButton();
  TmplJRadioButton jrbOtherMethod = new TmplJRadioButton() ;
//  TmplJRadioButton jrbOtherMatrixMethod = new TmplJRadioButton();

  TmplJCheckBox jckbOther = new TmplJCheckBox(){
    public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
        case TmplFormModes.MODE_SELECT:
          this.setEnabled(false);
          break;
        case TmplFormModes.MODE_INSERT:
          this.setEnabled(false);
          break;
        case TmplFormModes.MODE_UPDATE:
          this.setEnabled(true);
          break;
        case TmplFormModes.MODE_FIND:
          this.setEnabled(false);
          break;
      }
    }
  };

 /* TmplJButton jbtnShowSignificance=new TmplJButton(){
    public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
        case TmplFormModes.MODE_SELECT:
          this.setEnabled(true);
          break;
        case TmplFormModes.MODE_INSERT:
          this.setEnabled(false);
          break;
        case TmplFormModes.MODE_UPDATE:
          this.setEnabled(true);
          break;
        case TmplFormModes.MODE_FIND:
          this.setEnabled(false);
          break;
      }
    }
  };
  TmplJButton jbtnHideSignificance=new TmplJButton(){
    public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
        case TmplFormModes.MODE_SELECT:
          this.setEnabled(true);
          if(content2.isVisible()){
            content2.setVisible(false);
            this.setVisible(false);
            jbtnShowSignificance.setVisible(true);
          }
          break;
        case TmplFormModes.MODE_INSERT:
          this.setEnabled(true);
          if(content2.isVisible()){
           content2.setVisible(false);
           this.setVisible(false);
           jbtnShowSignificance.setVisible(true);
         }
          break;
        case TmplFormModes.MODE_UPDATE:
          this.setEnabled(true);
          break;
        case TmplFormModes.MODE_FIND:
	  this.setEnabled(true);
	  if (content2.isVisible()) {
	    content2.setVisible(false);
	    this.setVisible(false);
	    jbtnShowSignificance.setVisible(true);
	  }
          break;
      }
    }
  };*/
 TmplJButton jbtnEditOtherSign=new TmplJButton(){
   public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
       case TmplFormModes.MODE_SELECT:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_INSERT:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_UPDATE:
         this.setVisible(true);
         this.setEnabled(true);
	 break;
       case TmplFormModes.MODE_FIND:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
     }
   }
 };
 TmplJButton jbtnEditOtherMethod=new TmplJButton(){
   public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
       case TmplFormModes.MODE_SELECT:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_INSERT:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_UPDATE:
         this.setVisible(true);
         this.setEnabled(true);
	 break;
       case TmplFormModes.MODE_FIND:
         this.setVisible(false);
         this.setEnabled(false);
	 break;
     }
   }
 };

 TmplJButton jbtnEditSignificanceSIA=new TmplJButton(){
   public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
       case TmplFormModes.MODE_SELECT:
	 this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_INSERT:
	 this.setVisible(false);
         this.setEnabled(false);
	 break;
       case TmplFormModes.MODE_UPDATE:
	 this.setVisible(true);
         this.setEnabled(true);
	 break;
       case TmplFormModes.MODE_FIND:
	 this.setVisible(false);
         this.setEnabled(false);
	 break;
     }
   }
 };


  TmplButtonGroup bgSignificance = new TmplButtonGroup(){
    public void tmplMode(TemplateEvent e) {
      switch (e.getMode()) {
        case TmplFormModes.MODE_SELECT:
          this.setEnabled(false);
          break;
        case TmplFormModes.MODE_INSERT:
          this.setEnabled(false);
          break;
        case TmplFormModes.MODE_UPDATE:
          this.setEnabled(true);
          break;
        case TmplFormModes.MODE_FIND:
          this.setEnabled(false);
          break;
      }
    }
  };


  public EAStabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jtfldEnvAspName);
    datasource.addDatasourceListener(bgOperation);
    datasource.addDatasourceListener(bgApplicability);
    datasource.addDatasourceListener(bgSignificance);
    datasource.addDatasourceListener(jckbOther);
    datasource.addDatasourceListener(jtfldSignResult);

    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jtfldEnvAspName);
    fwCListener.addFWComponentListener(bgOperation);
    fwCListener.addFWComponentListener(bgApplicability);
    fwCListener.addFWComponentListener(bgSignificance);
    fwCListener.addFWComponentListener(jckbOther);
    fwCListener.addFWComponentListener(jbtnEditSignificanceSIA);
    fwCListener.addFWComponentListener(jbtnEditOtherMethod);
    fwCListener.addFWComponentListener(jbtnEditOtherSign);
    //fwCListener.addFWComponentListener(jbtnHideSignificance);

    fwCListener.addFWComponentListener(jtfldSignResult);
  }
  private void initialize() {
    removeAll();
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblEnvAspID.setText(res.getString("envAsp.label.code"));
    jtfldEnvAspID.setField("envAspID");
    jtfldEnvAspID.setHolder(jlblEnvAspID);

    jlblEnvAspName.setText(res.getString("envAsp.label.name"));
    jtfldEnvAspName.setField("envAspName");
    jtfldEnvAspName.setHolder(jlblEnvAspName);

    jlblSignificance.setText(res.getString("envAsp.label.significance"));

    jrbNormal.setText(res.getString("envAsp.label.operation.normal"));
    jrbNormal.setValue("N");
    jrbNotNormal.setText(res.getString("envAsp.label.operation.notNormal"));
    jrbNotNormal.setValue("A");
    jrbEmergency.setText(res.getString("envAsp.label.operation.emergency"));
    jrbEmergency.setValue("E");
    bgOperation.setField("functionality");
    bgOperation.add(jrbNormal);
    bgOperation.add(jrbNotNormal);
    bgOperation.add(jrbEmergency);

    jrbDirect.setText(res.getString("envAsp.label.applicabitity.direct"));
    jrbDirect.setValue("D");
    jrbIndirect.setText(res.getString("envAsp.label.applicabitity.indirect"));
    jrbIndirect.setValue("I");
    bgApplicability.setField("applicability");
    bgApplicability.add(jrbDirect);
    bgApplicability.add(jrbIndirect);

    jtfldSignResult.setEditable(false);
    jtfldSignResult.setField("significanceDesc");
    jlblSignResult.setText("Significancia:");

    jlblEvalSignificanceSIA.setText(res.getString("label.siaMethod"));

    jlblEvalSignOtherMethod.setText(res.getString("label.otherMethod"));

    jlblEvalSignLegalReq.setText(res.getString("label.otherSignificance"));


    jbtnEditOtherSign.setVisible(false);
    jbtnEditOtherMethod.setVisible(false);
    jbtnEditSignificanceSIA.setVisible(false);
    //jbtnHideSignificance.setVisible(false);

    jbtnEditOtherSign.setText(res.getString("label.Edit"));
    jbtnEditOtherMethod.setText(res.getString("label.Edit"));
    jbtnEditSignificanceSIA.setText(res.getString("label.Edit"));
    //jbtnShowSignificance.setText(res.getString("label.EvaluatMethods"));
    //jbtnHideSignificance.setText(res.getString("label.HideEvaluatMethods"));

    jckbOther.setField("otherSignificance");
    jckbOther.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});
    jckbOther.setOpaque(false);

    jrbSiaMethod.setValue("S");
    jrbOtherMethod.setValue("C");
    //jrbOtherMatrixMethod.setValue("M");

    bgSignificance.setField("significanceMethod");
    bgSignificance.add(jrbSiaMethod);
    bgSignificance.add(jrbOtherMethod);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu, pref, left:60dlu, 5dlu, 70dlu, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 10dlu, 50dlu, 10dlu, pref,5px");

    FormLayout formLayout2 = new FormLayout("5px,left:pref,4dlu, pref,4dlu, 70dlu, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    content2.setOpaque(false);
    content2.setLayout(formLayout2);

    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jlblEnvAspName, cc.xy(2, 2));
    content.add(jtfldEnvAspName, cc.xyw(4, 2, 3));
    content.add(jlblEnvAspID, cc.xy(8, 2));
    content.add(jtfldEnvAspID, cc.xy(10, 2));

    content.add(jlblSignificance, cc.xy(2, 4));

    content.add(jrbNormal, cc.xy(4, 6));
    content.add(jrbNotNormal, cc.xy(4, 8));
    content.add(jrbEmergency, cc.xy(4, 10));

    content.add(jrbDirect, cc.xy(6, 6));
    content.add(jrbIndirect, cc.xy(6, 8));

    //content.add(jbtnShowSignificance, cc.xyw(7, 4, 3));
    //content.add(jbtnHideSignificance, cc.xyw(7, 4, 3));
    content2.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));

    content2.add(jrbSiaMethod, cc.xy(2, 2));
    content2.add(jlblEvalSignificanceSIA, cc.xy(4, 2));
    content2.add(jbtnEditSignificanceSIA, cc.xy(6, 2));

    content2.add(jrbOtherMethod, cc.xy(2, 4));
    content2.add(jlblEvalSignOtherMethod, cc.xy(4, 4));
    content2.add(jbtnEditOtherMethod, cc.xy(6, 4));

    content2.add(jckbOther, cc.xy(2, 6));
    content2.add(jlblEvalSignLegalReq, cc.xy(4, 6));
    content2.add(jbtnEditOtherSign, cc.xy(6, 6));

    content.add(content2 ,cc.xywh(2,12,6,2));

    content.add(jlblSignResult, cc.xy(8, 12));
    content.add(jtfldSignResult, cc.xy(10, 12));

    content2.setVisible(true);

   /* jbtnShowSignificance.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            content2.setVisible(true);
            jbtnShowSignificance.setVisible(false);
            jbtnHideSignificance.setVisible(true);
          }
    });
    jbtnHideSignificance.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	content2.setVisible(false);
	jbtnShowSignificance.setVisible(true);
	jbtnHideSignificance.setVisible(false);
      }
    });*/


    jrbSiaMethod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
          // jbtnEditSignificanceSIA.setVisible(true);
          // jbtnEditOtherMethod.setVisible(false);
	 /* JFrame frame = new JFrame();
	  EAEvaluationSIAMethodDialog diag = null;
	  try {
	    diag = new EAEvaluationSIAMethodDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
            datasource.refresh();
	  }
	  catch (DataSourceException ex1) {
	    ex1.printStackTrace();
	  }
	  diag.setTitle(res.getString("label.siaDiag"));
	  diag.pack();
	  diag.setEnabled(true);
	  diag.setVisible(true);*/
	}

      }
    });
    jrbOtherMethod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
       //   jbtnEditSignificanceSIA.setVisible(false);
       //   jbtnEditOtherMethod.setVisible(true);
	  /*JFrame frame = new JFrame();
	  EAEvaluationOtherMethodDialog diag = null;
	  try {
	    diag = new EAEvaluationOtherMethodDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
            datasource.refresh();
	  }
	  catch (DataSourceException ex2) {
	  }
	  diag.setTitle(res.getString("label.otherMethodDiag"));
	  diag.pack();
	  diag.setEnabled(true);
	  diag.setVisible(true);*/
	}
      }
    });

  /*  jrbOtherMatrixMethod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
	  JFrame frame = new JFrame();
	  EAEvaluationOtherMatrixMethodDialog diag = null;
	  try {
	    diag = new EAEvaluationOtherMatrixMethodDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
            datasource.refresh();
	  }
	  catch (DataSourceException ex3) {
	  }
	  diag.setTitle(res.getString("label.otherMatrixMethodDiag"));
	  diag.pack();
	  diag.setEnabled(true);
	  diag.setVisible(true);
	}
      }
    });*/

   jckbOther.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJCheckBox)e.getSource()).isSelected()) {
	  //JFrame frame = new JFrame();
        //  jbtnEditOtherSign.setVisible(true);
	  /*EAEvaluationOtherSignificantDialog diag = null;
	  try {
	    diag = new EAEvaluationOtherSignificantDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
            datasource.refresh();
	  }
	  catch (DataSourceException ex4) {
	  }
	  diag.setTitle(res.getString("label.otherSignificanceDiag"));
	  diag.pack();
	  diag.setEnabled(true);
	  diag.setVisible(true);*/
          ((TmplJCheckBox)e.getSource()).setSelected(true);

	}
        else{
        //  jbtnEditOtherSign.setVisible(false);
          ((TmplJCheckBox)e.getSource()).setSelected(false);
        }

      }
    });
    jbtnEditSignificanceSIA.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame();
         EAEvaluationSIAMethodDialog diag = null;
         try {
           diag = new EAEvaluationSIAMethodDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
           datasource.refresh();
         }
         catch (DataSourceException ex1) {
           ex1.printStackTrace();
         }
         diag.setTitle(res.getString("label.siaDiag"));
         diag.pack();
         diag.setEnabled(true);
         diag.setVisible(true);

      }
    });

    jbtnEditOtherMethod.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame();
         EAEvaluationOtherMethodDialog diag = null;
         try {
           diag = new EAEvaluationOtherMethodDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
           datasource.refresh();
         }
         catch (DataSourceException ex2) {
         }
         diag.setTitle(res.getString("label.otherMethodDiag"));
         diag.pack();
         diag.setEnabled(true);
         diag.setVisible(true);

      }
    });

    jbtnEditOtherSign.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          EAEvaluationOtherSignificantDialog diag = null;
          try {
            diag = new EAEvaluationOtherSignificantDialog(frame, ((EnvironmentAspectDao)datasource.getCurrentRecord()).envAspectID);
            datasource.refresh();
          }
          catch (DataSourceException ex4) {
          }
          diag.setTitle(res.getString("label.otherSignificanceDiag"));
          diag.pack();
          diag.setEnabled(true);
          diag.setVisible(true);
        }
    });

    add(content, BorderLayout.NORTH);
  }
}
