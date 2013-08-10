/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ParameterTypeType.java,v 1.1 2006/09/26 10:08:22 jap Exp $
 */

package pt.inescporto.template.reports.parser.objects.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ParameterTypeType.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:08:22 $
 */
public class ParameterTypeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The java.lang.String type
     */
    public static final int JAVA_LANG_STRING_TYPE = 0;

    /**
     * The instance of the java.lang.String type
     */
    public static final ParameterTypeType JAVA_LANG_STRING = new ParameterTypeType(JAVA_LANG_STRING_TYPE, "java.lang.String");

    /**
     * The java.awt.Image type
     */
    public static final int JAVA_AWT_IMAGE_TYPE = 1;

    /**
     * The instance of the java.awt.Image type
     */
    public static final ParameterTypeType JAVA_AWT_IMAGE = new ParameterTypeType(JAVA_AWT_IMAGE_TYPE, "java.awt.Image");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private ParameterTypeType(int type, java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- pt.inescporto.template.reports.parser.objects.types.ParameterTypeType(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerateReturns an enumeration of all possible
     * instances of ParameterTypeType
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate()

    /**
     * Method getTypeReturns the type of this ParameterTypeType
     */
    public int getType()
    {
        return this.type;
    } //-- int getType()

    /**
     * Method init
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("java.lang.String", JAVA_LANG_STRING);
        members.put("java.awt.Image", JAVA_AWT_IMAGE);
        return members;
    } //-- java.util.Hashtable init()

    /**
     * Method readResolve will be called during deserialization to
     * replace the deserialized object with the correct constant
     * instance. <br/>
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve()

    /**
     * Method toStringReturns the String representation of this
     * ParameterTypeType
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString()

    /**
     * Method valueOfReturns a new ParameterTypeType based on the
     * given String value.
     *
     * @param string
     */
    public static pt.inescporto.template.reports.parser.objects.types.ParameterTypeType valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ParameterTypeType";
            throw new IllegalArgumentException(err);
        }
        return (ParameterTypeType) obj;
    } //-- pt.inescporto.template.reports.parser.objects.types.ParameterTypeType valueOf(java.lang.String)

}
