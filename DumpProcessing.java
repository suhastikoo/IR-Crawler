package assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class DumpProcessing {

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		
		// Putting Stop Words in List.
		Hashtable<String,Integer> stopWordsList = new Hashtable<String,Integer>();
		String path = "D:/CrawlerData/StopWords.txt";		
		String TempLine;
		try {
			BufferedReader TextFile = new BufferedReader(new FileReader(path));
			while((TempLine = TextFile.readLine()) != null){
				stopWordsList.put(TempLine, 1);
			}
//			for (Map.Entry<String, Integer> a : stopWordsList.entrySet()){
//				System.out.println(a.getKey());
//			}
			
			TextFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Read the dump files and tokenize it.
		//MyCrawler crawlerObj = new MyCrawler();
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		List<String> TempList = new ArrayList<String>();
		String folderLocation = "D:/CrawlerData/WebData/ParseData";
		String readUrl = "D:/CrawlerData/urlList";
		String TempLine1;
		String TempLine2;
		int count = 0;
		int initialWords = 0;
		
		String pageUrl = "";
		try {
		BufferedReader TextFile1 = new BufferedReader(new FileReader(readUrl));
		while((TempLine1 = TextFile1.readLine()) != null){
			int finalWords = 0;
			String hash = Integer.toString(TempLine1.hashCode());
			String fileLocation = folderLocation.concat(hash);
			BufferedReader TextFile = new BufferedReader(new FileReader(fileLocation));
				while((TempLine2 = TextFile.readLine()) != null){
					if (!TempLine2.isEmpty()){
						table = ComputeWordFrequencies(TokenizeFile(TempLine2, TempList, stopWordsList, finalWords).List, table);
						finalWords = TokenizeFile(TempLine2, TempList, stopWordsList, finalWords).numberOfWords;
						TempList.clear();
					}
				}
				if (finalWords > initialWords){
					initialWords = finalWords;
					pageUrl = TempLine1;
				}
				TextFile.close();
				count += 1;
			}
		TextFile1.close();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		Print(table);
		//PrintWordCount(table);
		System.out.println("Number of files accessed: " + count);
		//System.out.println(table.size());
		System.out.println("Longest page: " + pageUrl);
		System.out.println("Number of words: " + initialWords);
		Long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/60000 + " minutes");
	}
		
		// Prints out the total word count in table.
		public static void PrintWordCount(Hashtable<String, Integer> table) {
			int result = 0;
			for (Integer value : table.values()){
				result = result + value;
			}
			System.out.println("Total count in table: " + result);		
		}

		//Take a string and list as input and return a list of tokens containing only alphanumeric characters.
		public static ReturnObject TokenizeFile(String TempLine, List<String> TempList, 
				Hashtable<String, Integer> stopWordsList, int numberOfWords){
			//String temp = "aren't";
			//TempLine = TempLine.replaceAll(temp, " ");
			
			TempLine = TempLine.replaceAll("[^a-zA-Z0-9']", " ");
			
			StringTokenizer fileIn = new StringTokenizer(TempLine);	
			
			while (fileIn.hasMoreTokens()){
				String a = fileIn.nextToken();
				//System.out.println(a);
				a = a.replaceAll("[']", "");
				//a = a.replaceAll(" ", "");
				if (!stopWordsList.containsKey(a)){
					TempList.add(a);
				}
				numberOfWords += 1;
			}
			return new ReturnObject(TempList,numberOfWords);
			
		}
		
		// For tokens in list, compute their frequency and put in table.
		public static Hashtable<String, Integer> ComputeWordFrequencies(List<String> List, Hashtable<String, Integer> table) {
			for (String a : List){
				a = a.toLowerCase();
				if (table.containsKey(a)){
					Integer value = (Integer)table.get(a);
					value += 1;
					table.put(a, value);
				}
				else{
					table.put(a, 1);
				}
			}
			return table;		
		}
		public static void Print(Hashtable<String, Integer> table) {
			List<Map.Entry<String, Integer>> obj = new ArrayList<Entry<String, Integer>>(table.entrySet());
			Collections.sort(obj, new Comparator<Map.Entry<String, Integer>>(){
				public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
		            Integer ent1 = entry1.getValue();
		            Integer ent2 = entry2.getValue();
					return ent2.compareTo(ent1);
		        }});
			int length = 1;
			for (Map.Entry<String, Integer> entry : obj){
				if (length <= 500){
					System.out.printf("%-25s%d%n", entry.getKey(), entry.getValue());
					length += 1;
				}
				else{
					break;
				}
				
			}
			return;
		}
	}

