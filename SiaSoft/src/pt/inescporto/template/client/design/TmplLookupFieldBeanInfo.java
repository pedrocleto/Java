package pt.inescporto.template.client.design;

import java.beans.PropertyDescriptor;
import javax.swing.JTextFieldBeanInfo;
import java.util.*;

public class TmplLookupFieldBeanInfo extends JTextFieldBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList( super.getPropertyDescriptors() );
    try {
      l.add(new PropertyDescriptor( "url", TmplLookupField.class ));
      l.add(new PropertyDescriptor( "defaultRefField", TmplLookupField.class ));
      l.add(new PropertyDescriptor( "refFieldList", TmplLookupField.class ));
      l.add(new PropertyDescriptor( "index", TmplLookupField.class ));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplLookupFieldBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}
