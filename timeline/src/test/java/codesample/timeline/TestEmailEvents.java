package codesample.timeline;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
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
	public void TestGetSenderName() throws UnsupportedEncodingException, MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String from = "myEmail@whatever.com";
		String fromName = "Macon";
		InternetAddress iaSender = new InternetAddress(from, fromName);
		mime.setSender(iaSender);
		EmailEvent e = new EmailEvent(mime);
		
		assertEquals(fromName, e.getSenderName());
	}
	
	@Test
	public void TestGetReceiverNames() throws UnsupportedEncodingException, MessagingException
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		String to1 = "to1Email@whatever.com";
		String to2 = "to2Email@whatever.com";
		String to3 = "to3Email@whatever.com";
		String to4 = "to4Email@whatever.com";
		String to1Name = "Jack";
		String to2Name = "Michael";
		String to3Name = "Andrew";
		String to4Name = "Mike";
		InternetAddress iaTo1 = new InternetAddress(to1, to1Name);
		InternetAddress iaTo2 = new InternetAddress(to2, to2Name);
		InternetAddress iaTo3 = new InternetAddress(to3, to3Name);
		InternetAddress iaTo4 = new InternetAddress(to4, to4Name);
		
		String myNames[] = {to1Name, to2Name,to3Name,to4Name};
		Arrays.sort(myNames);
		
		mime.addRecipient(MimeMessage.RecipientType.TO, iaTo1);
		mime.addRecipient(MimeMessage.RecipientType.TO, iaTo2);
		mime.addRecipient(MimeMessage.RecipientType.TO, iaTo3);
		mime.addRecipient(MimeMessage.RecipientType.TO, iaTo4);
		
		EmailEvent e = new EmailEvent(mime);
		String receiveNames[] = e.getReceiverNames();
		Arrays.sort(receiveNames);
		
		assertArrayEquals(receiveNames, myNames);
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
	
//	@Test
//	public void TestIsBothFromCo()
//	{
//		fail();
//	}
//	
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


