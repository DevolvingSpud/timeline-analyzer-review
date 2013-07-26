package codesample.timeline;

import org.joda.time.DateTime;

import codesample.timeline.AbstractEvent;

public class EmailEvent extends AbstractEvent
{
	private Object emailEvent;
	
	public EmailEvent(DateTime start, String name) 
	{
		super(start);
		emailEvent = name;
	}
	
	//return messageID - might be a significance to this later.
	public int getMessageID()
	{
		return 0;
	}
	
	//return timeSent
	public DateTime getStart()
	{
		return null;
	}
	
	//return timeStart
	public DateTime getEnd() 
	{
		return null;
	}
	
	//return timeZone
	public String getTimeZone()
	{
		return null;
	}
	
	//return sender's Email contact
	public String getSenderEmail()
	{
		return null;
	}
	
	//return receiver's Email contact
	public String getReceiverEmail()
	{
		return null;
	}
	
	//return if both the sender and receiver both have company E-mails.
	public boolean isBothFromCompany()
	{
		return false;
	}
	
	//return subject.
	public String getSubject()
	{
		return null;
	}
	
	//return sender's name
	public String getSenderName()
	{
		return null;
	}
	
	//return receiver's name
	public String getReceiverName()
	{
		return null;
	}
	
	//return cc = "Carbon Copy" people's E-mails
	public String getCC()
	{
		return null;
	}
	
	//return bcc people's E-mails
	public String getBCC()
	{
		return null;
	}
}
