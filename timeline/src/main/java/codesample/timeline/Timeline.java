package codesample.timeline;

import java.util.*;

import org.joda.time.DateTime;

/**
 * This class implements an ordered collection that holds Event objects.
 *
 */
public class Timeline implements Collection<Event> 
{
//	public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("mm/dd/yyyy  kk:mm"); //"month/day/year  24hour:minutes of the hour"
	
	private TreeMap<DateTime, HashSet<Event>> eventMap = new TreeMap<DateTime, HashSet<Event>>();
	
	public boolean add(Event e) {
		return addToEventMap(eventMap, e);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Event> c) {
		
		if (c !=null)
		{
			Iterator<? extends Event> eventIter = c.iterator();
			while (eventIter.hasNext())
				this.add(eventIter.next());
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		eventMap.clear(); // Clears the map
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		
		if (o==null || !(o instanceof Event))
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (eventMap.containsKey(startTime)) // Check to see if the key is there.
		{
			// Then check if the event is there
			if (eventMap.get(startTime).contains(myEvent))
			{
				return true;
			}
			else
				return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		
		if (c !=null)
		{
			Iterator<?> eventIter = c.iterator();
			while (eventIter.hasNext())
			{
				if(!this.contains(eventIter.next()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty(){
		if (eventMap.isEmpty()) // returns true if the map is empty
		{
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<Event> iterator()
	{
		return new TimelineIterator();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (o==null || !(o instanceof Event)) //if the object is null or not event return false, cannot remove
		{
			return false;
		}
		
		Event myEvent = (Event)o;
		DateTime startTime = myEvent.getStart();
		
		if (!eventMap.containsKey(startTime)) // if the eventMap does not contain the key return false, cannot remove
		{
			return false;
		}
		
		if (eventMap.get(myEvent.getStart()).size()==1) // If the size of the set contains one event, remove the key
		{
			eventMap.remove(myEvent.getStart());
			return true;
		}
				
		HashSet<Event> events = eventMap.get(startTime); // else, remove the event located at a given key
		return events.remove(myEvent);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		if (c !=null)
		{
			Iterator<?> eventIter = c.iterator(); // iterate through the collection
			while (eventIter.hasNext())           // remove all instances in eventMap from collection
				this.remove(eventIter.next());
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		
		if(c!=null)
		{
			TreeMap<DateTime, HashSet<Event>> newEventMap = new TreeMap<DateTime, HashSet<Event>>();
			
			for (Object e: c)
			{
				if (e instanceof Event && this.contains(e))
				{
					addToEventMap(newEventMap, (Event)e);
				}
			}

			if (sizeEventMap(eventMap) == sizeEventMap(newEventMap))
			{
				return false;
			}
			
			eventMap= newEventMap;
			return true;
		}		
		return false;	
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	public int size() {	
		return sizeEventMap(eventMap);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		if (!eventMap.isEmpty())
		{
			ArrayList<Event> list = new ArrayList<Event>();
			for(DateTime key: eventMap.keySet())
			{
				for(Event e: eventMap.get(key))
				{		
					list.add(e);	
				}
			}	
			return list.toArray();
		}
	
		Object [] result = null;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public <T> T[] toArray(T[] a) {
		if (!eventMap.isEmpty())
		{
			ArrayList<Event> list = new ArrayList<Event>();
			
			for(DateTime key: eventMap.keySet())
			{
				for(Event e: eventMap.get(key))
				{
					for(int i=0; i<sizeEventMap(eventMap); i++)
					{
						System.out.println("[");
						list.add(e);
						System.out.println("]");
					}
				}
			}	
			return list.toArray(a);
		}
	
		T [] result = null;
		return result;
	}
	
	
	/**
	 * Adds an Event to a specified map of DateTimes to sets of Events.
	 * 
	 * @param mapOfEvents The map of DateTimes to sets of Events to add the event to
	 * @param e Event to add to the passed-in TreeMap of events
	 * @return
	 */
	private boolean addToEventMap(TreeMap<DateTime, HashSet<Event>> mapOfEvents, Event e) {
		if (e != null)
		{
			if(!mapOfEvents.containsKey(e.getStart()))   // If the DateTime is not there create a new key and hash set
			{
				mapOfEvents.put(e.getStart(), new HashSet<Event>()); // Places the DateTime and an empty Set into the map
			}
				mapOfEvents.get(e.getStart()).add(e); // Adds the new event to the empty Set in the given DateTime
				return true;
		}
		return false;
	}
	
	/**
	 * Calculates the total size of a map of DateTimes to sets of Events
	 * 
	 * @param mapOfEvents The map of DateTimes to sets of Events to add the event to
	 * @return 0 if empty, total number of events otherwise
	 */
	private int sizeEventMap(TreeMap<DateTime, HashSet<Event>> mapOfEvents) {
		
		int count =0; // Count variable to keep track of all the values in eventMap

		if (!mapOfEvents.isEmpty())
		{
			for(HashSet<Event> set: mapOfEvents.values())
			{
				count += set.size();
			}
				return count;
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (DateTime key: eventMap.keySet()) //the keys.
		{
			sb.append("[<").append(key).append(">: ");
			for (Event e: eventMap.get(key)) //gets each individual event  
			{
				sb.append("[").append(e).append("]");
				
			}
			sb.append("\n");
		}
		
		
		return sb.toString();
	}
	
	/**
     * This Iterator is designed to walk through the Timeline as if it were an
     * asymmetrical 2-dimensional array. To do this, we're only keeping track of
     * indexKey and indexValue and doing the navigation through the underlying
     * eventMap to determine what will actually work.
     * 
     * Does not implement the optional add, remove, or set operations
     *
     */
    private class TimelineIterator implements ListIterator<Event>

    {
        private int eventSetIndex = 0;  // Keeps track of which set you are currently on
        private int eventIndex = 0;     // Keeps track of which index inside the set you are on
        private int eventsPosition = 1; // Keeps track of nextIndex()/previousIndex()
        
        private HashSet<Event> eventSet; // Represents each individual set
        private Event[] events;          // Represents an array of all sets for printing in next()/previous()
        
        Object[] eventSets = new Object[0];
        
        /**
         *  No-argument protected constructor.
         */
        protected TimelineIterator()
        {
            if (eventMap != null && !eventMap.isEmpty())
            {
                Collection<HashSet<Event>> valueCollection = eventMap.values();
                if (valueCollection != null)
                {
                    eventSets = valueCollection.toArray();
                }
            }            
        }
        

        /* (non-Javadoc)
         * @see java.util.ListIterator#hasNext()
         */
        public boolean hasNext() 
        {
        	
        	if (eventSetIndex >= eventSets.length) { // If there are no more sets, retufn false
        		return false;
        	}
        	
        	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
       	        	
            if (eventIndex < eventSet.size()) // if the current index in set is less than the size, return true
            {
               return true;
            }
            
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size()) // If you are on the last element, return false
            {
            	return false;
            }
            else {
            	if (eventSetIndex < eventSets.length) {
            		return true;
            	}
            	else {
            		return false;
            	}
            }
            
        }

        
        /* (non-Javadoc)
         * @see java.util.ListIterator#next()
         */
        public Event next()
        {
        	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
        	events = (Event[]) eventSet.toArray(new Event[0]);
        	Event nextEvent = null;
      
            if (eventIndex < events.length)
            {
               nextEvent = events[eventIndex];
               eventIndex++;
               eventsPosition++;
            } else {
            	eventIndex = 0;
            	eventSetIndex ++;
            	eventsPosition++;
            	if (eventSetIndex < eventSets.length) {
            		HashSet<Event> eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		Event[] events = (Event[]) eventSet.toArray(new Event[0]);
            		nextEvent = events[eventIndex];
            		eventIndex++;
            		eventsPosition++;
            	} else {
                    throw new NoSuchElementException();
            	}
            }
            
            if(eventSets.length-1 == eventSetIndex && eventIndex==eventSet.size()) // If you are on the very last element, stop
            {
            	return nextEvent;
            }
              
            if (eventIndex >= events.length) { // If you moved to the end of a set, move to the next set
            	eventIndex = 0;
            	eventSetIndex ++;
            }
            return nextEvent;
        }

        /* (non-Javadoc)
         * @see java.util.ListIterator#hasPrevious()
         */
        public boolean hasPrevious()
        {
        	
        	if (eventSetIndex==0 && eventIndex==0)
        	{
        		return false;
        	}
        	
            if (eventIndex > 0)
            {
               return true;
            }
            else {
            	if (eventSetIndex > 0) { // there is a previous set
            		return true;
            	}
            	else {
            		return false;
            	}
            }
        }

        /* (non-Javadoc)
         * @see java.util.ListIterator#previous()
         */
        public Event previous() 
        {
			eventSet = (HashSet<Event>)eventSets[eventSetIndex];
        	events = (Event[]) eventSet.toArray(new Event[0]);
        	Event previousEvent = null;
        	
        	if (eventSetIndex==0 && eventIndex==0)
        	{
        		throw new NoSuchElementException();
        	}
      
            if (eventIndex > 0)
            {
               eventIndex--;  
               previousEvent = events[eventIndex];
            } else {
            	eventSetIndex --;
            	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            	eventIndex = eventSet.size();
            	if (eventSetIndex > 0) {
            		eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            		events = (Event[]) eventSet.toArray(new Event[0]);
            		eventIndex--;
            		eventsPosition--;
            		previousEvent = events[eventIndex];
            	}
            }
            
            if (eventSetIndex > 0 && eventIndex == 0) { // If after you moved back you are on the first element of a set, go back to the last element of the previous set
            	eventSetIndex --;
            	eventSet = (HashSet<Event>)eventSets[eventSetIndex];
            	eventIndex = eventSet.size();     	
            }
            return previousEvent;
        }
        

        /* (non-Javadoc)
         * @see java.util.ListIterator#nextIndex()
	     */
        public int nextIndex() //of the all the events or just the events in a particular set.
		{
        	return eventsPosition;
        }

		
        /* (non-Javadoc)
         * @see java.util.ListIterator#previousIndex()
         */
        public int previousIndex() {
            return eventsPosition-1; // Since eventsPosition is declared at 1 subtract one when going backwards
        }

        /* (non-Javadoc)
         * @see java.util.ListIterator#remove()
         */
        public void remove()
        {
            throw new UnsupportedOperationException(
                    "We're not implementing remove!");
        }

        /** Not implemented by Timeline
         *
         */
        public void set(Event e)
        {
            throw new UnsupportedOperationException(
                    "We're not implementing set!");
        }

        /** Not implemented by Timeline
        *
        */
       public void add(Event e)
        {
        	throw new UnsupportedOperationException(
                    "We're not implementing add!");
        }
    }
}
