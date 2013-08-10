package pt.inescporto.template.client.design;

import java.beans.PropertyDescriptor;
import javax.swing.JTextFieldBeanInfo;

public class TmplJButtonBeanInfo extends javax.swing.JButtonBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    PropertyDescriptor[] result = super.getPropertyDescriptors();
    try {
      return result;
    }
    catch( Exception ex ) {
      System.err.println( "TmplJButtonBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}