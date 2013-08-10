package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJDatePicker;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import pt.inescporto.template.client.design.TmplJCheckBox;

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
public class SearchRegulatoryFullPane extends JPanel {
  JPanel jpnlContent = new JPanel();
  TmplJLabel jlblName = new TmplJLabel();
  public TmplJTextField jtfldName = new TmplJTextField();
  TmplJLabel jlblResume = new TmplJLabel();
  public TmplJTextField jtfldResume = new TmplJTextField();
  TmplJButton jbtnSearch = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));
  TmplJButton jbtnLessOptions = new TmplJButton();
  TmplJLabel jlblDataIni = new TmplJLabel();
  TmplJDatePicker jdpckDataIni = new TmplJDatePicker();
  TmplJLabel jlblDataFim = new TmplJLabel();
  TmplJDatePicker jdpckDataFim = new TmplJDatePicker();
  TmplJLabel jlblScope = new TmplJLabel();
  public TmplJTextField jtfldScope = new TmplJTextField();
  TmplJLabel jlblSource = new TmplJLabel();
  public TmplJTextField jtfldSource = new TmplJTextField();
  TmplJLabel jlblTheme = new TmplJLabel();
  public TmplJTextField jtfldTheme = new TmplJTextField();
  TmplJLabel jlblTheme1 = new TmplJLabel();
  public TmplJTextField jtfldTheme1 = new TmplJTextField();
  TmplJLabel jlblAplicability = new TmplJLabel();
  public TmplJCheckBox jcheckAplicability = new TmplJCheckBox();
  TmplJLabel jlblHasLegReq = new TmplJLabel();
  public TmplJCheckBox jcheckHasLegReq = new TmplJCheckBox();
  TmplJLabel jlblEffective = new TmplJLabel();
  public TmplJCheckBox jcheckEffective  = new TmplJCheckBox();
  TmplJLabel jlblOther = new TmplJLabel();
  public TmplJCheckBox jcheckOther = new TmplJCheckBox();

  public SearchRegulatoryFullPane(TmplJButton jbtnSearch,TmplJButton jbtnLessOptions) {
    this.jbtnSearch = jbtnSearch;
    this.jbtnLessOptions=jbtnLessOptions;
    try {
      initialize();
    }
    catch (Exception ex) {
    }
  }

  private void initialize() throws Exception {
    jlblName.setText("Nome:");
    jlblResume.setText("Sumário:");
    jlblDataIni.setText("De:");
    jlblDataFim.setText("Até:");
    jlblScope.setText("Âmbito:");
    jlblSource.setText("Fonte:");
    jlblTheme.setText("Tema:");
    jlblTheme1.setText("Sub-Tema:");
    jbtnLessOptions.setText("Menos Opções");
    jlblAplicability.setText("Aplicabilidade:");
    jlblHasLegReq.setText("Com RL:");
    jlblEffective.setText("Vigente:");
    jlblOther.setText("Rev. /N.V.");

    jcheckEffective.setSelected(true);;

    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, right:pref, 4dlu, 150dlu:grow, 35dlu, 4dlu, center:pref, 5px",
					   "5px, pref, 4dlu, pref,4dlu, pref,4dlu, pref,4dlu, pref,4dlu, pref,4dlu, pref, 4dlu, 40dlu,4dlu, pref,4dlu, pref,5px");
    jpnlContent.setLayout(formLayout);

    FormLayout formLayout2 = new FormLayout("30dlu, right:pref, 4dlu,pref, 40dlu, right:pref, 4dlu,pref, 5px",
					    "5px, pref, 5px");

    FormLayout formLayout3 = new FormLayout("30dlu, right:pref, 4dlu,pref, 40dlu, right:pref, 4dlu,pref, 5px",
					    "5px, pref, 4dlu, pref, 5px");

    JPanel datePrev = new JPanel();
    datePrev.setOpaque(false);
    datePrev.setLayout(formLayout2);

    JPanel checkBoxs = new JPanel();
    checkBoxs.setOpaque(false);
    checkBoxs.setLayout(formLayout3);

    //   formLayout.setRowGroups(new int[][] { {2, 4,8,10,12,14}
    // });
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblName, cc.xy(2, 2));
    jpnlContent.add(jtfldName, cc.xyw(4, 2,2));

    jpnlContent.add(jlblResume, cc.xy(2, 4));
    jpnlContent.add(jtfldResume, cc.xyw(4, 4,2));
    jdpckDataIni.setSelectedDate(null);
    jdpckDataFim.setSelectedDate(null);

    datePrev.setBorder(BorderFactory.createTitledBorder(""));
    ((TitledBorder)datePrev.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    datePrev.add(jlblDataIni, cc.xy(2, 2));
    datePrev.add(jdpckDataIni, cc.xy(4, 2));
    datePrev.add(jlblDataFim, cc.xy(6, 2));
    datePrev.add(jdpckDataFim, cc.xy(8, 2));

    jpnlContent.add(datePrev, cc.xyw(2, 6, 6));

    jpnlContent.add(jlblScope, cc.xy(2, 8));
    jpnlContent.add(jtfldScope, cc.xyw(4, 8,2));

    jpnlContent.add(jlblSource, cc.xy(2, 10));
    jpnlContent.add(jtfldSource, cc.xyw(4, 10,2));

    jpnlContent.add(jlblTheme, cc.xy(2, 12));
    jpnlContent.add(jtfldTheme, cc.xyw(4, 12,2));

    jpnlContent.add(jlblTheme1, cc.xy(2, 14));
    jpnlContent.add(jtfldTheme1, cc.xyw(4, 14,2));

    checkBoxs.setBorder(BorderFactory.createTitledBorder(""));
    ((TitledBorder)checkBoxs.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    checkBoxs.add(jlblAplicability, cc.xy(2, 2));
    checkBoxs.add(jcheckAplicability, cc.xy(4, 2));
    checkBoxs.add(jlblHasLegReq, cc.xy(6, 2));
    checkBoxs.add(jcheckHasLegReq, cc.xy(8, 2));
    checkBoxs.add(jlblEffective, cc.xy(2, 4));
    checkBoxs.add(jcheckEffective, cc.xy(4, 4));
    checkBoxs.add(jlblOther, cc.xy(6, 4));
    checkBoxs.add(jcheckOther, cc.xy(8, 4));

    jpnlContent.add(checkBoxs, cc.xywh(2, 16, 4,3));


    jpnlContent.add(jbtnSearch, cc.xywh(7, 16, 1, 3, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jbtnLessOptions, cc.xyw(5, 20, 3, CellConstraints.FILL, CellConstraints.FILL));


    add(jpnlContent, BorderLayout.CENTER);
  }

}
