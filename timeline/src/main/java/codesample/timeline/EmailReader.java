package codesample.timeline;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.joda.time.DateTime;

//A class that simply reads E-mails while EmailEvent is situational.
public class EmailReader //I need to extend or implement something.
{
	Path start = Paths.get("C:/Users/mcarlton/Desktop/allen-p._sent_mail.15Emails");
	Files.walkFileTree(start, new SimpleFileVistor<path>() {
		public FileVisitResult file(Path filePath, BasicFileAttributes attrs) throws IOException
		{
			
		}
	}
	

}



