package com.skywaet.middlewarefactory.grpcserver.util;

public final class UrlToRegexpConversionUtils {
    private UrlToRegexpConversionUtils() {
    }

    private static final String PATH_PARAMETER_REGEXP = "{\\w+}";

    private static final String REPLACEMENT = "[A-Za-z0-9;\\-_.=,]+";

    public static String createRegexpForUrl(String url) {
        return url.replaceAll(PATH_PARAMETER_REGEXP, REPLACEMENT);
    }
}
