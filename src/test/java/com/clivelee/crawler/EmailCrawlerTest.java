package com.clivelee.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.google.common.truth.Truth.*;
import static org.mockito.Mockito.when;

public class EmailCrawlerTest {

    Page mockPage = Mockito.mock(Page.class);
    WebURL mockUrl = Mockito.mock(WebURL.class);

    @Before
    public void setUp() {
        when(mockUrl.getSubDomain()).thenReturn("web");
        when(mockUrl.getDomain()).thenReturn("mit.edu");
        when(mockUrl.getURL()).thenReturn("http://web.mit.edu/index.html");
    }

    @Test
    public void shouldVisit_ok() {
        EmailCrawler crawler = new EmailCrawler("web.mit.edu", null);

        boolean shouldVisitResult = crawler.shouldVisit(mockPage, mockUrl);

        assertThat(shouldVisitResult).isTrue();
    }

    @Test
    public void shouldVisit_fail() {
        EmailCrawler crawler = new EmailCrawler("www.mit.edu", null);

        boolean shouldVisitResult = crawler.shouldVisit(mockPage, mockUrl);

        assertThat(shouldVisitResult).isFalse();
    }
}