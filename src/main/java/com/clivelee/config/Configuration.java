package com.clivelee.config;

public final class Configuration {
    private String domainName;
    public String domainName() { return domainName; }

    Configuration(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "Domain Name: " + domainName;
    }
}
