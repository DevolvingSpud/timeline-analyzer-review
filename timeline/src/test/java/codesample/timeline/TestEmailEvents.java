package codesample.timeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;
import org.junit.Test;

public class TestEmailEvents 
{
	@Test
	public void TestGetStart() throws MessagingException
	{
		DateTime d = new DateTime(1994,11,25,0,0); //time set
		Date e = d.toDate(); //convert to Java.util date format
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		MimeMessage mime = new MimeMessage(session); 
		mime.setSentDate(e);  //setting date.
		EmailEvent event = new EmailEvent(mime); //make an event for extracting
		//System.out.println(event.getStart().toDate());
		//System.out.println(e);

		assertEquals(e,event.getStart()); //testing the event
	}
	
	@Test
	public void TestGetEnd() throws MessagingException 
	{
		DateTime d = new DateTime(94,11,25,0,0); //time set.
		Date e = d.toDate(); //convert to java.util.Date.
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		mime.setSentDate(e); //setting the date.
		EmailEvent event = new EmailEvent(mime);
		
		assertEquals(e, event.getStart());
	}
	
	
	//WEB ADDRESS: https://forums.oracle.com/thread/2178060
	//not sure how to test this...I clearly have to set a message ID.
	@Test
	public void TestGetMessageID() throws MessagingException
	{
		String id = "22287574.1075863594524"; //message ID
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		mime.set
		EmailEvent event = new EmailEvent(mime);
		assertEquals(id, event.getMessageID());
	}
	
	@Test
	public void TestGetSenderEmail()
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		Address [] senders = new Address[] {InternetAddress.parse("example1@email.com"),
										InternetAddress.parse("example2@email.com"),
										InternetAddress.parse("example3@email.com")};
		
		mime.setSender(senders);
		EmailEvent event = new EmailEvent(mime);
		assertEquals(senders, event.getSenderEmail());
	}
	
	@Test
	public void TestGetReceiverEmail()
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		Address [] receivers = new Address[] {InternetAddress.parse("example1@email.com"),
										InternetAddress.parse("example2@email.com"),
										InternetAddress.parse("example3@email.com")};
		
		mime.setRecipient(Message.RecipientType.TO, receivers);
		EmailEvent event = new EmailEvent(mime);
		assertEquals(receivers, event.getCC());
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
	public void TestGetSenderName()
	{
		fail();
	}
	
	@Test
	public void TestGetReceiverName()
	{
		fail();
	}
	
	@Test 
	public void TestGetCC()
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		Address [] cc = new Address[] {InternetAddress.parse("example1@email.com"),
										InternetAddress.parse("example2@email.com"),
										InternetAddress.parse("example3@email.com")};
		
		mime.setRecipient(Message.RecipientType.CC, cc);
		EmailEvent event = new EmailEvent(mime);
		assertEquals(s, event.getCC());
	}
	
	@Test
	public void TestBCC()
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		Address [] bcc = new Address[] {InternetAddress.parse("example1@email.com"),
										InternetAddress.parse("example2@email.com"),
										InternetAddress.parse("example3@email.com")};
		
		mime.setRecipient(Message.RecipientType.BCC, bcc);
		EmailEvent event = new EmailEvent(mime);
		assertEquals(bcc, event.getBCC());
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


