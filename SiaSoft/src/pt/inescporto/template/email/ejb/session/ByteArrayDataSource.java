package pt.inescporto.template.email.ejb.session;

import java.io.*;
import javax.activation.*;

/**
 * A simple DataSource for demonstration purposes.
 * This class implements a DataSource from:
 * 	an InputStream
 *	a byte array
 * 	a String
 *
 * @author John Mani
 * @author Bill Shannon
 * @author Max Spivak
 */


public class ByteArrayDataSource implements DataSource {
    private byte[] data;	// data
    private String type;	// content-type
    private String name;        //name

    /* Create a DataSource from an input stream */
    public ByteArrayDataSource(InputStream is, String type) {
      if(type == null) {
        this.type = "application/octet-stream";
      } else {
        this.type = type;
      }

      try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
	    int ch;

	    while ((ch = is.read()) != -1)
                // XXX - must be made more efficient by
	        // doing buffered reads, rather than one byte reads
	        os.write(ch);
	    data = os.toByteArray();

        } catch (IOException ioex) { }
    }

    /* Create a DataSource from a byte array */
    public ByteArrayDataSource(byte[] data, String type) {
        this.data = data;
        if(type == null) {
          this.type = "application/octet-stream";
        } else {
          this.type = type;
        }
    }

    /* Create a DataSource from a String */
    public ByteArrayDataSource(String data, String type) {
	try {
	    // Assumption that the string contains only ASCII
	    // characters!  Otherwise just pass a charset into this
	    // constructor and use it in getBytes()
	    this.data = data.getBytes("iso-8859-1");
	} catch (UnsupportedEncodingException uex) { }
        if(type == null) {
          this.type = "application/octet-stream";
        } else {
          this.type = type;
      }
    }

    /**
     * Return an InputStream for the data.
     * Note - a new stream must be returned each time.
     */
    public InputStream getInputStream() throws IOException {
	if (data == null)
	    throw new IOException("no data");
	return new ByteArrayInputStream(data);
    }

    public OutputStream getOutputStream() throws IOException {
	throw new IOException("cannot do this");
    }

    public String getContentType() {
        return type;
    }

    public String getName() {
        return name == null ? "dummy" : name;
    }
}
