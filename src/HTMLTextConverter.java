import java.io.*;

import org.jsoup.Jsoup;

public class HTMLTextConverter {
	// public static void main(String[] args) throws FileNotFoundException,
	// NullPointerException, IOException {
	// convertHtmlToText();
	// }

	// This method converts HTML Files into text documents.
	public static void convertHtmlToText() throws IOException, FileNotFoundException, NullPointerException {
		org.jsoup.nodes.Document my_doc = null;
		BufferedWriter my_out = null;

		try {
			File dir = new File("C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\src\\HTMLFiles\\");
			File[] file_Array = dir.listFiles();
			int i = 0;
			for (File file1 : file_Array) {
				// System.out.println(i);
				// i++;
				my_doc = Jsoup.parse(file1, "UTF-8");
				String my_str = file1.getName().substring(0, file1.getName().lastIndexOf('.'));
				my_out = new BufferedWriter(new FileWriter(
						"C:\\Users\\Namrata\\eclipse-workspace\\MyWebSearchEngine\\ConvertedTextFiles\\" + my_str + ".txt"));
				my_out.write(my_doc.text());
				my_out.close();
				// System.out.println("File " + file.getName() + " converted into " +
				// file.getName()+ ".txt successfully");

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
