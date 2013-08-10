package pt.inescporto.template.client.design.table;

import javax.swing.table.*;

public class TmplTableColumnLookup extends TableColumn {
  protected int fieldPerm = 7;

  public TmplTableColumnLookup(int modelIndex, String url) {
    super(modelIndex);
    cellEditor = new TmplLookupEditor();
    cellRenderer = new TmplLookupRenderer(url);
  }
}
