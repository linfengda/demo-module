package com.lfd.soa.demo.gateway.config;

/**
 * @author linfengda
 * @date 2021-09-03 01:20
 */

import feign.Logger.Level;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.Formatter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Date;
import java.util.Locale;

@Configuration
public class FeignRegisterConfiguration {
    public FeignRegisterConfiguration() {
    }

    @Bean
    public WebMvcRegistrations feignWebRegistrations() {
        return new WebMvcRegistrations() {
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new FeignRegisterConfiguration.FeignFilterRequestMappingHandlerMapping();
            }
        };
    }

    @Bean
    public FeignFormatterRegistrar feignFormatterRegistrar() {
        return (registry) -> {
            registry.addFormatter(new FeignRegisterConfiguration.DateFormatter());
        };
    }

    @Bean
    Level feignLoggerLevel() {
        return Level.FULL;
    }

    public static class DateFormatter implements Formatter<Date> {
        private static final int DEFAULT_VALUE = -1;
        private static final int TIMESTAMP_LENGTH = 13;

        public DateFormatter() {
        }

        public Date parse(String text, Locale locale) {
            if (StringUtils.isNotBlank(text) && text.length() == 13) {
                long timestamp = NumberUtils.toLong(text, -1L);
                if (timestamp != -1L) {
                    return new Date(timestamp);
                }
            }

            return null;
        }

        public String print(Date date, Locale locale) {
            return String.valueOf(date.getTime());
        }
    }

    private static class FeignFilterRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        private FeignFilterRequestMappingHandlerMapping() {
        }

        protected boolean isHandler(Class<?> beanType) {
            return super.isHandler(beanType) && AnnotationUtils.findAnnotation(beanType, FeignClient.class) == null;
        }
    }
}
