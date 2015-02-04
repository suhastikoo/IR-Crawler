package assignment2;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("C:/Users/suhas/Desktop");
        System.out.println(crawlConfig.toString());
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try {
                CrawlController crawlController = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
                System.out.println(crawlController);
        }
        catch(Exception e){
        	System.out.println("Error!!!");
        }

	}

}


