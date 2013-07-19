package codesample.timeline;

import org.joda.time.DateTime;

public class EmailEvents extends AbstractEvent
{
	private Object emailEvent;
	
	public EmailEvents(DateTime start, String name) 
	{
		super(start);
		emailEvent = name;
	}
	
	public Object getEvent()
	{
		return emailEvent;
	}
	
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailEvents other = (EmailEvents) obj;
		if (emailEvent == null)
		{
			if (other.emailEvent != null)
				return false;
		} 
		else if (!emailEvent.equals(other.emailEvent))
			return false;
		return true;
	
	}	
}
