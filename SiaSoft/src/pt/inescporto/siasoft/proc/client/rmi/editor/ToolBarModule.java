package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import pt.inescporto.template.client.design.*;
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
public class ToolBarModule extends JPanel implements ActionListener{
  private ButtonGroup jbtng = new ButtonGroup();
  private ModuleListener mListener;

  private TmplJRadioButton jtbtnMproc = new TmplJRadioButton();
  private TmplJRadioButton jtbtnOp = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmAuditor = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmPGA = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmAA = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmMonitor = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmFun = new TmplJRadioButton();
  private TmplJRadioButton jtbtnsmCOEmerg = new TmplJRadioButton();
  private TmplJRadioButton jtbtnMasq = new TmplJRadioButton();
  private TmplJRadioButton jtbtnMdoc = new TmplJRadioButton();
  private TmplJRadioButton jtbtnMcom = new TmplJRadioButton();
  private TmplJRadioButton jtbtnMform = new TmplJRadioButton();

  public ToolBarModule(ModuleListener mListener) {
    super();
    this.mListener = mListener;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    setOpaque(false);
    BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    setLayout(layout);

    jtbtnMproc.setText("MProc");
    jtbtnMproc.setActionCommand("MProc");
    jtbtnMproc.addActionListener(this);
    jtbtnMproc.setSelected(true);

    jtbtnsmAA.setText("smAA");
    jtbtnsmAA.addActionListener(this);
    jtbtnsmAA.setActionCommand("smAA");

    jtbtnsmAuditor.setText("smAuditor");
    jtbtnsmAuditor.addActionListener(this);
    jtbtnsmAuditor.setActionCommand("smAuditor");

    jtbtnsmCOEmerg.setText("smCO_EMERG");
    jtbtnsmCOEmerg.addActionListener(this);
    jtbtnsmCOEmerg.setActionCommand("smCO_EMERG");

    jtbtnsmFun.setText("smFun");
    jtbtnsmFun.addActionListener(this);
    jtbtnsmFun.setActionCommand("smFun");

    jtbtnsmMonitor.setText("smMonitor");
    jtbtnsmMonitor.addActionListener(this);
    jtbtnsmMonitor.setActionCommand("smMonitor");

    jtbtnsmPGA.setText("smPGA");
    jtbtnsmPGA.addActionListener(this);
    jtbtnsmPGA.setActionCommand("smPGA");

    /*
    jtbtnOp.setText("MGOp");
    jtbtnOp.setActionCommand("MGOp");
    jtbtnOp.addActionListener(this);
    */

    jtbtnMasq.setText("MASQ");
    jtbtnMasq.setActionCommand("MASQ");
    jtbtnMasq.addActionListener(this);

    jtbtnMdoc.setText("MDoc");
    jtbtnMdoc.setActionCommand("MDoc");
    jtbtnMdoc.addActionListener(this);

    jtbtnMcom.setText("MCom");
    jtbtnMcom.setActionCommand("MCom");
    jtbtnMcom.addActionListener(this);

    jtbtnMform.setText("MForm");
    jtbtnMform.setActionCommand("MForm");
    jtbtnMform.addActionListener(this);

    jbtng.add(jtbtnMproc);
    jbtng.add(jtbtnOp);
    jbtng.add(jtbtnMasq);
    jbtng.add(jtbtnMdoc);
    jbtng.add(jtbtnMcom);
    jbtng.add(jtbtnMform);
    jbtng.add(jtbtnsmAA);
    jbtng.add(jtbtnsmAuditor);
    jbtng.add(jtbtnsmCOEmerg);
    jbtng.add(jtbtnsmFun);
    jbtng.add(jtbtnsmMonitor);
    jbtng.add(jtbtnsmPGA);

    add(jtbtnMproc);
    add(jtbtnsmAA);
    add(jtbtnsmAuditor);
    add(jtbtnsmCOEmerg);
    add(jtbtnsmFun);
    add(jtbtnsmMonitor);
    add(jtbtnsmPGA);

    add(jtbtnMasq);
    add(jtbtnMdoc);
    add(jtbtnMcom);
    add(jtbtnMform);
  }

  public void actionPerformed(ActionEvent e) {
    mListener.modelChanged(e.getActionCommand());
  }

  public TmplJRadioButton getJtbtnMasq() {
    return jtbtnMasq;
  }

  public TmplJRadioButton getJtbtnMcom() {
    return jtbtnMcom;
  }

  public TmplJRadioButton getJtbtnMdoc() {
    return jtbtnMdoc;
  }

  public TmplJRadioButton getJtbtnMform() {
    return jtbtnMform;
  }

  public TmplJRadioButton getJtbtnOp() {
    return jtbtnOp;
  }

  public TmplJRadioButton getJtbtnMproc() {
    return jtbtnMproc;
  }
}
