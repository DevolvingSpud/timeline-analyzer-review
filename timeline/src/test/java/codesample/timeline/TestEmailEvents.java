package codesample.timeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;
import org.junit.Test;

public class TestEmailEvents 
{

	@Test
	public void TestGetSenderEmail() throws MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String sender = "admin@whatever.com";
		InternetAddress iaSender = new InternetAddress(sender);
		mime.setSender(iaSender);
		EmailEvent e = new EmailEvent(mime);
		Address from = e.getSenderEmail();
		assertEquals(sender, from.toString());
	}
	
	@Test
	public void TestGetReceiverEmail() throws MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String receiver = "muffin@whatever.com";
		InternetAddress iaReceiver = new InternetAddress(receiver);
		mime.setRecipient(Message.RecipientType.TO, iaReceiver);
		EmailEvent e = new EmailEvent(mime);
		
		for(Address a: e.getReceiverEmail())
		{
			//System.out.println(a.toString());
			assertEquals(receiver, a.toString());
		}
	}
	@Test
	public void TestIsToSelf() throws MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		//sender
		String sender = "admin@whatever.com";
		InternetAddress iaSender = new InternetAddress(sender);
		mime.setSender(iaSender);
		//receiver
		String receiver = "admin@whatever.com";
		InternetAddress iaReceiver = new InternetAddress(receiver);
		mime.setRecipient(Message.RecipientType.TO, iaReceiver);
		
		EmailEvent e = new EmailEvent(mime);
		
		//assertTrue(e.isSameCompany());
		
	}
	
	@Test
	public void TestIsBothFromCo()
	{
		fail();
	}
	
	@Test 
	public void TestGetSubject() throws MessagingException
	{
		String s = "Blank Subject";
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		mime.setSubject(s);
		EmailEvent event = new EmailEvent(mime);
		assertEquals(s, event.getSubject());
		
	}
	
	@Test
	public void TestAttachmentSuccess() throws MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String s = "testFile";
		mime.setDisposition(s);
		
		EmailEvent event = new EmailEvent(mime);
		
		//System.out.println(event.attachment());
		assertEquals(s,event.attachment());
	}
	@Test
	public void TestAttachmentFail() throws MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String s = "testFile";
		//mime.setDisposition(s);
		
		EmailEvent event = new EmailEvent(mime);
		
		//System.out.println(event.attachment());
		assertNotSame(s,event.attachment());
	}
}


