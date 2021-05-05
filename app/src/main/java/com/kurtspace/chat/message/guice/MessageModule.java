package com.kurtspace.chat.message.guice;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.google.inject.AbstractModule;
import com.kurtspace.chat.message.resource.ApiResource;

import org.gwizard.web.WebConfig;

public class MessageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ApiResource.class);
        bind(WebConfig.class).to(MyWebConfig.class);
        bind(JacksonConfig.class);
    }

    private static final class MyWebConfig extends WebConfig {
        @Override
        public int getPort() {
            return 8777;
        }
    }

    @Provider
    public static final class JacksonConfig implements ContextResolver<ObjectMapper> {
        private final ObjectMapper objectMapper;

        public JacksonConfig() throws Exception {

            objectMapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL).registerModule(new JSR310Module());

        }

        @Override
        public ObjectMapper getContext(Class<?> arg0) {
            return objectMapper;
        }
    }
}
