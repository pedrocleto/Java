package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.DataFlavor;
import javax.swing.tree.DefaultMutableTreeNode;

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
public class TransferableTreeNode implements Transferable {
  public static final DataFlavor treeNodeFlavor = new DataFlavor(DefaultMutableTreeNode.class,"TreeNode");
  private DefaultMutableTreeNode treeNode = null;
  public TransferableTreeNode(DefaultMutableTreeNode treeNode) {
    this.treeNode = treeNode;
  }
  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {treeNodeFlavor};
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return flavor.equals(treeNodeFlavor);
  }
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

    if (flavor.equals(treeNodeFlavor))
      return treeNode;
    throw new UnsupportedFlavorException(flavor);
  }
}
