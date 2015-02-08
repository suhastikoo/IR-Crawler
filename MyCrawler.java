package assignment2;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	private final static Pattern BINARY_FILES_EXTENSIONS =
	        Pattern.compile(".*\\.(bmp|gif|jpe?g|png|tiff?|pdf|ico|xaml|pict|rif|pptx?|ps" +
	        "|mid|mp2|mp3|mp4|wav|wma|au|aiff|flac|ogg|3gp|aac|amr|au|vox" +
	        "|avi|mov|mpe?g|ra?m|m4v|smil|wm?v|swf|aaf|asf|flv|mkv" +
	        "|zip|rar|gz|7z|aac|ace|alz|apk|arc|arj|dmg|jar|lzip|lha)" +
	        "(\\?.*)?$"); // For url Query parts ( URL?q=... )

	  /**
	   * You should implement this function to specify whether the given url
	   * should be crawled or not (based on your crawling logic).
	   */
		//static List<String> urlList = new ArrayList<String>();
		static Hashtable<String,Integer> urlFreq = new Hashtable<String,Integer>();
		static Hashtable<String,Integer> urlFreqWeb = new Hashtable<String,Integer>();
		
		static TreeMap<String,Integer> subDomainFreq = new TreeMap<String,Integer>();
		static int counter = 0;
		Controller objCont = new Controller();
		
		
		
	
	  @Override
	  public boolean shouldVisit(WebURL url) {
		 String currenturl = url.toString();
		 int quespos = currenturl.indexOf("?");
		 if(quespos<0){
			 quespos = currenturl.indexOf("#");
		 }
		 if(quespos>=0){
			 currenturl = currenturl.substring(0,quespos);
		 }
		 if(!urlFreqWeb.containsKey(currenturl)){
			 urlFreqWeb.put(currenturl, 1);
			 
			 String href = url.getURL().toLowerCase();
			 return !BINARY_FILES_EXTENSIONS.matcher(href).matches() && href.contains(".ics.uci.edu");
		 }
		  else{			  
			  return false;
		  }
	  }	 

	  /**
	   * This function is called when a page is fetched and ready to be processed
	   * by your program.
	   */
	  @Override
	  public void visit(Page page) {
	    //int docid = page.getWebURL().getDocid();
	    String url = page.getWebURL().getURL();
	    //String domain = page.getWebURL().getDomain();
	    //String path = page.getWebURL().getPath();
	    String subDomain = page.getWebURL().getSubDomain();
	    //String parentUrl = page.getWebURL().getParentUrl();
	    //String anchor = page.getWebURL().getAnchor();
	    
	    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	    String text = htmlParseData.getText();
	    //String html = htmlParseData.getHtml();
	      	    
	    String fileDatapath = "D:/CrawlerData/WebData/ParseData";
	    String code = Integer.toString(url.hashCode());
	    fileDatapath = fileDatapath.concat(code);
	    File filename = new File(fileDatapath);   
		try {
			FileWriter fw = new FileWriter(filename,true);
			BufferedWriter write = new BufferedWriter(fw);
			write.write(text);
			
			write.newLine();
			write.close();
		}		
	    	catch (IOException e) {
				System.out.println("Error!!!");
			} 
	   
	    if (urlFreq.containsKey(url)){	    	
	    	urlFreq.put(url, urlFreq.get(url)+1);
	    }	    
	    else{
	    	subDomain = subDomain.substring(0, subDomain.length()-4);
	    	if (!subDomainFreq.containsKey(subDomain)){
	    	   	subDomainFreq.put(subDomain, 1);
	    	}
	    	else{
		    	subDomainFreq.put(subDomain, subDomainFreq.get(subDomain)+1);
	    	}

	    	urlFreq.put(url, 1);
	    }
	  }
	}
