package com.clivelee.config;

public class UrlNotSpecifiedException extends RuntimeException {
    public UrlNotSpecifiedException() {
        super("Url not specified");
    }
}
