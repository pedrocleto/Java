package pt.inescporto.siasoft.proc.client.rmi.editor;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

public class TransferableComponent implements Transferable {
  public static final DataFlavor userObjectFlavor = new DataFlavor(GraphNode.class, "Object");
  private Object obj = null;

  public TransferableComponent(Object obj) {
    this.obj = obj;
  }

  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {
	userObjectFlavor};
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return flavor.equals(userObjectFlavor);
  }

  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    if (flavor.equals(userObjectFlavor))
      return obj;
    throw new UnsupportedFlavorException(flavor);
  }
}
