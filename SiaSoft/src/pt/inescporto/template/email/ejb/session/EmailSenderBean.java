package pt.inescporto.template.email.ejb.session;

import java.io.*;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import pt.inescporto.template.dao.*;

public class EmailSenderBean implements java.io.Serializable {

  private String SMTP_HOST_NAME = null;
  private String SMTP_AUTH_USER = null;
  private String SMTP_AUTH_PWD = null;
  private boolean EMAIL_SESSION_DEBUG = false;
  boolean smtpServerRequiresAuth = false;

  public EmailSenderBean(String SMTP_HOST_NAME, String SMTP_AUTH_USER, String SMTP_AUTH_PWD, boolean EMAIL_SESSION_DEBUG, boolean smtpServerRequiresAuth) {
    this.SMTP_HOST_NAME = SMTP_HOST_NAME;
    this.SMTP_AUTH_USER = SMTP_AUTH_USER;
    this.SMTP_AUTH_PWD = SMTP_AUTH_PWD;
    this.EMAIL_SESSION_DEBUG = EMAIL_SESSION_DEBUG;
    this.smtpServerRequiresAuth = smtpServerRequiresAuth;
  }

  private void postMail(Vector toEmailsList, String subject, String message, String from, boolean html) throws MessagingException, UserException {
    postMail(toEmailsList, subject, message, from, html, null, null);
  }

  public void postMail(Vector toEmailsList, String subject, String message, String from, boolean html, Vector attachFilenames, Vector attachBytes) throws MessagingException, UserException {
    boolean debug = EMAIL_SESSION_DEBUG;

    //Set the host smtp address
    Properties props = new Properties();
    props.put("mail.smtp.host", SMTP_HOST_NAME);
    Authenticator auth = null;

    if (smtpServerRequiresAuth) {
      props.put("mail.smtp.auth", "true");
      auth = new SMTPAuthenticator();
    }

    // create some properties and get the default Session
    Session session = Session.getDefaultInstance(props, auth);
    session.setDebug(debug);
    // create a message
    MimeMessage msg = new MimeMessage(session);
    // set the from and to address
    InternetAddress addressFrom = new InternetAddress(from);
    msg.setFrom(addressFrom);
    msg.setSentDate(new Date());

    InternetAddress[] addressTo = new InternetAddress[toEmailsList.size()];
    for (int i = 0; i < toEmailsList.size(); i++) {
      addressTo[i] = new InternetAddress((String)toEmailsList.get(i));
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);
    // Optional : You can also set your custom headers in the Email if you Want
    ///msg.addHeader("MyHeaderName", "myHeaderValue");
    // Setting the Subject and Content Type
    msg.setSubject(subject);

    if ((attachFilenames == null || attachFilenames.size() == 0) && (attachBytes == null || attachBytes.size() == 0)) {
      if (html) {
	msg.setContent(message, "text/html");
      }
      else {
	msg.setContent(message, "text/plain");
      }
    }
    else {
      String contentType = html ? "text/html" : "text/plain";
      msg = addAttachments(msg, message, contentType, attachFilenames, attachBytes);
    }
    Transport.send(msg);
  }

  private MimeMessage addAttachments(MimeMessage msg, String content, String contentType, Vector filenames, Vector attachBytes) throws UserException {
    try {
      Multipart multipart = new MimeMultipart();

      // create the message part
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      //fill message
      messageBodyPart.setContent(content, contentType);
      multipart.addBodyPart(messageBodyPart);

      // Part two are file attachments
      if (filenames != null) {
	for (int i = 0; i < filenames.size(); i++) {
	  String fileName = (String)filenames.get(i);
	  messageBodyPart = new MimeBodyPart();
	  DataSource source = new FileDataSource(fileName);
	  messageBodyPart.setDataHandler(new DataHandler(source));
	  messageBodyPart.setFileName(source.getName());
	  multipart.addBodyPart(messageBodyPart);
	}
      }

      // Part three are memory bytecode attachments
      if (attachBytes != null) {
	for (int i = 0; i < attachBytes.size(); i++) {
	  CustomEmailAttachment obj = (CustomEmailAttachment)attachBytes.get(i);
	  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	  ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
	  objectStream.writeObject(obj.getObject());
	  messageBodyPart = new MimeBodyPart();
	  DataSource source = obj.getObject() instanceof String ? new ByteArrayDataSource((String)obj.getObject(), obj.getType()) : new ByteArrayDataSource(byteStream.toByteArray(), obj.getType());
	  messageBodyPart.setDataHandler(new DataHandler(source));
	  messageBodyPart.setFileName(obj.getFileName());
	  multipart.addBodyPart(messageBodyPart);
	}
      }

      // Put parts in message
      msg.setContent(multipart);

      return msg;
    }
    catch (Exception ex) {
      throw new UserException("Error sending email with File Attachments");
    }
  }

  /**
   * SimpleAuthenticator is used to do simple authentication
   * when the SMTP server requires it.
   */
  private class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
      String username = SMTP_AUTH_USER;
      String password = SMTP_AUTH_PWD;
      return new PasswordAuthentication(username, password);
    }
  }
}
