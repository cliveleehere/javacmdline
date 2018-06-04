package com.clivelee.crawler;

import com.clivelee.crawler.EmailCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlController;

public final class EmailCrawlerFactory implements CrawlController.WebCrawlerFactory<EmailCrawler> {
    private String domainName;

    EmailCrawlerFactory(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public EmailCrawler newInstance() throws Exception {
        return new EmailCrawler(domainName);
    }
}
