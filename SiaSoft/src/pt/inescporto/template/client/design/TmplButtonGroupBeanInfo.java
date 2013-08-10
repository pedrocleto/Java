package pt.inescporto.template.client.design;

import java.beans.SimpleBeanInfo;
import java.beans.PropertyDescriptor;
import java.util.*;

public class TmplButtonGroupBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    ArrayList l = new java.util.ArrayList();
    try {
      l.add(new PropertyDescriptor( "label", TmplButtonGroup.class));
      l.add(new PropertyDescriptor( "field", TmplButtonGroup.class));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplButtonGroupBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}