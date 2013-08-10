package pt.inescporto.siasoft.tools;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJPasswordField;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JPanel;

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
public class OpenDSPanel extends JPanel {
  private TmplJLabel jlblDriver = new TmplJLabel();
  private TmplJTextField jtfldDriver = new TmplJTextField();
  private TmplJLabel jlblUrl = new TmplJLabel();
  private TmplJTextField jtfldUrl = new TmplJTextField();
  private TmplJLabel jlblUser = new TmplJLabel();
  private TmplJTextField jtfldUser = new TmplJTextField();
  private TmplJLabel jlblPasswd = new TmplJLabel();
  private TmplJPasswordField jtfldPasswd = new TmplJPasswordField();

  public OpenDSPanel() {
    try {
      initializeGUI();
    }
    catch (Exception ex) {
    }
  }
  private void initializeGUI() throws Exception {
    setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8}});
    CellConstraints cc = new CellConstraints();

    jlblDriver.setText("Driver");
    jtfldDriver.setText("org.postgresql.Driver");
    jlblUrl.setText("Connection string");
    jtfldUrl.setText("jdbc:postgresql://localhost/SIASoft");
    jlblUser.setText("User");
    jtfldUser.setText("SIASoft");
    jlblPasswd.setText("Password");
    jtfldPasswd.setText("canela");

    add(jlblDriver, cc.xy(2, 2));
    add(jtfldDriver, cc.xyw(4, 2, 2));

    add(jlblUrl, cc.xy(2, 4));
    add(jtfldUrl, cc.xyw(4, 4, 2));

    add(jlblUser, cc.xy(2, 6));
    add(jtfldUser, cc.xy(4, 6));

    add(jlblPasswd, cc.xy(2, 8));
    add(jtfldPasswd, cc.xy(4, 8));
  }

  public String getDriver() {
    return jtfldDriver.getText();
  }

  public String getUrl() {
    return jtfldUrl.getText();
  }

  public String getUser() {
    return jtfldUser.getText();
  }

  public String getPasswd() {
    return new String(jtfldPasswd.getPassword());
  }
}
