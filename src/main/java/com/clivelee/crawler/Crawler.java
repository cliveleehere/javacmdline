package com.clivelee.crawler;

import com.clivelee.config.Configuration;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.util.TextUtils;

public class Crawler {

    private static int numberOfCrawlers = 4;
    private static int numberOfPages = 100;

    private String url;
    private String domainName;
    private CrawlConfig crawlConfig;
    private PageFetcher pageFetcher;

    private Crawler(String url, CrawlConfig crawlConfig, PageFetcher pageFetcher) {
        this.url = url;
        this.crawlConfig = crawlConfig;
        this.pageFetcher = pageFetcher;

        this.domainName = url.toLowerCase();
        if (domainName.startsWith("https://")) domainName = domainName.substring(8);
        else if (domainName.startsWith("http://")) domainName = domainName.substring(7);
    }

    public static Crawler create(Configuration configuration) throws InvalidConfigurationException {
        String crawlStorageFolder = "./crawl/root";

        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder(crawlStorageFolder);
        crawlConfig.setIncludeHttpsPages(true);
        crawlConfig.setMaxPagesToFetch(numberOfPages);
        crawlConfig.setConnectionTimeout(1000);

        PageFetcher pageFetcher = new PageFetcher(crawlConfig);

        String urlString = configuration.domainName();

        //Since the library expects a fully URL with a scheme, try to connect to the website in different ways:
        // 1) the way it's specified,
        // 2) with 'https://' prefixed, then
        // 3) with 'http://' prefixed.
        UrlFetchableChain startingChain = new UrlFetchableChain(pageFetcher, urlString);
        UrlFetchableChain httpsChain = new UrlFetchableChain(pageFetcher, "https://" + urlString);
        UrlFetchableChain httpChain = new UrlFetchableChain(pageFetcher, "http://" + urlString);

        startingChain.setNext(httpsChain).setNext(httpChain);
        urlString = startingChain.start();
        if (TextUtils.isEmpty(urlString)) {
            pageFetcher.shutDown();
            throw new InvalidConfigurationException();
        }

        return new Crawler(urlString, crawlConfig, new PageFetcher(crawlConfig));
    }

    public void crawl() throws Exception {
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);

        controller.addSeed(url);
        EmailCrawlerFactory emailCrawlerFactory = new EmailCrawlerFactory(domainName);

        controller.start(emailCrawlerFactory, numberOfCrawlers);
    }
}
