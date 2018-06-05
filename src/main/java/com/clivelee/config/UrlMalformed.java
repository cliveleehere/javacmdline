package com.clivelee.config;

class UrlMalformed extends RuntimeException {
    UrlMalformed(String url) {
        super("Url is wrong: " + url);
    }
}
