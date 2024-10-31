package com.maybank.interview.config;

import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public final class PrincipalHttpLogFormatter implements HttpLogFormatter {

    private final JsonHttpLogFormatter delegate;
    private final String service;

    public PrincipalHttpLogFormatter(final JsonHttpLogFormatter delegate, final String service) {
        this.delegate = delegate;
        this.service = service;
    }

    @Override
    public String format(Precorrelation precorrelation, HttpRequest request) throws IOException {
        final Map<String, Object> content = delegate.prepare(precorrelation, request);
        content.put("date", getFormattedDate());
        content.remove("remote");
        content.remove("origin");
        content.remove("protocol");
        content.put("type", "REQUEST");

        String clientInfo = "";
        if (request.getHeaders() != null && request.getHeaders().get("User-Agent") != null) {
            clientInfo = request.getHeaders().get("User-Agent").getFirst();
        }
        content.put("client-info", clientInfo);
        content.put("request-context", request.getRequestUri());
        content.put("service", service);
        return delegate.format(content);
    }

    @Override
    public String format(Correlation correlation, HttpResponse response) throws IOException {
        final Map<String, Object> content = delegate.prepare(correlation, response);
        content.remove("origin");
        content.put("type", "RESPONSE");
        return delegate.format(content);
    }

    private String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm:ss z");
        ZonedDateTime now = ZonedDateTime.now();
        return now.format(formatter);
    }
}