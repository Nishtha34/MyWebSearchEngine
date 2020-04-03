import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
Indexing webpages
Author: Namrata Ajmeri
**/
public class MyWebSpider 
{
	private HashSet<String> webLinks;
	ArrayList<String> arrayList = new ArrayList<String>();
	private static final int MAX_DEPTH = 4;

	public MyWebSpider()
	{
		webLinks = new HashSet<String>();
	}
	public void saveUrl(final String filename, final String stringUrl) throws MalformedURLException, IOException {
		{
			try 
			{
				// Create myURL object
				URL url = new URL(stringUrl);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

				// Enter filename where you want to download
				String stringFilename = filename + "_wiki" + ".html";

				// Web pages will be downloaded the specified path
				BufferedWriter bufferedWriter = new BufferedWriter(
						new FileWriter("C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\src\\HTMLFiles\\" + stringFilename));

				// read each line from stream till end
				String line;
				while ((line = bufferedReader.readLine()) != null) 
				{
					bufferedWriter.write(line);
				}

				bufferedReader.close();
				bufferedWriter.close();
			}
			catch (MalformedURLException mue) 
			{
				System.out.println("Malformed URL Exception raised");
			} 
			catch (IOException ie) 
			{
				System.out.println("IOException raised");
			}
		}
	}
	public void getPageLinks(String URL, int depth) 
	{
		if (!webLinks.contains(URL) && (depth < MAX_DEPTH))
		{
			try 
			{
                if (webLinks.add(URL)) 
                {
                    System.out.println(URL);
                    arrayList.add(URL);
					int i = arrayList.indexOf(URL);
                    saveUrl(Integer.toString(i), URL);
                }

                Connection con = Jsoup.connect(URL);
                Connection.Response resp = con.execute();
                if (resp.statusCode() == 200)
                {
                	Document document = con.get();
                    Elements linksOnPage = document.select("a[href]");

                    depth++;
                    for (Element page : linksOnPage) 
                    {
                        getPageLinks(page.attr("abs:href"), depth);
                    }
                }
            } 
			catch (IOException e) 
			{
                System.out.println(" URL : " + e.getMessage());
            }
		}
	}
	/**
	 * Downloads web pages from the specified url using BFS approach
	 * @param args
	 * @author Namrata Ajmeri
	 */
	public static void main(String[] args) 
	{
		MyWebSpider myWebSpider = new MyWebSpider();
		int initialDepth = 0;
		myWebSpider.getPageLinks("https://en.wikipedia.org/wiki/Main_Page/", initialDepth);
	}
}
