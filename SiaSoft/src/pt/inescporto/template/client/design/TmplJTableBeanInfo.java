package pt.inescporto.template.client.design;

import javax.swing.JTableBeanInfo;
import java.beans.PropertyDescriptor;
import java.util.*;

public class TmplJTableBeanInfo extends JTableBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList( super.getPropertyDescriptors() );
    try {
      l.add(new PropertyDescriptor( "url", TmplJTable.class ));
      l.add(new PropertyDescriptor( "headers", TmplJTable.class ));
      l.add(new PropertyDescriptor( "fields", TmplJTable.class ));
      l.add(new PropertyDescriptor( "staticLinkCondition", TmplJTable.class ));
      l.add(new PropertyDescriptor( "msgCode", TmplJTable.class ));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplJTableBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}