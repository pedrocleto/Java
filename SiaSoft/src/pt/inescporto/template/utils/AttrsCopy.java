package pt.inescporto.template.utils;

import java.lang.reflect.*;
import pt.inescporto.template.elements.*;

public class AttrsCopy {
  public static Object cloneObject( Object toBeCloned )
                    throws java.lang.IllegalAccessException, java.lang.InstantiationException {
    Object clone = toBeCloned.getClass().newInstance();
    Field fldSource[] = toBeCloned.getClass().getDeclaredFields();
    Field fldDest[] = clone.getClass().getDeclaredFields();

    // make hard copy of equal fields
    for( int i = 0; i < fldSource.length; i++ ) {
      for( int j = 0; j < fldDest.length; j++ ) {
        if( (fldSource[i].getName()).equals(fldDest[j].getName()) ) {
          Object fldFrom = fldSource[i].get(toBeCloned);
          Object fldTo = fldDest[j].get(clone);
          if( fldFrom instanceof pt.inescporto.template.elements.TplObject )
            ((TplObject)fldTo).setValueAsObject(((TplObject)fldFrom).getValueAsObject());
        }
      }
    }
    return clone;
  }
}
