package codesample.timeline;

import org.joda.time.DateTime;

import codesample.timeline.util.NamedEventGenerator;

public class TestQueryMain {

	public static void main(String[] args) {
		Timeline timeline = new Timeline();
		
		timeline.addAll(NamedEventGenerator.generate(new DateTime(1993,1,1,0,0), new DateTime(2010,12,30,0,0), (3000000), (10), (2), (5), (5), (5)));
		
//		timeline.includedInQuery(new DateTime(2000,1,1,0,0), new DateTime(2003,12,25,0,0));
		
	}

}
