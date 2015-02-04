//package assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.frontier.Frontier;
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
		static List<String> urlList = new ArrayList<String>();
		static Hashtable<String,Integer> urlFreq = new Hashtable<String,Integer>();
		static Hashtable<String,Integer> urlFreqWeb = new Hashtable<String,Integer>();
		
		static Hashtable<String,List<String>> subDomainFreq = new Hashtable<String,List<String>>();
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
		 
		  //if(!urlFreq.containsKey(url) && temp!=url.getSubDomain().toLowerCase()){
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
	    int docid = page.getWebURL().getDocid();
	    String url = page.getWebURL().getURL();
	    String domain = page.getWebURL().getDomain();
	    String path = page.getWebURL().getPath();
	    String subDomain = page.getWebURL().getSubDomain();
	    String parentUrl = page.getWebURL().getParentUrl();
	    String anchor = page.getWebURL().getAnchor();
	    
	    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	    String text = htmlParseData.getText();
	    String html = htmlParseData.getHtml();
	      
	      
	    List<WebURL> links = htmlParseData.getOutgoingUrls();
	    
	    
	    String file1 = "CrawlerData/Subdomains";
	    File urls1 = new File(file1);
	    if (!urls1.exists()){			
	    	try {
				urls1.createNewFile();
			} 
	    	catch (IOException e) {
				e.printStackTrace();
			}
		}	    
		
	    List<String> list = new ArrayList<String>();
	   
	    if (urlFreq.containsKey(url)){	    	
	    	urlFreq.put(url, urlFreq.get(url)+1);
	    }
	    
	    else{
	    	subDomain = subDomain.substring(0, subDomain.length()-4);
	    	if (!subDomainFreq.containsKey(subDomain)){
	    	//	list.add(url);
		    	subDomainFreq.put(subDomain, list);

				try {
					FileWriter fw2 = new FileWriter(urls1,true);
					BufferedWriter write2 = new BufferedWriter(fw2);
					write2.write(subDomain);
					write2.newLine();
					write2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	else{
		    	list = subDomainFreq.get(subDomain);
		    	list.add(url);
		    	subDomainFreq.put(subDomain, list);
	    	}
	    try {
	    	urlFreq.put(url, 1);
	    	String code = Integer.toString(url.hashCode());
			String file = "CrawlerData/urlList";
			File urls = new File(file);
			if (!urls.exists()){
				urls.createNewFile();
			}
			FileWriter fw1 = new FileWriter(urls,true);
			BufferedWriter write1 = new BufferedWriter(fw1);
			write1.write(url);
			write1.newLine();
			write1.close();
			urlList.add(url);
		} 
	    catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	    catch (IOException e) {
			e.printStackTrace();
		}
	    //logger.debug("Docid: {}"+docid);
	    logger.info("URL: "+url);
	    //logger.debug("Domain: '{}'"+domain);
	    //logger.debug("Sub-domain: '{}'"+subDomain);
	    //logger.debug("Path: '{}'"+path);
	    //logger.debug("Parent page: {}"+parentUrl);
	    //logger.debug("Anchor text: {}"+anchor);
	    

	   // if (page.getParseData() instanceof HtmlParseData) {
	      
	      
	      //String fileDatapath = "CrawlerData/parseData";
	      //String code = Integer.toString(url.hashCode());
	      //fileDatapath = fileDatapath.concat(code);
	      //File filename = new File(fileDatapath);
	    /*   
		try {
			FileWriter fw = new FileWriter(filename,true);
			BufferedWriter write = new BufferedWriter(fw);
			//write.write(text);
			
			write.newLine();
			
		//=======================================================Counting number of words
		
			
			String normalisedtext = text.replaceAll("[^\\dA-Za-z]", " ");
			String[] tokens;
			tokens = normalisedtext.split("\\s");
			for(String tok: tokens){
				write.write(tok);	
				write.newLine();
			}
			
			write.newLine();
			write.write(Integer.toString(tokens.length));
			
		//======================================================
			
			write.close();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
	    String normalisedtext = text.replaceAll("[^\\dA-Za-z]", " ");
		String[] tokens;
		tokens = normalisedtext.split("\\s+");
		
	    // To connect to mongodb server
        MongoClient mongoClient;
		try {
			
			mongoClient = new MongoClient( "localhost" , 27017 );
	        DB db = mongoClient.getDB( "IR1" );
			DBCollection webColl = db.getCollection("Webpages");
			DBObject obj = new BasicDBObject();
			obj.put("url", url);
			obj.put("text", tokens);
			webColl.save(obj);
			
			/*BasicDBObject doc = new BasicDBObject("name", "MongoDB")
	        .append("type", "database")
	        .append("count", counter)
	        .append("info", new BasicDBObject(code, text));
			
						
			coll.insert(doc);
			
			DBObject myDoc = coll.findOne();
		*/

			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		
		

	      logger.debug("Text length: {}"+text.length());
	      //logger.debug("Html length: {}"+html.length());
	      //logger.debug("Number of outgoing links: {}"+links.size());
	    }
	    Header[] responseHeaders = page.getFetchResponseHeaders();
	    /*if (responseHeaders != null) {
	      logger.debug("Response headers:");
	      for (Header header : responseHeaders) {
	        logger.debug("\t{}: {}" + header.getName() + header.getValue());
	      }
	    }*/

	    logger.debug("=============");
	    //return;
	    //System.out.println(urlList);
	  }
	  
	  
	  //return true;
	}
