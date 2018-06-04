package com.clivelee;

import com.clivelee.config.ArgumentProcessor;
import com.clivelee.config.Configuration;
import com.clivelee.crawler.Crawler;

public class Main {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new ArgumentProcessor().parseArguments(args);

        new Crawler(configuration).crawl();
    }
}
