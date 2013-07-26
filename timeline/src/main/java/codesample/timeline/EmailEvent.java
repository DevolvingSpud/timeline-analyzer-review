package codesample.timeline;

import java.sql.Array;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;

import codesample.timeline.AbstractEvent;

public class EmailEvent implements Event
{
	DateTime startDate = null;
	private MimeMessage message;
	
	/**
	 * EmailEvent requires a sent date, which is pulled from a MimeMessage of 
	 * an E-mail. 
	 * @param message - a MimeMessage containing the content of an E-mail.
	 * @throws MessagingException
	 */
	public EmailEvent(MimeMessage message) throws MessagingException
	{
		this.message = message;
		startDate = new DateTime(message.getSentDate());
	}
	
	/**
	 * Receives MessageID header in E-mail.
	 * @return MessageID header.
	 * @throws MessagingException
	 */
	public String getMessageID() throws MessagingException
	{
		return message.getMessageID();
	}
	
	/**
	 * @return sent date of an E-mail.
	 */
	public DateTime getStart() 
	{
		return startDate;
	}
	
	/**
	 * @return Java.util Date instead of DateTime of an E-mails Received Time.
	 * @throws MessagingException
	 */
	//Not completely sure if this Received date is necessary yet...question on board in red.
	public Date getStartOfRecieved() throws MessagingException
	{
		return message.getReceivedDate();
	}
	
	/**
	 * Returns a the sent date of an E-mail b/c E-mails do not contain end times.
	 * @return SentDate of an E-mail.
	 */
	public DateTime getEnd() 
	{
		return startDate;
	}
	
	/**
	 * @return An array of addresses belonging to the Sender(s).
	 * @throws MessagingException 
	 */
	public Address[] getSenderEmail() throws MessagingException
	{
		return message.getFrom(); 
	}
	
	/**
	 * @return the primary recipient's E-mail address/addresses.
	 * @throws MessagingException
	 */
	public Address[] getReceiverEmail() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.TO); 
		/*I need to test to see if the above return actually returns the
		 * primary recipient's E-mail address or the primary recipient's Name.
		 */
	}
	
	//return if both the sender and receiver both have company E-mails.
	public boolean isBothFromCompany()
	{
		return false;
	}
	
	/**
	 * @return the subject header of an E-mail.
	 * @throws MessagingException
	 */
	public String getSubject() throws MessagingException
	{
		return message.getSubject();
	}
	
	//returns sender's name ==> there is not a method for this in Mime.Message API.
	public String getSenderName()
	{
		return null;
	}
	
	/*return sender's name ==> before attempting this method make sure 
	 * that getReceiverEmail() works (look at comment in getReceiver to understand).
	 */
	//return receiver's name
	public String getReceiverName()
	{
		return null;
	}
	
	/**
	 * @return array of carbon-copy (Cc) recipient's E-mail addresses.
	 * @throws MessagingException 
	 */
	public Address[] getCC() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.CC); 
	}
	
	/**
	 * @return array of blind carbon-copy (Bcc) recipient's E-mail addresses.
	 * @throws MessagingException 
	 */
	public Address[] getBCC() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.TO); 
	}
}
