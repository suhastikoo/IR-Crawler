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

public class TwoGramFrequency {

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		Hashtable<String,Integer> stopWordsList = new Hashtable<String,Integer>();
		String path = "D:/CrawlerData/StopWords.txt";		
		String TempLine;
		try {
			BufferedReader TextFile = new BufferedReader(new FileReader(path));
			while((TempLine = TextFile.readLine()) != null){
				stopWordsList.put(TempLine, 1);
			}
			TextFile.close();
		} 
		catch (IOException e) {
			System.out.println("Error!!!");
		}
		
		//System.out.println(stopWordsList);
		
		Hashtable<Token, Integer> TokenFreqTable = new Hashtable<Token, Integer>();
		List<String> TempList = new ArrayList<String>();
		String folderLocation = "D:/CrawlerData/WebData/ParseData";
		String readUrl = "D:/CrawlerData/urlList.txt";
		String TempLine1;
		String TempLine2;
		int count = 0;
		
		//String pageUrl = "";
		try {
		BufferedReader TextFile1 = new BufferedReader(new FileReader(readUrl));
		while((TempLine1 = TextFile1.readLine()) != null){
			String TokenBuffer = "";
			String hash = Integer.toString(TempLine1.hashCode());
			String fileLocation = folderLocation.concat(hash);
			BufferedReader TextFile = new BufferedReader(new FileReader(fileLocation));
				while((TempLine2 = TextFile.readLine()) != null){
					if (!TempLine2.isEmpty()){
						TempList = TokenizeFileWithStop(TempLine2);
						if (TokenBuffer==""){
							TokenFreqTable = ComputeTokenFrequencies(TempList, TokenFreqTable, stopWordsList);
						}
						else{							
							TempList.add(0, TokenBuffer);
							TokenFreqTable = ComputeTokenFrequencies(TempList, TokenFreqTable, stopWordsList);
						}
						if((TempList.size() - 1) >= 0){
						TokenBuffer = TempList.get(TempList.size() - 1);
						}
						TempList.clear();
						
					}
				}
				//System.out.println("File number is" + Integer.toString(count));
				TextFile.close();
				count += 1;
			}
		TextFile1.close();		
		}		
		catch (FileNotFoundException e) {
			System.out.println("File not found!!!");
		} catch (IOException e) {
			System.out.println("Error!!!");
		}
		
		System.out.println("Total number of files accessed :" + count);
		PrintToken(TokenFreqTable);
		Long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000 + " seconds");
	}		
		
		public static List<String> TokenizeFileWithStop(String TempLine){
			List<String> TempList = new ArrayList<String>();
			TempLine = TempLine.replaceAll("[^a-zA-Z0-9']", " ");
			StringTokenizer fileIn = new StringTokenizer(TempLine);				
			while (fileIn.hasMoreTokens()){
				String a = fileIn.nextToken();
				a = a.replaceAll("[']", "");
				if(a.length()>1){
				TempList.add(a);
				}
			}
			return TempList;			
		}

		private static void PrintToken(Hashtable<Token, Integer> table) {
			List<Map.Entry<Token, Integer>> obj = new ArrayList<Entry<Token, Integer>>(table.entrySet());
			Collections.sort(obj, new Comparator<Map.Entry<Token, Integer>>(){
				public int compare(Map.Entry<Token, Integer> entry1, Map.Entry<Token, Integer> entry2) {
		            Integer ent1 = entry1.getValue();
		            Integer ent2 = entry2.getValue();
					return ent2.compareTo(ent1);
		        }});
			int count=0;
			for (Map.Entry<Token, Integer> entry : obj){
				if(count<20){
				System.out.print(entry.getKey().token1 + "   " + entry.getKey().token2 + "  ");
				System.out.println(" --> " + entry.getValue());
				count++;
				}
				else{
					break;
				}
			}
			
		}
		
		// For tokens in list, compute 2Gram frequency and put in table.
		private static Hashtable<Token, Integer> ComputeTokenFrequencies(List<String> List, Hashtable<Token, Integer> table, Hashtable<String,Integer> stopWordsList) {
			int i = 0;
			while(i < List.size()-1){
				Token obj = new Token();
				if(stopWordsList.containsKey(List.get(i).toLowerCase())){
					i++;
					continue;
				}					
				obj.token1 = List.get(i).toLowerCase();
				if(stopWordsList.containsKey(List.get(i+1).toLowerCase())){
					i++;
					continue;
				}					
				obj.token2 = List.get(i+1).toLowerCase();
				if (table.containsKey(obj)){
					Integer value = (Integer)table.get(obj);
					value += 1;
					table.put(obj, value);
				}
				else{
					table.put(obj, 1);
				}
				i++;
			}
			return table;
		}
	}



