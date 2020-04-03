import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Nishtha Patel
 * @author Namrata Ajmeri
 * @author Ashmita Bharti
 *
 */
public class WebSearchEngine 
{
	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();

	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		System.out.print(">>Welcome to our Search\n>>Team Members:"
				+ "\n\t Namrata Ajmeri"
				+ "\n\t Nishtha Patel"
				+ "\n\t Asmita Bharti");

		System.out.print("\n>>Enter your search:\n>>");
		
		WebSearchEngine webSearchEngine = new WebSearchEngine();

		Hashtable<String, Integer> hashTable = new Hashtable<String, Integer>();

		String pattern = input.nextLine();
		long my_fileNumber = 0;
		int frequency = 0;
		int count = 0; // Number of files that contains the Searched word
		try {
			Instant start = Instant.now();
			long startTimesearch = System.currentTimeMillis();
			File webpagesource = new File("C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\src\\HTMLFiles");

			File[] fileArray = webpagesource.listFiles();

			System.out.println("Searching... \n");
			for (int i = 0; i < fileArray.length; i++) {

				frequency = webSearchEngine.searchPattern(fileArray[i], pattern);

				hashTable.put(fileArray[i].getName(), frequency);
				
				if (frequency != 0) {
					count++;
				}

				my_fileNumber++;
			}
			System.out.println("\nNo. of Files for the search '" + pattern + "'" + " : " + count);
			long endTimesearch = System.currentTimeMillis();
			
			if (count == 0) 
			{
				System.out.println("\nExact word not found. Searching related word...");
				webSearchEngine.suggestions(pattern);
			}
			
			rankFiles(hashTable, count);
			
	    	Instant end = Instant.now();
	    	Duration interval = Duration.between(start, end); 

			System.out.println("\nSearched in about " + interval.getSeconds() + " seconds ");

		} 
		catch (Exception e)
		{
			System.out.println("Exception:" + e);
		} 
	}
	// Positions and Occurrences of the words
	public int searchPattern(File filePath, String pattern) throws IOException 
	{
		int my_counter = 0;
		String fileText = "";
		
		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String line = null;

			while ((line = bufferedReader.readLine()) != null)
			{
				fileText = fileText + line;
			}
			bufferedReader.close();

			String textinFile = fileText;
		
			BoyerMoore boyerMoore = new BoyerMoore(pattern);

			int offset = 0;

			for (int loc = 0; loc <= textinFile.length(); loc += offset + pattern.length()) 
			{
				offset = boyerMoore.search(pattern, textinFile.substring(loc));
				if ((offset + loc) < textinFile.length()) 
				{
					my_counter++;
				}
			}
		}
		catch (NullPointerException e) 
		{
			e.printStackTrace();
		}	
		return my_counter;
	}

	public static void rankFiles(Hashtable<?, Integer> fname, int numberOfOccurrences) 
	{
		// Transfer as List and sort it
		ArrayList<Map.Entry<?, Integer>> arrayList = new ArrayList(fname.entrySet());

		Collections.sort(arrayList, new Comparator<Map.Entry<?, Integer>>() 
		{
			public int compare(Map.Entry<?, Integer> obj1, Map.Entry<?, Integer> obj2) 
			{
				return obj1.getValue().compareTo(obj2.getValue());
			}
		});

		Collections.reverse(arrayList);

		if (numberOfOccurrences > 0) 
		{
			System.out.println("\n------Top 10 search results-----\n");

			int my_number = 10;
			int j = 1;
			while (arrayList.size() > j && my_number > 0) 
			{
				System.out.println("(" + j + ") " + arrayList.get(j) + " times ");
				j++;
				my_number--;
			}
		} 
	}

	//Use of regular expressions and edit distance for spell checking
	public void suggestions(String mispelledPattern) 
	{
		try 
		{
			// String scanned to find the pattern.
			String line = " ";
			String reg_ex = "[\\w]+[@$%^&*()!?=.{}\b\n\t]*";

			// Create a Pattern object
			Pattern pat = Pattern.compile(reg_ex);
			// Creating matcher object.
			Matcher my_match = pat.matcher(line);
			int file_Number = 0;
			try 
			{
				File my_directory = new File("C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\src\\Check");
				File[] fileArray = my_directory.listFiles();
				for (int i = 0; i < fileArray.length; i++)

				{
					findWord(fileArray[i], file_Number, my_match, mispelledPattern);
					file_Number++;
				}

				Set keys = new HashSet();
				Integer val = 0;
				Integer value = 1;
				
				int counter = 0;

				System.out.println("\nDid you mean? ");
				for (Map.Entry entry : numbers.entrySet()) 
				{
					if (val == entry.getValue()) 
					{
						break;
					}
					else 
					{
						if (value == entry.getValue()) 
						{
							if (counter == 0)
							{
								System.out.print(entry.getKey());
								counter++;
							}
							else 
							{
								System.out.print(" or " + entry.getKey());
								counter++;
							}
						}
					}
				}
			} 
			catch (Exception e) 
			{
				System.out.println("Exception:" + e);
			} 
			finally 
			{
			}
		} 
		catch (Exception e) 
		{

		}
	}

	// finds strings with similar pattern and calls edit distance() on those strings
	public static void findWord(File sourceFile, int fileNumber, Matcher match, String str)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException 
	{
		try 
		{
			int i = 0;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
			String line = null;

			while ((line = bufferedReader.readLine()) != null) 
			{
				match.reset(line);
				while (match.find()) 
				{
					key.add(match.group());
				}
			}
			
			bufferedReader.close();
			for (int p = 0; p < key.size(); p++) 
			{
				numbers.put(key.get(p), editDistance(str.toLowerCase(), key.get(p).toLowerCase()));
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Exception:" + e);
		}
	}

	public static int editDistance(String string1, String string2) 
	{
		int len1 = string1.length();
		int len2 = string2.length();

		int[][] my_array = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			my_array[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			my_array[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = string1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = string2.charAt(j);

				if (c1 == c2) {
					my_array[i + 1][j + 1] = my_array[i][j];
				} else {
					int replace = my_array[i][j] + 1;
					int insert = my_array[i][j + 1] + 1;
					int delete = my_array[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					my_array[i + 1][j + 1] = min;
				}
			}
		}

		return my_array[len1][len2];
	}


}
