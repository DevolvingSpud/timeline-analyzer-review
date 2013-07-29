package codesample.timeline;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;


public class EmailEvent implements Event
{
	DateTime startDate = null; //the sent time
	//DateTime endDate = null; //the received time
	private MimeMessage message;
	
	/** EmailEvent requires a sent date, which is pulled from a MimeMessage of an E-mail. 
	 * @param message - a MimeMessage containing the content of an E-mail.
	 * @throws MessagingException
	 * The startDate represents the time sent.
	 * The endDate represents the time received*/
	public EmailEvent(MimeMessage message) throws MessagingException
	{
		this.message = message;
		startDate = new DateTime(message.getSentDate());
	}
	
	
	/** @return sent date of E-mail.*/
	public DateTime getStart() 
	{
		return  startDate;
	}
	
	
	/** @return sent date of E-mail. */
	public DateTime getEnd() 
	{
		return startDate;
	}
	
	
	/**
	 * Receives MessageID header in E-mail hopefully to give
	 * this message a unique ID number.
	 * @return MessageID header.
	 * @throws MessagingException
	 *//*
	public String getMessageID() throws MessagingException 
	{
		return message.getMessageID();
	}*/
	
	
	/** @return An array of addresses belonging to the Sender(s).
	 * @throws MessagingException */
	public Address[] getSenderEmail() throws MessagingException
	{
		return message.getFrom(); 
	}
	
	
	/** @return the primary recipient's E-mail address/addresses.
	 * @throws MessagingException */
	public Address[] getReceiverEmail() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.TO); 
		/*I need to test to see if the above return actually returns the
		 * primary recipient's E-mail address or the primary recipient's Name.
		 */
	}
	
	/*//not necessary yet.
	*//** @return if both the sender and receiver both have company E-mails.*//*
	public boolean isBothFromCo()
	{
		if getSenderEmail().equals(get.ReceiverEmail()
			return true;
		return false;
	}*/
	
	
	/** @return the subject header of an E-mail.
	 * @throws MessagingException*/
	public String getSubject() throws MessagingException
	{
		return message.getSubject();
	}
	
	
	/**@return sender's name ==> there is not a method for this in Mime.Message API. */
	public String getSenderName()
	{
		return null;
	}
	
	
	/**@return sender's name ==> before attempting this method make sure 
	 * that getReceiverEmail() works (look at comment in getReceiver to understand).
	 */
	public String getReceiverName()
	{
		return null;
	}
	
	
	/** @return array of carbon-copy (Cc) recipient's E-mail addresses.
	 * @throws MessagingException */
	public Address[] getCC() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.CC); 
	}
	
	
	/** @return array of blind carbon-copy (Bcc) recipient's E-mail addresses.
	 * @throws MessagingException */
	public Address[] getBCC() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.TO); 
	}
	
	
	/** @return size of E-mail/content in bytes. 
	 * @throws MessagingException */
	public int getSize() throws MessagingException
	{
		return message.getSize();
	}
}
