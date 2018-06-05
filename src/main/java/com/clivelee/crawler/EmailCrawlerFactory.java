package com.clivelee.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

import java.util.HashSet;
import java.util.Set;

final class EmailCrawlerFactory implements CrawlController.WebCrawlerFactory<EmailCrawler> {
    private String domainName;
    private Set<String> emails = new HashSet<>();

    EmailCrawlerFactory(String domainName) {
        this.domainName = domainName;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public EmailCrawler newInstance() throws Exception {
        return new EmailCrawler(domainName, emails);
    }
}
