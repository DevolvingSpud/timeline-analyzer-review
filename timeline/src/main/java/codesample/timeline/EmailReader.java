package codesample.timeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;


/**
 * This class is to create a timeline from all E-mails that exist within a
 * specific path.
 * @author mcarlton
 */
public class EmailReader 
{
	/**
	 * Make new timeline.
	 */
	private Timeline timeline = new Timeline();


	/**
	 * @return the current timeline
	 */
	public Timeline getTimeLine() 
	{
		return timeline;
	}
	
	/**
	 * Reads all of the emails that are found within the given path/directory tree and adds
	 * them to our internal timeline. Works recursively.
	 * 
	 * @param path
	 *            the path to check for emails
	 * @throws FileNotFoundException
	 *             if the input path cannot be found
	 */
	public void readEmails(Path path) throws FileNotFoundException, IOException
	{
		
		if (Files.exists(path))
		{
			Files.walkFileTree(path, new EmailVisitor());
		} else {
			throw new FileNotFoundException(path + " not found");
		}
	}
	
	private class EmailVisitor extends SimpleFileVisitor<Path>
	{
		private int count = 0;
		
		//I can not think of a reason I would need any other case than this one.
		/**
		 * Instructions for when a desired file is read.  When read, the file is then 
		 * counted for testing purposes.
		 */
		public FileVisitResult visitFile(Path path, BasicFileAttributes attr)
		{	
			count++;
			System.out.println("[" + count + "]:" + path.toString());
			return CONTINUE;
			
		}
	}
}


