package pt.inescporto.siasoft.tools;

import javax.swing.*;
import pt.inescporto.template.client.design.TmplJButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Dimension;

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
public class OpenDialogDS extends JDialog implements ActionListener{
  private OpenDSPanel pnlDS = new OpenDSPanel();
  private TmplJButton jbtnConnect = new TmplJButton();

  public OpenDialogDS(Frame owner, String title) {
    super(owner, title, true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    try {
      initializeGUI();
    }
    catch (Exception ex) {
    }

    // adjust dialog to center of screen
    Dimension screen = owner.getSize();
    int x = (owner.getX() + screen.width - (int)getPreferredSize().getWidth()) / 2;
    int y = (owner.getY() + screen.height - (int)getPreferredSize().getHeight()) / 2;
    setLocation(x, y);
  }

  private void initializeGUI() throws Exception {
    jbtnConnect.setText("Connect");
    jbtnConnect.setActionCommand("CONNECT");
    jbtnConnect.addActionListener(this);

    setLayout(new BorderLayout());
    add(pnlDS, BorderLayout.CENTER);
    add(jbtnConnect, BorderLayout.SOUTH);
  }

  public String getDriver() {
    return pnlDS.getDriver();
  }

  public String getUrl() {
    return pnlDS.getUrl();
  }

  public String getUser() {
    return pnlDS.getUser();
  }

  public String getPasswd() {
    return pnlDS.getPasswd();
  }

  public void actionPerformed(ActionEvent e) {
    dispose();
  }
}
