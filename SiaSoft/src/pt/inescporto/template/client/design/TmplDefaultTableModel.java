package pt.inescporto.template.client.design;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class TmplDefaultTableModel extends DefaultTableModel {
  public void setValueAt( Object value, int row, int column ) {
    return;
  }

  public boolean isCellEditable( int row, int column ) {
    return false;
  }

  public void moveRow( int startIndex, int endIndex, int toIndex ) {
    return;
  }
}
