package com.maybank.interview.config;

import com.maybank.interview.util.ThreadLocalTraceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.zalando.logbook.CorrelationId;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.StreamHttpLogWriter;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class LoggingConfiguration {

    public static final String MESS_X_TRACE_ID = "correlation";
    private static final Logger LOGGER = LogManager.getLogger(LoggingConfiguration.class);
    @Value("${spring.application.name}")
    private String service;

    @Bean
    public CorrelationId configure() {
        return r -> {
            String currCorrelationId = Optional.ofNullable(r.getHeaders().getFirst(MESS_X_TRACE_ID))
                    .filter(StringUtils::hasText)
                    .orElseGet(() -> (String) ThreadLocalTraceUtils.getThreadAttribute(MESS_X_TRACE_ID));
            ThreadLocalTraceUtils.storeThreadAttribute(MESS_X_TRACE_ID, currCorrelationId);
            return Optional.ofNullable(currCorrelationId).filter(StringUtils::hasText).orElseGet(() -> {
                String correlationId = UUID.randomUUID().toString();
                LOGGER.debug("Generated correlation id : {} ", correlationId);
                ThreadLocalTraceUtils.storeThreadAttribute(MESS_X_TRACE_ID, correlationId);
                return correlationId;
            });
        };
    }

    @Bean
    public Sink customLogbookSink() {
        HttpLogFormatter formatter = new PrincipalHttpLogFormatter(new JsonHttpLogFormatter(), service);
        return new DefaultSink(formatter, new StreamHttpLogWriter());
    }
}
