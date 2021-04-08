package mail;

import java.net.InetAddress;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class Email {
	String hostname = "Unknown";
	
  public Email(Boolean newComputer, String content, String filePath) {
	    Properties props = new Properties();
	    /** Parâmetros de conexão com servidor Gmail */
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	    "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");

	    Session session = Session.getDefaultInstance(props,
	      new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication()
	           {
	                 return new PasswordAuthentication("gdisposible@gmail.com",
	                 "DisposibleGmail1-");
	           }
	      });

	    /** Ativa Debug para sessão */
	    session.setDebug(true);

	    try {
	    	
	    	try
	    	{
	    	    InetAddress addr;
	    	    addr = InetAddress.getLocalHost();
	    	    hostname = addr.getHostName();
	    	}
	    	catch (Exception e) {
			}

	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("gdisposible@gmail.com"));
	      //Remetente

	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse("jhelisong@hotmail.com");

	      message.setRecipients(Message.RecipientType.TO, toUser);
	      if(newComputer) {
	    	  message.setSubject("Novo Computador Instalado " + hostname);//Assunto
	      }
	      else {
	    	  message.setSubject("Nova Empresa Cadastrada " + hostname);//Assunto
	      }
	      
	      
	      BodyPart messageBodyPart = new MimeBodyPart();
	      messageBodyPart.setText(content);
	      Multipart multipart = new MimeMultipart();
	      multipart.addBodyPart(messageBodyPart);

	      if(filePath != null) {
	          messageBodyPart = new MimeBodyPart();
	          String filename = filePath;
	          DataSource source = new FileDataSource(filename);
	          messageBodyPart.setDataHandler(new DataHandler(source));
	          messageBodyPart.setFileName(filename);
	          multipart.addBodyPart(messageBodyPart);
	      }
      
         message.setContent(multipart ); 
         
	      Transport.send(message);

	      System.out.println("Feito!!!");

	     } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	  }
	}