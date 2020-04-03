import java.io.*;

import org.jsoup.Jsoup;

public class HTMLTextConverter 
{
	/**
	 * This method converts HTML files into text files.
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public static void convertHtmlToText() 
			throws IOException, FileNotFoundException, NullPointerException
	{
		org.jsoup.nodes.Document myDocument = null;
		BufferedWriter bufferedWriter = null;

		try
		{
			File dir = new File("C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\src\\HTMLFiles\\");
			File[] files = dir.listFiles();
			int i = 0;
			for (File file : files) 
			{
				myDocument = Jsoup.parse(file, "UTF-8");
				String myString = file.getName().substring(0, file.getName().lastIndexOf('.'));
				bufferedWriter = new BufferedWriter(new FileWriter(
						"C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\ConvertedTextFiles\\" + myString + ".txt"));
				bufferedWriter.write(myDocument.text());
				bufferedWriter.close();
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
