package org.scorpion.external.tmail;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.scorpion.api.common.ICustomHandler;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.ScorpionSequenceUtil;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TMailMessageHandler implements ICustomHandler{
	

	@Override
	public void handler(Object obj) throws ScorpionBaseException {
		try {
			Session session = initConf((TMailMessage)obj);
			sendMailMessage(generateMessage((TMailMessage)obj,session),(TMailMessage)obj,session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScorpionBaseException(e);
		}
	}
	
	
	/**
	 * @param mailMessage
	 * @throws MessagingException 
	 * @throws GeneralSecurityException 
	 */
	private Session initConf(TMailMessage mailMessage) throws MessagingException, GeneralSecurityException{
		Properties prop = new Properties();
		prop.setProperty("mail.host", mailMessage.getHost());
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		if(mailMessage.isSSL()){
			MailSSLSocketFactory sf = new MailSSLSocketFactory();  
			sf.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", "true");  
			prop.put("mail.smtp.ssl.socketFactory", sf);  
		}
		if(mailMessage.getPort() != null&& !"".equals(mailMessage.getPort()))
			prop.setProperty("mail.smtp.port", mailMessage.getPort());  
		Session session = Session.getInstance(prop);
		session.setDebug(true);
		return session;
	}
	
	
	/**
	 * @param mailMessage
	 * @param session
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private MimeMessage generateMessage(TMailMessage mailMessage,Session session) throws Exception{

		boolean isP = false; boolean isA = false;
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailMessage.getFrom()));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getRecipient()));
		message.setSubject(mailMessage.getSubject()==null?"":mailMessage.getSubject());
		MimeBodyPart text = new MimeBodyPart();
        MimeMultipart mp2 = new MimeMultipart();
        MimeBodyPart content = new MimeBodyPart();
		MimeMultipart mp = new MimeMultipart();
        if(mailMessage.getPictures() != null&&mailMessage.getPictures().size()>0){
        	isP = true;
        	StringBuffer sb = new StringBuffer();
        	for(String pd:mailMessage.getPictures()){
        		MimeBodyPart image = new MimeBodyPart();
        		DataHandler dh = new DataHandler(new FileDataSource(pd));
        		image.setDataHandler(dh);
        		String cid = ScorpionSequenceUtil.generateSequeueString()+"."+pd.split("\\.")[1];
        		image.setContentID(cid);
        		sb.append("<img src='cid:"+cid+"'>");
        		mp.addBodyPart(image);
        		mp.setSubType("related");
        	}
        	mailMessage.setContent(mailMessage.getContent()+sb.toString());
        }
		if(mailMessage.getContent() == null)
			text.setContent("","text/html;charset=UTF-8");
		else
			text.setContent(mailMessage.getContent(),"text/html;charset=UTF-8");
	
		if(mailMessage.getAttachments() != null&&mailMessage.getAttachments().size()>0){
			isA = true;
			for(String attachment:mailMessage.getAttachments()){
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(attachment));
				attach.setDataHandler(dh);
				attach.setFileName(dh.getName());
				mp2.addBodyPart(attach);
			}
		}
		if(isP&&isA){
			mp.addBodyPart(text);
			content.setContent(mp);
	        mp2.addBodyPart(content);
	        message.setContent(mp2);
			message.saveChanges();
		}else if(isP&&!isA){
    		mp.addBodyPart(text);
			message.setContent(mp);
			message.saveChanges();
		}else if(!isP&&isA){
	        message.setContent(mp2);
			message.saveChanges();
		}else{
			message.setSubject(mailMessage.getSubject());
			message.setContent(mailMessage.getContent(), "text/html;charset=UTF-8");
		}
		return message;

	}
	
	
	
	
	
	/**
	 * @param message
	 * @throws MessagingException
	 */
	private void sendMailMessage(Message message,TMailMessage mailMessage,Session session) throws MessagingException{
		Transport ts = session.getTransport();
		ts.connect(mailMessage.getHost(), mailMessage.getFrom(), mailMessage.getPassword());
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	

}
