package com.clivelee;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public final class MyCrawlerFactory implements CrawlController.WebCrawlerFactory<MyCrawler> {
    @Override
    public MyCrawler newInstance() throws Exception {
        return new MyCrawler("http://www.ics.uci.edu/");
    }
}
