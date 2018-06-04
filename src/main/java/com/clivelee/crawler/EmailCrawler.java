package com.clivelee.crawler;

import com.clivelee.utils.TextFinder;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

final class EmailCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|html))$");

    private String domainName;
    private Set<String> emails;

    EmailCrawler(String domainName, Set<String> emails) {
        super();
        this.domainName = domainName.toLowerCase();
        this.emails = emails;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith(domainName);
    }

    @Override
    public void visit(Page page) {

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();

            Set<String> foundEmails = TextFinder.findEmails(text + '\n' + html);
            foundEmails.forEach((s) -> {
                if (!emails.contains(s)) {
                    System.out.println(s);
                    emails.add(s);
                }
            });
        }
    }
}
