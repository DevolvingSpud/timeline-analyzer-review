package codesample.timeline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

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
	private Timeline timeline;
	
	/**
	 * Reads all of the emails that are found within the given path/directory tree and adds
	 * them to our internal timeline. Works recursively.
	 * 
	 * @param path
	 *            the path to check for emails
	 * @throws FileNotFoundException
	 *             if the input path cannot be found
	 */
	public Timeline readEmails(Path path) throws FileNotFoundException, IOException
	{
		timeline = new Timeline();
		if (Files.exists(path))
			Files.walkFileTree(path, new EmailVisitor());
		else 
			throw new FileNotFoundException(path + " not found");
		return timeline;
	}
	
	private class EmailVisitor extends SimpleFileVisitor<Path>
	{
		private int count = 0;
		
		/**
		 * Instructions for when a desired file is read.  When read, the file is then 
		 * counted for testing purposes, made into a Mime Message, parsed into EmailEvents,
		 * sent into a timeline.
		 * @throws IOException 
		 */
		
		//write the purpose of the exception above
		public FileVisitResult visitFile(Path path, BasicFileAttributes attr) throws IOException
		{	
			InputStream mailFileInputStream = new FileInputStream(path.toString());
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			
			try{
				MimeMessage message = new MimeMessage(session, mailFileInputStream);
				EmailEvent e = new EmailEvent(message); //make EmailEvent from message
				timeline.add(e);
				
				System.out.println("Mime Message:"+message.getSubject());
				
				//System.out.println("Email Header: "+message.getAllHeaderLines().toString());
			} catch (MessagingException e){
				e.printStackTrace();
			} finally{
				mailFileInputStream.close();
			}
			
			count++;
			System.out.println("[" + count + "]:" + path.toString());
			return CONTINUE;
			
		}
	}
}


