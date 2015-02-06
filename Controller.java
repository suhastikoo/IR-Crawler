package assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.frontier.Frontier;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	static int count = 0;
   
	
	
	public static void main(String[] args) throws Exception {
    	double start = System.currentTimeMillis();
            String crawlStorageFolder = "CrawlerData/";
            int numberOfCrawlers = 7;            
            //Frontier obj = new Frontier(null, null, null);
            BasicConfigurator.configure();
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            
            // To connect to mongodb server
            //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // Now connect to your databases
            //DB db = mongoClient.getDB( "test" );
    		//System.out.println("Connect to database successfully");
            
    		
    		config.setPolitenessDelay(300);
    		
            config.setMaxDepthOfCrawling(-1);

            /*
             * You can set the maximum number of pages to crawl. The default value
             * is -1 for unlimited number of pages
             */
            config.setMaxPagesToFetch(-1);

            /*
             * Do you want crawler4j to crawl also binary data ?
             * example: the contents of pdf, or the metadata of images etc
             */
            config.setIncludeBinaryContentInCrawling(false);

            /*
             * Do you need to set a proxy? If so, you can use:
             * config.setProxyHost("proxyserver.example.com");
             * config.setProxyPort(8080);
             *
             * If your proxy also needs authentication:
             * config.setProxyUsername(username); config.getProxyPassword(password);
             */

            /*
             * This config parameter can be used to set your crawl to be resumable
             * (meaning that you can resume the crawl from a previously
             * interrupted/crashed crawl). Note: if you enable resuming feature and
             * want to start a fresh crawl, you need to delete the contents of
             * rootFolder manually.
             */
            config.setResumableCrawling(false);
            
            /* Naming the user agent*/
            String userAgentString = "UCI WebCrawler 22363556/24449837/27481720";
            config.setUserAgentString(userAgentString);


            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            //controller.addSeed("http://www.ics.uci.edu/~welling/");
            //controller.addSeed("http://www.ics.uci.edu/~lopes/");
            controller.addSeed("http://www.ics.uci.edu/");
            //controller.addSeed("http://www.kraftscafe.com/");
            //controller.addSeed("http://www.profcmn.com/");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);  
            //obj.
            //MyCrawler obj = new MyCrawler();
            //obj.table
            System.out.println("Number of unique URL: " + MyCrawler.urlFreq.size());
            System.out.println("Number of Sub Domains: " + (MyCrawler.subDomainFreq.size()));
            System.out.println(MyCrawler.subDomainFreq);
            
            String file = "D:/CrawlerData/urlList";
    		File urls = new File(file);
    		if (!urls.exists()){
    			urls.createNewFile();
    		}
    		FileWriter fw1 = new FileWriter(urls,true);
    		BufferedWriter write1 = new BufferedWriter(fw1);
    		for (Map.Entry<String, Integer> a : MyCrawler.urlFreq.entrySet()){
    			write1.write(a.getKey());
    			write1.newLine();
    		}
    		
    		
    		write1.close();
    			//urlList.add(url);
    		 
    	    
            
            
            String file1 = "D:/CrawlerData/Subdomains";
    	    File urls1 = new File(file1);
    	    if (!urls1.exists()){
    				urls1.createNewFile();    			
    		}
    	    
    	    FileWriter fw2 = new FileWriter(urls1,true);
			BufferedWriter write2 = new BufferedWriter(fw2);
			
			Set<Entry<String, Integer>> set = MyCrawler.subDomainFreq.entrySet();
			Iterator<Entry<String, Integer>> itr = set.iterator();
			while (itr.hasNext()){
				Map.Entry<String, Integer> map = (Map.Entry<String, Integer>)itr.next();
				write2.write(map.getKey() + " , " + map.getValue());
				write2.newLine();
			}
//			for (Entry<String, Integer> a : MyCrawler.subDomainFreq.entrySet()){
//				write2.write(a.getKey() + " , " + a.getValue());
//				write2.newLine();
//				
//			}   
			write2.close();
            double end = System.currentTimeMillis();
            double ans = ((end-start)/1000)/60;
            System.out.println("Time Consumed: " + ans + " minutes");
            
//            DBCursor cursor = MyCrawler.coll.find();
//            
//            try {
//               while(cursor.hasNext()) {
//                   System.out.println(cursor.next());
//               }
//            } finally {
//               cursor.close();
//            }
            //MongoDBClass lencheck = new MongoDBClass();
            //System.out.println(MongoDBClass.Maxlen);
            //System.out.println(MongoDBClass.urlmax);

    }
}
	