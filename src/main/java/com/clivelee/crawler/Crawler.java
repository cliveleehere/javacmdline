package com.clivelee.crawler;

import com.clivelee.config.Configuration;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {

    private Configuration configuration;

    public Crawler(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    //todo: we could pass in how long to sleep & the number of threads
    public void crawl() throws Exception {
        String crawlStorageFolder = "./crawl/root";
        int numberOfCrawlers = 4;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setIncludeHttpsPages(true);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(configuration.domainName());
        EmailCrawlerFactory emailCrawlerFactory = new EmailCrawlerFactory(configuration.domainName());
        controller.startNonBlocking(emailCrawlerFactory, numberOfCrawlers);

        // Wait for 30 seconds
        Thread.sleep(30 * 1000);

        controller.shutdown();
        controller.waitUntilFinish();
    }
}
