package pt.inescporto.siasoft.tools;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Hashtable;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

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
public class DBAnalizer {
  public DBAnalizer() {
    AnalizerGUI frame = new AnalizerGUI();

    frame.validate();

    // Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    DBAnalizer row = new DBAnalizer();
/*    try {
      row.create("envaspothermethod");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }*/
  }
}
