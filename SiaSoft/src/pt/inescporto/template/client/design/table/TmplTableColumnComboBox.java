package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import java.util.Vector;

public class TmplTableColumnComboBox extends TableColumn {
  public static final int DEFAULT_RENDER = 0;
  public static final int LOOKUP_RENDER = 1;
  protected int fieldPerm = 7;

  public TmplTableColumnComboBox(int modelIndex, String url) {
    this(modelIndex, url, new Integer[] {new Integer(1), new Integer(0)},
         TmplTableColumnComboBox.DEFAULT_RENDER, null);
  }

  public TmplTableColumnComboBox(int modelIndex, String url, Integer[] showSave) {
    this(modelIndex, url, showSave, TmplTableColumnComboBox.DEFAULT_RENDER, null);
  }

  public TmplTableColumnComboBox(int modelIndex, String url, int renderType) {
    this(modelIndex, url, new Integer[] {new Integer(1), new Integer(0)},
         renderType, null);
  }

  public TmplTableColumnComboBox(int modelIndex, String url, String linkCondition) {
    this(modelIndex, url, new Integer[] {new Integer(1), new Integer(0)},
         TmplTableColumnComboBox.DEFAULT_RENDER, linkCondition);
  }

  public TmplTableColumnComboBox(int modelIndex, String url, Integer[] showSave, String linkCondition) {
    this(modelIndex, url, showSave, TmplTableColumnComboBox.DEFAULT_RENDER, linkCondition);
  }

  public TmplTableColumnComboBox(int modelIndex, String url, int renderType, String linkCondition) {
    this(modelIndex, url, new Integer[] {new Integer(1), new Integer(0)},
         renderType, linkCondition);
  }

  public TmplTableColumnComboBox(int modelIndex, String url,
                                 Integer[] showSave, int renderType) {
    this(modelIndex, url, showSave, renderType, null);
  }
  public TmplTableColumnComboBox(int modelIndex, String url,
                                 Integer[] showSave, int renderType, String linkCondition) {
    super(modelIndex);
    cellEditor = new TmplComboBoxEditor(url, showSave, linkCondition, new Vector());
    switch (renderType) {
      default:
      case DEFAULT_RENDER:
        cellRenderer = new TmplDefaultCellRenderer();
        break;
      case LOOKUP_RENDER:
        cellRenderer = new TmplLookupRenderer(url);
        break;
    }
  }

  public TmplTableColumnComboBox(int modelIndex, Object[] dataItems,
                                 Object[] dataValues) {
    this(modelIndex, dataItems, dataValues,
         TmplTableColumnComboBox.DEFAULT_RENDER);

  }

  public TmplTableColumnComboBox(int modelIndex, Object[] dataItems,
                                 Object[] dataValues, int renderType) {
    super(modelIndex);
    cellEditor = new TmplComboBoxEditor(dataItems, dataValues);
    switch (renderType) {
      default:
      case DEFAULT_RENDER:
        cellRenderer = new TmplDefaultCellRenderer();
        break;
      case LOOKUP_RENDER:
        cellRenderer = new TmplLookupRenderer(dataItems, dataValues);
        break;
    }
  }
}
