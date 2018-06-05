package com.clivelee.crawler;


import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Chain of Responsibility pattern
 */
class UrlFetchableChain {
    private PageFetcher pageFetcher;
    private UrlFetchableChain next;
    private String urlString;

    UrlFetchableChain(PageFetcher pageFetcher, String urlString) {
        this.pageFetcher = pageFetcher;
        this.urlString = urlString;
    }

    final UrlFetchableChain setNext(UrlFetchableChain next) {
        this.next = next;
        return next;
    }

    final String start() {
        if (isUrlFetchable()) return urlString;
        else if (next == null) return null;
        return next.start();
    }

    private boolean isUrlFetchable() {
        WebURL webURL = new WebURL();
        webURL.setURL(urlString);
        try {
            return pageFetcher.fetchPage(webURL).getStatusCode() < 400;
        } catch (Exception e) {
            return false;
        }
    }
}
