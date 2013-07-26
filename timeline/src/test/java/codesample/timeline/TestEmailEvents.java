package codesample.timeline;

import static org.junit.Assert.assertEquals;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;
import org.junit.Test;

public class TestEmailEvents 
{
	
	//doesn't work yet...
	@Test
	public void TestGetStart() throws MessagingException
	{
		DateTime d = new DateTime(94,11,25,0,0);
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage mime = new MimeMessage(session);
		
		mime.setSentDate(d.toDate()); 
		EmailEvent event = new EmailEvent(mime);
		
		DateTime dt = new DateTime(d);
		
		assertEquals(event.getStart(), dt);
	}
}
