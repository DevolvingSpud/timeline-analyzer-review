package codesample.timeline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import org.junit.Test;

public class TestEmailReader 
{
	
	@Test
	public void testEmailReader() {
		EmailReader er = new EmailReader();
		String path = "C:/Users/mcarlton/Desktop/allen-p._sent_mail.15Emails";
		try 
		{
			er.readEmails(FileSystems.getDefault().getPath(path));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
