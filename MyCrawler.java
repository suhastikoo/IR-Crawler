package assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

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
		static List<String> subDomainList = new ArrayList<String>();
		static Hashtable<String,Integer> urlFreq = new Hashtable<String,Integer>();
		static Hashtable<String,List<String>> subDomainFreq = new Hashtable<String,List<String>>();
		//static flag = 0;
		Controller objCont = new Controller();
		
		
	
	  @Override
	  public boolean shouldVisit(WebURL url) {
		  //String subDomain1 = url.getSubDomain().toLowerCase();
		  String temp = "calendar";
		  if(!urlFreq.containsKey(url)){// && !temp.toLowerCase().equals(subDomain1)){
		  String href = url.getURL().toLowerCase();
	    return !BINARY_FILES_EXTENSIONS.matcher(href).matches() && href.contains("ics.uci.edu/") 
	    		&& !href.contains(temp);
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
	    
//	    String file1 = "D:/CrawlerData/Subdomains";
//	    File urls1 = new File(file1);
//	    if (!urls1.exists()){			
//	    	try {
//				urls1.createNewFile();
//			} 
//	    	catch (IOException e) {
//				e.printStackTrace();
//			}
//		}	    
		
	    List<String> list = new ArrayList<String>();
	  /*  if (!subDomainFreq.containsKey(subDomain) && subDomain.length()>3){
	    	List<String> list = new ArrayList<String>();
	    	list.add(url);
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
	    	List<String> list = subDomainFreq.get(subDomain);
	    	list.add(url);
	    	subDomainFreq.put(subDomain, list);
	    }*/
	    if(subDomain.contains(".ics")){	    	
	   
	    if (urlFreq.containsKey(url)){	    	
	    	urlFreq.put(url, urlFreq.get(url)+1);
	    }
	    
	    else{
	    	subDomain = subDomain.substring(0, subDomain.length()-4);
	    	if (!subDomainFreq.containsKey(subDomain)){
	    		list.add(url);
		    	subDomainFreq.put(subDomain, list);
		    	subDomainList.add(subDomain);
//				try {
//					FileWriter fw2 = new FileWriter(urls1,true);
//					BufferedWriter write2 = new BufferedWriter(fw2);
//					write2.write(subDomain);
//					write2.newLine();
//					write2.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	    	}
	    	else{
		    	list = subDomainFreq.get(subDomain);
		    	list.add(url);
		    	subDomainFreq.put(subDomain, list);
	    	}
	  //  try {
	    	urlFreq.put(url, 1);
	    	//count += 1;
	
	    	
	    	//String content = new String(page.getContentData(),page.getContentCharset());
			//String filePath = "D:/CrawlerData/pageInfo";
			String code = Integer.toString(url.hashCode());
			//filePath = filePath.concat(code);
			//File filename = new File(filePath);
			
			//String file = "D:/CrawlerData/urlList";
			//File urls = new File(file);
			
		//	if (!filename.exists()){
//			if (!urls.exists()){
//		//		filename.createNewFile();
//				urls.createNewFile();
//			}
//			FileWriter fw1 = new FileWriter(urls,true);
//			BufferedWriter write1 = new BufferedWriter(fw1);
//			
//			write1.write(url);
//			write1.newLine();
//			write1.close();
			
			
			//FileWriter fw = new FileWriter(filename,true);
			//BufferedWriter write = new BufferedWriter(fw);
			urlList.add(url);
			
			//write.write(url);
			//write.newLine();
			//write.write(domain);
			//write.newLine();
			//write.write(path);
			//write.newLine();
			//write.write(subDomain);
			//write.newLine();		
			//write.close();
		//} 
//	    catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
//	    catch (IOException e) {
//			e.printStackTrace();
//		}
	    //logger.debug("Docid: {}"+docid);
	    logger.info("URL: "+url);
	    //logger.debug("Domain: '{}'"+domain);
	    //logger.debug("Sub-domain: '{}'"+subDomain);
	    //logger.debug("Path: '{}'"+path);
	    //logger.debug("Parent page: {}"+parentUrl);
	    //logger.debug("Anchor text: {}"+anchor);

	    if (page.getParseData() instanceof HtmlParseData) {
	      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	      String text = htmlParseData.getText();
	      String html = htmlParseData.getHtml();
	      List<WebURL> links = htmlParseData.getOutgoingUrls();
	      
	     // String fileDatapath = "D:/CrawlerData/parseData";
	      //String code = Integer.toString(url.hashCode());
	      //fileDatapath = fileDatapath.concat(code);
	      //File filename = new File(fileDatapath);
	       
		//try {
			//FileWriter fw = new FileWriter(filename,true);
			//BufferedWriter write = new BufferedWriter(fw);
			//write.write(text);
			//write.close();
			
		//} 
		//catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}

	      //logger.debug("Text length: {}"+text.length());
	      //logger.debug("Html length: {}"+html.length());
	      //logger.debug("Number of outgoing links: {}"+links.size());
	    }
	    //Header[] responseHeaders = page.getFetchResponseHeaders();
	    /*if (responseHeaders != null) {
	      logger.debug("Response headers:");
	      for (Header header : responseHeaders) {
	        logger.debug("\t{}: {}" + header.getName() + header.getValue());
	      }
	    }*/

	    //logger.debug("=============");
	    //return;
	    //System.out.println(urlList);
	    }
	    }
	    }
	  }

