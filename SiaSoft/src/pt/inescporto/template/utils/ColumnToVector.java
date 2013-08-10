package pt.inescporto.template.utils;

import java.util.*;
import pt.inescporto.template.elements.*;

public class ColumnToVector {
  public static Vector convertColumn( Vector array, int column ) {
    Vector result = new Vector();

    if( array != null ) {
      for( int i = 0; i < array.size(); i++ ) {
        Vector row = (Vector)array.elementAt( i );
        TplObject element = (TplObject)row.elementAt( column );
        if( element instanceof TplString )
          result.addElement( ((TplString)element).getValue() );
        if( element instanceof TplBoolean )
          result.addElement( ((TplBoolean)element).getValue() );
        if( element instanceof TplInt )
          result.addElement( ((TplInt)element).getValue() );
        if( element instanceof TplLong )
          result.addElement( ((TplLong)element).getValue() );
        if( element instanceof TplFloat )
          result.addElement( ((TplFloat)element).getValue() );
        if( element instanceof TplTimestamp )
          result.addElement( ((TplTimestamp)element).getValue() );
      }
    }

    return result;
  }
}