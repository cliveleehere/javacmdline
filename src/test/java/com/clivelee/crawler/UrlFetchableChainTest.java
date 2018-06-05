package com.clivelee.crawler;

import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static com.google.common.truth.Truth.assertThat;

public class UrlFetchableChainTest {

    PageFetcher mockPageFetcher = Mockito.mock(PageFetcher.class);
    PageFetchResult mockPageFetchResult = Mockito.mock(PageFetchResult.class);

    String fakeUrlString = "fake.url";

    @Test
    public void start_ok() throws Exception {
        when(mockPageFetcher.fetchPage(any())).thenReturn(mockPageFetchResult);
        when(mockPageFetchResult.getStatusCode()).thenReturn(200);

        String returnString = new UrlFetchableChain(mockPageFetcher, fakeUrlString).start();

        assertThat(returnString).isEqualTo(fakeUrlString);
    }

    @Test
    public void start_fail() throws Exception {
        when(mockPageFetcher.fetchPage(any())).thenReturn(mockPageFetchResult);
        when(mockPageFetchResult.getStatusCode()).thenReturn(400);

        String returnString = new UrlFetchableChain(mockPageFetcher, fakeUrlString).start();

        assertThat(returnString).isNull();
    }

    @Test
    public void start_chainedOk() throws Exception {
        when(mockPageFetcher.fetchPage(any())).thenReturn(mockPageFetchResult);
        when(mockPageFetchResult.getStatusCode()).thenReturn(400).thenReturn(200);
        UrlFetchableChain firstChain = new UrlFetchableChain(mockPageFetcher, "first.string");
        UrlFetchableChain secondChain = new UrlFetchableChain(mockPageFetcher, "second.string");
        firstChain.setNext(secondChain);

        String returnString = firstChain.start();

        assertThat(returnString).isEqualTo("second.string");
    }

    @Test
    public void start_chainedFail() throws Exception {
        when(mockPageFetcher.fetchPage(any())).thenReturn(mockPageFetchResult);
        when(mockPageFetchResult.getStatusCode()).thenReturn(400).thenReturn(400);
        UrlFetchableChain firstChain = new UrlFetchableChain(mockPageFetcher, "first.string");
        UrlFetchableChain secondChain = new UrlFetchableChain(mockPageFetcher, "second.string");
        firstChain.setNext(secondChain);

        String returnString = firstChain.start();

        assertThat(returnString).isNull();
    }
}