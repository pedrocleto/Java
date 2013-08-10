package pt.inescporto.template.client.design;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author jap
 * @version 1.0
 */
public class ImageLoader {
  public static java.awt.Image loadImage(final String imageName) {
    final java.lang.ClassLoader loader = ImageLoader.class.getClassLoader();
    java.awt.Image image = null;
    java.io.InputStream is = (java.io.InputStream)
        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction() {
      public Object run() {
        if (loader != null) {
          return loader.getResourceAsStream(imageName);
        }
        else {
          return ClassLoader.getSystemResourceAsStream(imageName);
        }
      }
    });
    if (is != null) {
      try {
        final int BlockLen = 256;
        int offset = 0;
        int len;
        byte imageData[] = new byte[BlockLen];
        while ( (len = is.read(imageData, offset, imageData.length - offset)) >
               0) {
          if (len == (imageData.length - offset)) {
            byte newData[] = new byte[imageData.length * 2];
            System.arraycopy(imageData, 0, newData, 0, imageData.length);
            imageData = newData;
            newData = null;
          }
          offset += len;
        }
        image = java.awt.Toolkit.getDefaultToolkit()
            .createImage(imageData);
      }
      catch (java.io.IOException ex) {}
    }
    return image;
  }
}
