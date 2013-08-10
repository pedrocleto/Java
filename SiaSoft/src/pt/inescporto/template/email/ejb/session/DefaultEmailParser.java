package pt.inescporto.template.email.ejb.session;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class DefaultEmailParser {

  public static boolean searchMessageForIndividualTags(String message) {
    return message.split(DefaultEmailTags.fromEmail).length > 1 ? true : false ||  message.split(DefaultEmailTags.fromName).length > 1 ? true : false
        || message.split(DefaultEmailTags.fromUserName).length > 1 ? true : false || message.split(DefaultEmailTags.toEmail).length > 1 ? true : false
        || message.split(DefaultEmailTags.toName).length > 1 ? true : false || message.split(DefaultEmailTags.toUserName).length > 1 ? true : false;
  }

  public static String parseMessage(String message, String fromEmail, String fromName, String fromUserName, String toEmail, String toName, String toUserName, Hashtable properties ) {
    String newMessage = message;
    //Replace DEFAULT Tags
    if ( fromEmail != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.fromEmail, fromEmail);
    }
    if ( fromName != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.fromName, fromName);
    }
    if ( fromUserName != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.fromUserName, fromUserName);
    }
    if ( toEmail != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.toEmail, toEmail);
    }
    if ( toName != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.toName, toName);
    }
    if ( toUserName != null ) {
      newMessage = newMessage.replaceAll(DefaultEmailTags.toUserName, toUserName);
    }

    Enumeration enumr = properties.keys();
    while (enumr.hasMoreElements()) {
      String key = (String) enumr.nextElement();
      String value = (String) properties.get(key);
      newMessage = newMessage.replaceAll(key, value);
    }

    return newMessage;
  }

  public static String parseMessage(String message, Hashtable properties) {
    String newMessage = message;
    Enumeration enumr = properties.keys();
    while (enumr.hasMoreElements()) {
      String key = (String) enumr.nextElement();
      String value = (String) properties.get(key);
      newMessage = newMessage.replaceAll(key, value);
    }
    return newMessage;
  }

}
