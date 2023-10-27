package com.threecolts.hiring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiringTestImpl implements HiringTest {
    @Override
    public int countUniqueUrls(List<String> urls) {
        return (int) urls.stream()
                .map(this::normalizeUrl)
                .distinct()
                .count();
    }

    @Override
    public Map<String, Integer> countUniqueUrlsPerTopLevelDomain(List<String> urls) {
        var result = new HashMap<String, Integer>();
        urls.forEach(url -> {
            var tld = extractTopLevelDomain(url);
            result.merge(tld, 1, Integer::sum);
        });
        return result;
    }

    private String extractTopLevelDomain(String url) {
        try {
            var uri = new URI(url);
            var host = uri.getHost();
            var segments = host.split("\\.");
            if (segments.length > 1) {
                return segments[segments.length - 2] + "." + segments[segments.length - 1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String normalizeUrl(String url) {
        try {
            var uri = new URI(url);

            var scheme = uri.getScheme();

            if (scheme == null) {
                throw new RuntimeException("URL scheme is required.");
            }

            var user = uri.getUserInfo();
            var host = uri.getHost();

            var port = normalizePort(scheme, uri.getPort());
            var path = normalizePath(uri.getPath());
            var query = normalizeQuery(uri.getQuery());
            var fragment = normalizeFragment(uri.getFragment());

            var result = new URI(scheme, user, host, port, path, query, fragment);

            return result.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int normalizePort(String scheme, int port) {
        switch (port) {
            case 80:
                if ("http".equals(scheme)) {
                    return -1;
                }
                break;

            case 443:
                if ("https".equals(scheme)) {
                    return -1;
                }
                break;
        }
        return port;
    }

    private static String normalizePath(String path) {
        var result = removeDuplicates(path, '/');
        if (result == null || result.isEmpty()) {
            return null;
        }
        int length = result.length();
        char value = result.charAt(length - 1);
        if (value == '/') {
            return result.substring(0, length - 1);
        }
        return result;
    }

    private static String normalizeQuery(String query) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        var parts = query.split("&");
        if (parts.length > 1) {
            Arrays.sort(parts);
            var builder = new StringBuilder();
            for (String part : parts) {
                if (part.isEmpty()) {
                    continue;
                }
                int length = builder.length();
                if (length > 0) {
                    builder.append("&");
                }
                builder.append(part);
            }
            return builder.toString();
        }
        return query;
    }

    private static String normalizeFragment(String fragment) {
        if (fragment == null || fragment.isEmpty()) {
            return null;
        }
        return fragment;
    }

    private static String removeDuplicates(String text, char character) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        var builder = new StringBuilder();
        var duplicatesCount = 0;
        var textLength = text.length();
        for (int i = 0; i < textLength; ++i) {
            char value = text.charAt(i);
            if (value == character) {
                duplicatesCount += 1;
                if (duplicatesCount > 1) {
                    continue;
                }
            } else {
                duplicatesCount = 0;
            }
            builder.append(value);
        }
        return builder.toString();
    }
}
