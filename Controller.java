package assignment2;
//comment test from urjit
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;

import com.mongodb.DB;
import com.mongodb.MongoClient;

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
            String crawlStorageFolder = "D:/CrawlerData/";
            int numberOfCrawlers = 10;            
            //Frontier obj = new Frontier(null, null, null);
            BasicConfigurator.configure();
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            
            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // Now connect to your databases
            DB db = mongoClient.getDB( "test" );
    		System.out.println("Connect to database successfully");
            //boolean auth = db.authenticate(myUserName, myPassword);
    		 //System.out.println("Authentication: "+auth);
            
            /*
             * crawlStorageFolder is a folder where intermediate crawl data is
             * stored.
             */
            //String crawlStorageFolder = args[0];

            /*
             * numberOfCrawlers shows the number of concurrent threads that should
             * be initiated for crawling.
             */
            //int numberOfCrawlers = Integer.parseInt(args[1]);

            //CrawlConfig config = new CrawlConfig();

           // config.setCrawlStorageFolder(crawlStorageFolder);

            /*
             * Be polite: Make sure that we don't send more than 1 request per
             * second (1000 milliseconds between requests).
             */
            config.setPolitenessDelay(300);

            config.setMaxDepthOfCrawling(70);

            config.setMaxPagesToFetch(350);

            config.setIncludeBinaryContentInCrawling(false);

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

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);  
            //obj.
            //MyCrawler obj = new MyCrawler();
            //obj.table
            System.out.println("Number of unique URL: " + MyCrawler.urlList.size());
            System.out.println("Number of Sub Domains: " + MyCrawler.subDomainFreq.size());
            System.out.println(MyCrawler.subDomainFreq);
            
            String file = "D:/CrawlerData/urlList";
			File urls = new File(file);
			if (!urls.exists()){				
						urls.createNewFile();
			}
					FileWriter fw1 = new FileWriter(urls,true);
					BufferedWriter write1 = new BufferedWriter(fw1);
					
					for (String a : MyCrawler.urlList){
						write1.write(a);
						write1.newLine();
					}					
					write1.close();
			String file1 = "D:/CrawlerData/Subdomains";
			File urls1 = new File(file1);
		    if (!urls1.exists()){			
					    	//try {
								urls1.createNewFile();
							//} 
					    	//catch (IOException e) {
								//e.printStackTrace();
							//}
						}	
		    FileWriter fw2 = new FileWriter(urls1,true);
			BufferedWriter write2 = new BufferedWriter(fw2);
			
			for (String b : MyCrawler.subDomainList){
				write2.write(b);
				write2.newLine();
			}					
			write2.close();
            double end = System.currentTimeMillis();
            double ans = ((end-start)/1000)/60;
            System.out.println("Time Consumed: " + ans + " minutes");
    }
}
	