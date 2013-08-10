package pt.inescporto.siasoft;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.*;
import javax.swing.BorderFactory;
import java.awt.Font;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.rmi.MenuSingleton;

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
public class StatusBar extends JPanel implements EventSynchronizerWatcher {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jlblStatus = new JLabel();
  JLabel jlblProfile = new JLabel();

  public StatusBar() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, SyncronizerSubjects.sysChangeProfile);
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    jlblStatus.setFont(new java.awt.Font("Dialog", Font.PLAIN, 10));
    jlblStatus.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    jlblProfile.setFont(new java.awt.Font("Dialog", Font.PLAIN, 10));
    jlblProfile.setBorder(BorderFactory.createLoweredBevelBorder());
    this.add(jlblStatus, new GridBagConstraints(0, 0, 1, 1, 200.0, 0.0
						, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 300, 0));
    this.add(jlblProfile, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
						 , GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 100, 0));
  }

  public void setStatus(String msg) {
    jlblStatus.setText(msg);
  }

  public void setProfile(String profile) {
    jlblProfile.setText(profile);
  }

  public void eventSynchronizerTriggered(String subject) {
    if (subject.equals(SyncronizerSubjects.sysChangeProfile)) {
      if (MenuSingleton.getUserData().getApplicableProfileData().getProfile() == null) {
        setProfile("");
      }
      else {
	String profileID = MenuSingleton.getUserData().getApplicableProfileData().getProfile() == null ? "" : MenuSingleton.getUserData().getApplicableProfileData().getProfile().profileID.getValue();
	String userID = MenuSingleton.getUserData().getApplicableProfileData().getProfile().userID.getValue();
	setProfile(profileID + " [" + userID + "]");
      }
    }
  }
}
