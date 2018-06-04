package com.clivelee.config;

import org.apache.http.util.TextUtils;

import java.net.URI;

public final class ArgumentProcessor implements IArgumentProcessor {
    @Override
    public Configuration parseArguments(String[] args) {

        if (args == null || args.length != 1) {
            throw new UrlNotSpecifiedException();
        }

        String urlString = args[0];

        URI uri = URI.create(urlString);

        if (TextUtils.isEmpty(uri.getScheme())) {
            urlString = "http://" + urlString;
        }

        return new Configuration(urlString);
    }
}
