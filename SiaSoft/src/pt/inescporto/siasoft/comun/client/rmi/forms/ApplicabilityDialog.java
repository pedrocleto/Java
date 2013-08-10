package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.TmplJCheckBoxBit;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import pt.inescporto.template.client.design.TmplButtonGroupBit;
import pt.inescporto.template.elements.TplInt;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.ResourceBundle;

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
public class ApplicabilityDialog extends JDialog implements ActionListener {
  private Integer value = null;

  private JPanel panel1 = new JPanel();
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();

  private TmplButtonGroupBit btngrpApp = new TmplButtonGroupBit();
  private TmplJCheckBoxBit jchkbDirect = null;
  private TmplJCheckBoxBit jchkbIndirect = null;
  private TmplJCheckBoxBit jchkbInform = null;

  private JPanel jpnlButtons = new JPanel();
  private JButton jbtnOk = new JButton();
  private JButton jbtnCancel = new JButton();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  public ApplicabilityDialog(Frame owner, String title, boolean modal, Integer value) {
    super(owner, title, true);
    this.value = value;

    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getPreferredSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  public ApplicabilityDialog(Frame owner, Integer value) {
    this(owner, res.getString("app.label.title"), true, value);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);

    jchkbDirect = new TmplJCheckBoxBit();
    jchkbDirect.setText(res.getString("app.label.directly"));
    jchkbDirect.setBit(UserProfileDao.DIRECT_APP);
    jchkbDirect.setActionCommand("APP_DIR");
    jchkbDirect.addActionListener(this);

    jchkbIndirect = new TmplJCheckBoxBit();
    jchkbIndirect.setText(res.getString("app.label.indirectly"));
    jchkbIndirect.setBit(UserProfileDao.INDIRECT_APP);
    jchkbIndirect.setActionCommand("APP_IND");
    jchkbIndirect.addActionListener(this);
    jchkbIndirect.setEnabled(false);

    jchkbInform = new TmplJCheckBoxBit();
    jchkbInform.setLabel(res.getString("app.label.info"));
    jchkbInform.setBit(UserProfileDao.INFORM_APP);
    jchkbInform.setEnabled(false);

    btngrpApp = new TmplButtonGroupBit();
    btngrpApp.add(jchkbDirect);
    btngrpApp.add(jchkbIndirect);
    btngrpApp.add(jchkbInform);
    btngrpApp.setLink(new TplInt(value));

    FlowLayout flowLayout = new FlowLayout();
    flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
    jPanel1 = new JPanel();
    jPanel1.setLayout(flowLayout);
    jPanel1.add(jchkbDirect, null);
    jPanel1.add(jchkbIndirect, null);
    jPanel1.add(jchkbInform, null);

    jbtnOk.setText("OK");
    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);
    jpnlButtons.add(jbtnOk);
    jbtnCancel.setText(res.getString("label.cancel"));
    jbtnCancel.setActionCommand("CANCEL");
    jbtnCancel.addActionListener(this);
    jpnlButtons.add(jbtnCancel);

    panel1.add(jPanel1, BorderLayout.CENTER);
    panel1.add(jpnlButtons, BorderLayout.SOUTH);
  }

  public int getValue() {
    return value.intValue();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("OK")) {
      btngrpApp.tmplSave(new TemplateEvent(this));
      value = btngrpApp.getLink().getValue();
      dispose();
    }
    if (e.getActionCommand().equals("CANCEL")) {
      value = -1;
      dispose();
    }
    if (e.getActionCommand().equals("APP_DIR")) {
      if (jchkbDirect.isSelected()) {
        jchkbIndirect.setEnabled(true);
      }
      else {
        jchkbIndirect.setEnabled(false);
        jchkbInform.setEnabled(false);
        jchkbIndirect.setSelected(false);
        jchkbInform.setSelected(false);
      }
    }
    if (e.getActionCommand().equals("APP_IND")) {
      if (jchkbIndirect.isSelected()) {
        jchkbInform.setEnabled(true);
      }
      else {
        jchkbIndirect.setSelected(false);
        jchkbInform.setSelected(false);
      }
    }
  }
}

