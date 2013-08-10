package pt.inescporto.template.client.design.table;

import javax.swing.table.*;

public class TmplTableColumnModel extends DefaultTableColumnModel {
  public TmplTableColumnModel() {
    super();
    columnSelectionAllowed = false;
  }

  public void addColumn(TableColumn tc) {
    super.addColumn(tc);
  }
}
