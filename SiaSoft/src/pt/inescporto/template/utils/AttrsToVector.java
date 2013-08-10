package pt.inescporto.template.utils;

import java.util.*;
import java.sql.*;
import java.lang.reflect.*;
import pt.inescporto.template.elements.*;

public class AttrsToVector {
  public static Vector Convert( Object attrs ) {
    Field[] fld = attrs.getClass().getDeclaredFields();

    // create row vector
    Vector rowData = new Vector();

    // for all fields
    for( int i = 0; i < fld.length; i++ ) {
      try {
        // is a string
        if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString ) {
          TplString myStr = (TplString)fld[i].get(attrs);
          rowData.add( myStr.getValue() );
        }
        // is a boolean
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean ) {
          Field booleanField = fld[i];
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          rowData.add( myBoolean.getValue() );
        }
        // is a integer
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt ) {
          Field intField = fld[i];
          TplInt myInt = (TplInt)fld[i].get(attrs);
          rowData.add( myInt.getValue() );
        }
        // is a long
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong ) {
          Field longField = fld[i];
          TplLong myLong = (TplLong)fld[i].get(attrs);
          rowData.add( myLong.getValue() );
        }
        // is a float
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat ) {
          Field floatField = fld[i];
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          rowData.add( myFloat.getValue() );
        }
        // is a Timestamp
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp ) {
          Field timeField = fld[i];
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          rowData.add( myTime.getValue() );
        }
      }
      catch (Exception e) {
        System.out.println( e.toString() );
      }
    }
    return rowData;
  }

  public static Vector Convert( Vector row ) {
    TplString     ts = new TplString();
    TplBoolean    tb = new TplBoolean();
    TplInt        ti = new TplInt();
    TplLong       tl = new TplLong();
    TplFloat      tf = new TplFloat();
    TplTimestamp  tt = new TplTimestamp();

    // create row vector
    Vector rowData = new Vector();

    Iterator i = row.iterator();

    // for all fields
    while( i.hasNext() ) {
      Object test = i.next();
      try {
        // is a string
        if (test instanceof pt.inescporto.template.elements.TplString)
          rowData.add( ((TplString)test).getValue() );
        // is a boolean
        if (test instanceof pt.inescporto.template.elements.TplBoolean)
          rowData.add( ((TplBoolean)test).getValue() );
        // is a integer
        if (test instanceof pt.inescporto.template.elements.TplInt)
          rowData.add( ((TplInt)test).getValue() );
        // is a long
        if (test instanceof pt.inescporto.template.elements.TplLong)
          rowData.add( ((TplLong)test).getValue() );
        // is a float
        if (test instanceof pt.inescporto.template.elements.TplFloat)
          rowData.add( ((TplFloat)test).getValue() );
        // is a Timestamp
        if (test instanceof pt.inescporto.template.elements.TplTimestamp)
          rowData.add( ((TplTimestamp)test).getValue() );
      }
      catch (Exception e) {
        System.out.println( e.toString() );
      }
    }
    return rowData;
  }

  public static Collection ListConvert( Collection list ) {
    if( list == null )
      return null;

    ArrayList converted = new ArrayList();
    Iterator it = list.iterator();

    while( it.hasNext() )
      converted.add(Convert(it.next()));

    return converted;
  }

  public static Collection ListConvert( Vector list ) {
    ArrayList converted = new ArrayList();
    Iterator it = list.iterator();

    while( it.hasNext() )
      converted.add(Convert((Vector)it.next()));

    return converted;
  }

  // convert Dao to vector of String
  public static Vector StringConvert( Object attrs ) {
    Field[] fld = attrs.getClass().getDeclaredFields();

    // create row vector
    Vector rowData = new Vector();

    // for all fields
    for( int i = 0; i < fld.length; i++ ) {
      try {
        // is a string
        if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString ) {
          TplString myStr = (TplString)fld[i].get(attrs);
          rowData.add( myStr.getValue() == null ? "" : myStr.getValue() );
        }
        // is a boolean
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean ) {
          Field booleanField = fld[i];
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          rowData.add( String.valueOf(myBoolean.getValue().booleanValue()) );
        }
        // is a integer
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt ) {
          Field intField = fld[i];
          TplInt myInt = (TplInt)fld[i].get(attrs);
          rowData.add( String.valueOf(myInt.getValue().intValue()) );
        }
        // is a long
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong ) {
          Field longField = fld[i];
          TplLong myLong = (TplLong)fld[i].get(attrs);
          rowData.add( String.valueOf( myLong.getValue().longValue()) );
        }
        // is a float
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat ) {
          Field floatField = fld[i];
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          rowData.add( String.valueOf( myFloat.getValue().floatValue()) );
        }
        // is a Timestamp
        else if( fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp ) {
          Field timeField = fld[i];
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          rowData.add( myTime.getValue() == null ? "" : myTime.getValue().toString() );
        }
      }
      catch (Exception e) {
        System.out.println( e.toString() );
      }
    }
    return rowData;
  }

  // convert vector of Dao to vector of vector of String
  public static Collection ListStringConvert( Vector list ) {
    ArrayList converted = new ArrayList();
    Iterator it = list.iterator();

    while( it.hasNext() )
      converted.add(StringConvert((Object)it.next()));

    return converted;
  }

  // convert vector of Tpl Element to vector of String
  public static Vector TplElementStringConvert( Vector list ) {
    Iterator it = list.iterator();

    // create row vector
    Vector rowData = new Vector();

    // for all fields
    while( it.hasNext() ) {
      try {
        // is a string
        Object myObj = it.next();
        if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplString") ) {
          rowData.add( ((TplString)myObj).getValue() == null ? "" : ((TplString)myObj).getValue() );
        }
        // is a boolean
        else if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplBoolean") ) {
          rowData.add( String.valueOf(((TplBoolean)myObj).getValue().booleanValue()) );
        }
        // is a integer
        else if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplInt") ) {
          rowData.add( String.valueOf(((TplInt)myObj).getValue().intValue()) );
        }
        // is a long
        else if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplLong") ) {
          rowData.add( String.valueOf( ((TplLong)myObj).getValue().longValue()) );
        }
        // is a float
        else if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplFloat") ) {
          rowData.add( String.valueOf(((TplFloat) myObj).getValue().floatValue()) );
        }
        // is a Timestamp
        else if( myObj.getClass().getName().equals("pt.inescporto.template.elements.TplTimestamp") ) {
          rowData.add( ((TplTimestamp)myObj).getValue() == null ? "" : ((TplTimestamp)myObj).getValue().toString() );
        }
      }
      catch (Exception e) {
        System.out.println( e.toString() );
      }
    }
    return rowData;
  }

  // convert vector of vector of Tpl Elements to vector of vector of String
  public static Collection ListTplElementStringConvert( Vector list ) {
    ArrayList converted = new ArrayList();
    Iterator it = list.iterator();

    while( it.hasNext() )
      converted.add(TplElementStringConvert((Vector)it.next()));

    return converted;
  }
}