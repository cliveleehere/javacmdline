package com.clivelee.config;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;


public final class ArgumentProcessor {

    private static String urlRegex = "^(http(s)?://)?(([a-z0-9]+)[.])+[a-z0-9]+$";
    private static Pattern p = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);

    public Configuration parseArguments(String[] args) {

        if (args == null || args.length != 1) {
            throw new UrlNotSpecifiedException();
        }

        String urlString = args[0];

        if (!isValidDomain(urlString)) {
            throw new UrlMalformed(urlString);
        }
        return new Configuration(urlString);
    }

    @VisibleForTesting
    static boolean isValidDomain(@Nonnull String urlString) {
        return p.matcher(urlString).find();
    }
}
