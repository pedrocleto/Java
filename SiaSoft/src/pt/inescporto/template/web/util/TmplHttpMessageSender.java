package pt.inescporto.template.web.util;

import java.io.*;
import pt.inescporto.template.utils.TmplMessages;

public class TmplHttpMessageSender {
  static int errCode = TmplMessages.MSG_OK;
  static java.lang.Exception lastEx = null;
  private boolean debug = true;
  String url = null;

  public TmplHttpMessageSender() {
    this.url = null;
  }

  public TmplHttpMessageSender( String url ) {
    this.url = url;
  }

  public int getErrCode() {
    return errCode;
  }

  public java.lang.Exception getException() {
    return lastEx;
  }

  public Object doSendObject( Object msg ) {//throws Exception {
    try {
      if( debug ) System.out.println( "Attempting to connect to " + this.url );
      java.net.URL urlConn = new java.net.URL( this.url );
      java.net.URLConnection c = urlConn.openConnection();

      c.setUseCaches( false );
      c.setDoOutput( true );
      c.setDoInput( true );

      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      ObjectOutputStream outData = new ObjectOutputStream( byteOut );

      if( debug ) System.out.println( "Writing request ..." );
      outData.writeObject( msg );
      outData.flush();

      byte buf[] = byteOut.toByteArray();
      c.setRequestProperty( "Content-type", "application/octet-stream" );
      c.setRequestProperty( "Content-length", "" + buf.length );

      DataOutputStream dataOut = new DataOutputStream( c.getOutputStream() );
      dataOut.write( buf );

      dataOut.flush();
      dataOut.close();

      if( debug ) System.out.println( "Reading response ..." );
      ObjectInputStream inData = new ObjectInputStream( c.getInputStream() );

      msg = inData.readObject();

      inData.close();
      errCode = TmplMessages.MSG_OK;
      lastEx = null;
    }
    catch( java.lang.ClassNotFoundException cnfex ) {
      if( debug ) cnfex.printStackTrace();
      errCode = TmplMessages.CERR_CLASSNOTFOUND;
      lastEx = (java.lang.Exception)cnfex;
    }
    catch( java.net.MalformedURLException mfURLex ) {
      if( debug ) mfURLex.printStackTrace();
      errCode = TmplMessages.CERR_MALFORMEDURL;
      lastEx = (java.lang.Exception)mfURLex;
    }
    catch( java.io.IOException ioex ) {
      if( debug ) ioex.printStackTrace();
      errCode = TmplMessages.CERR_IO;
      lastEx = (java.lang.Exception)ioex;
    }

    return msg;
  }
}
