package codesample.timeline.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;

import codesample.timeline.NamedEvent;

/**
 * A generator for NamedEvent objects to be used for testing purposes.
 * <p>
 * The idea of the <em>NamedEventGenerator</em> is to allow large numbers of
 * events to be generated for testing purposes. The events will use a standard
 * naming convention and will be randomized between two dates, with the idea of
 * maintaining an average number of event 'collisions' at particular start
 * dates. This will allow us to properly exercise our timeline and allow us to
 * ensure that things work when dealing with large numbers of events.
 * 
 * @author Lamont
 * 
 */
public class NamedEventGenerator {

	// The default timeframe for our timeline in seconds
	public static long DEFAULT_TIMEFRAME_SECONDS = 86400L;

	// The default maximum interval in seconds
	public static int DEFAULT_MAX_INTERVAL_SECONDS = 86400;

	// The default seed
	public static long DEFAULT_SEED = 11223344L;

	// The default number of collisions to try and maintain
	public static int DEFAULT_NUM_COLLISIONS = 5;

	/**
	 * Generates a list of events given very specific criteria.
	 * <p>
	 * Generates a list of <em>eventsToGenerate</em> events that are guaranteed
	 * to be between <em>fromDate</em> and <em>toDate</em>. Additionally, they
	 * will fill the following criteria:
	 * <ul>
	 * <li>They will be of <em>maxInterval</em> length</li>
	 * <li>They will average somewhere around <em>averageCollisions</em> events
	 * with the same time</li>
	 * </ul>
	 * The randomizers will be seeded with the appropriate seeds
	 * <em>startSeed</em>, <em>listSeed</em>, and <em>intervalSeed</em> so that
	 * the system can produce the same start dates.
	 * 
	 * @param fromDate
	 *            The date to start the events from
	 * @param toDate
	 *            The date that the events should go to
	 * @param eventsToGenerate
	 *            The number of events to generate
	 * @param maxInterval
	 *            The maximum interval size
	 * @param averageCollisions
	 *            The average size of the buckets. Used to guarantee collisions.
	 * @param startSeed
	 *            The seed used for the start date randomizer.
	 * @param listSeed
	 *            The seed used for the list randomizer.
	 * @param intervalSeed
	 *            the seed used for the interval randomizer.
	 * @return The list of generated events
	 */
	public static List<NamedEvent> generate(DateTime fromDate, DateTime toDate,
			long eventsToGenerate, int maxInterval, int averageCollisions,
			long startSeed, long listSeed, long intervalSeed) {

		// Create the randomizer for starts
		Random startRandom = new Random(startSeed);
		long dateRange = toDate.getMillis() - fromDate.getMillis();

		// Create the buckets. In order to guarantee that we get collisions, we
		// are going to create the start dates and select from the list.
		ArrayList<DateTime> dateList = new ArrayList<DateTime>();
		long bucketsToGenerate = (averageCollisions == 0) ? eventsToGenerate
				: (long) eventsToGenerate / averageCollisions;
		while (bucketsToGenerate > 0) {
			dateList.add(fromDate.plus(Math.abs(startRandom.nextLong()
					% dateRange)));
			--bucketsToGenerate;
		}

		LinkedList<NamedEvent> eventList = new LinkedList<NamedEvent>();
		Random listRandom = new Random(listSeed);
		Random endTimeRandom = new Random(intervalSeed);

		for (long i = 0L; i < eventsToGenerate; i++) {
			// Generate a name for you
			StringBuilder sb = new StringBuilder();
			sb.append("Generated #").append(i);

			// Determine the actual start date to use
			DateTime startDate = dateList.get(listRandom.nextInt(dateList
					.size()));

			// Determine the end date to use
			DateTime endDate = startDate.plusSeconds(Math.abs(endTimeRandom
					.nextInt() % maxInterval));
			eventList.add(new NamedEvent(sb.toString(), startDate, endDate));
		}

		return eventList;
	}

	/**
	 * A static method to generate a list of events given minimal criteria.
	 * <p>
	 * The events will be generated within a day of the start date and will last
	 * for at most maxInterval.
	 * 
	 * @param fromDate
	 *            the date to generate the events from
	 * @param numToGenerate
	 *            the number of events to generate
	 * @param maxInterval
	 *            the maximum interval
	 * @return the generated list of events
	 */
	public static List<NamedEvent> generate(DateTime fromDate,
			long numToGenerate, int maxInterval) {
		return generate(fromDate,
				fromDate.plusSeconds((int) DEFAULT_TIMEFRAME_SECONDS),
				numToGenerate, maxInterval, DEFAULT_NUM_COLLISIONS,
				DEFAULT_SEED, DEFAULT_SEED, DEFAULT_SEED);
	}

	/**
	 * A static method to generate events.
	 * <p>
	 * The events will generated within a day of fromDate and will last for at
	 * most one day.
	 * 
	 * @param fromDate
	 *            the date to generate the events from
	 * @param numToGenerate
	 *            the number of events to generate
	 * @return the generated list of events
	 */
	public static List<NamedEvent> generate(DateTime fromDate,
			long numToGenerate) {
		return generate(fromDate,
				fromDate.plusSeconds((int) DEFAULT_TIMEFRAME_SECONDS),
				numToGenerate, DEFAULT_MAX_INTERVAL_SECONDS,
				DEFAULT_NUM_COLLISIONS, DEFAULT_SEED, DEFAULT_SEED, DEFAULT_SEED);
	}

}