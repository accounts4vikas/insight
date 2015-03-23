package org.insight.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RunningMedianDemo {

	public static void main(String[] args) 
			throws FileNotFoundException, IOException {
		
		RunningMedian runningMedian = new RunningMedian();
		FileListings listing = new FileListings();

		File inputDir = new File("wc_input");
		PrintWriter writer = new PrintWriter("wc_output/med_result.txt", "UTF-8");
		
		List<File> files = listing.getFileListing(inputDir);

		for (File file : files) {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				int wordsCount = 0;
				if (line.length() != 0) {
					String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
					wordsCount = words.length;
				}
				runningMedian.addLineWordCount(wordsCount);

				writer.println(runningMedian.getMedian());
				line = br.readLine();
			}
		}
		
		writer.close();		

	}

}
