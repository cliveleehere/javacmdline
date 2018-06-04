package com.clivelee;

import com.clivelee.config.ArgumentProcessor;
import com.clivelee.config.Configuration;
import com.clivelee.crawler.MyCrawlerFactory;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Main {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new ArgumentProcessor().parseArguments(args);


        String crawlStorageFolder = "./crawl/root";
        int numberOfCrawlers = 4;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setIncludeHttpsPages(true);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

//        controller.addSeed("http://www.ics.uci.edu/");
        controller.addSeed("www.ics.uci.edu");

        controller.start(new MyCrawlerFactory(), numberOfCrawlers);
    }
}
