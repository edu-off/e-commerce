package br.com.ms.logistica.adapters.handler;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorDecoderConfig {

    @Bean
    public ErrorDecoder getErrorDecoder() {
        return new CustomizedErrorDecoder();
    }

}
