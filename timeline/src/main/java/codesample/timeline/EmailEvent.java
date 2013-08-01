package codesample.timeline;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;
import javax.mail.Flags;


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
	 */
	public String getMessageID() throws MessagingException 
	{
		return message.getMessageID();
	}
	
	
	/** @return An array of addresses belonging to the Sender(s).
	 * @throws MessagingException */
	public Address getSenderEmail() throws MessagingException
	{
		return message.getSender();
	}
	
	
	/** @return the primary recipient's E-mail address/addresses.
	 * @throws MessagingException */
	public Address[] getReceiverEmail() throws MessagingException
	{
		return message.getRecipients(Message.RecipientType.TO); 
	}
	
	
	/** @return If the sender and receiver are the same when
	 * the number of Receivers equal 1. Essentially, sending an
	 * E-mail to yourself. 
	 * @throws MessagingException *//*
	public boolean isToSelf() throws MessagingException
	{
		Address sender = getSenderEmail();
		Address[] receiver = getReceiverEmail();
		if(receiver.length==1)
		{
			for (Address a:receiver)
			{
				if(a.equals(sender))
					return true;
			}
			return false;
		}
		return false;
	}*/

	
	/** @return the subject header of an E-mail.
	 * @throws MessagingException*/
	public String getSubject() throws MessagingException
	{
		return message.getSubject();
	}
	
	
	/**@return String of the sender's name. 
	 * @throws MessagingException */
	public String getSenderName() throws MessagingException
	{
		Address sender = getSenderEmail();
		if (sender instanceof InternetAddress) {
			return ((InternetAddress)sender).getPersonal();
		}
		return null;
	}
	
	
	/**@return Array of Strings in which contain all of the receiver's names.
	 * @throws MessagingException */
	public String[] getReceiverNames() throws MessagingException
	{
		Address[] receivers = getReceiverEmail();
		String [] names = new String[receivers.length];
		for (int i=0; i<receivers.length; i++)
			if (receivers[i] instanceof InternetAddress)
			{
				names[i] = (((InternetAddress)receivers[i]).getPersonal());
			}	
		return names;
	}
	
	
	@Override
	public String toString() 
	{
		return "EmailEvent [startDate=" + startDate + ", message=" + message+ "]";
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
		return message.getRecipients(Message.RecipientType.BCC); 
	}
	
	
	/** @return size of E-mail/content in bytes.
	 * @return -1 if the size cannot be determined. 
	 * @throws MessagingException */
	public int getSize() throws MessagingException
	{
		return message.getSize();
	}
	
	
	/** @returns If Email has an attachment, the file name is returned. If not, returns
	 * 'None'
	 * @throws MessagingException*/
	public String attachment() throws MessagingException
	{
		String disposition = message.getDisposition();
		if (disposition != null)
			return disposition;
		else
			return "None";
	}
	
	
	/** @return an array of user made flags as strings 
	 * @throws MessagingException */
	public String [] getUsrFlags() throws MessagingException
	{
		return message.getFlags().getUserFlags();
	}
	
	
	/**@return an array of system made flags as strings
	 * @throws MessagingException */
	public String [] getSysFlags() throws MessagingException
	{
		return message.getFlags().getUserFlags();
	}
}
