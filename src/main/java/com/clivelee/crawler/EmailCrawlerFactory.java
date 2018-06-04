package com.clivelee.crawler;

import com.clivelee.crawler.EmailCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlController;

import java.util.HashSet;
import java.util.Set;

final class EmailCrawlerFactory implements CrawlController.WebCrawlerFactory<EmailCrawler> {
    private String domainName;
    private Set<String> emails = new HashSet<>();

    EmailCrawlerFactory(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public EmailCrawler newInstance() throws Exception {
        return new EmailCrawler(domainName, emails);
    }
}
