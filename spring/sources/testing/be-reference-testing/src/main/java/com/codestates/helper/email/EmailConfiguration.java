package com.codestates.helper.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EmailConfiguration {
    @Primary
    @Bean
    public EmailSendable MockEmailSendable() {
        return new MockEmailSendable();
    }

    @Bean
    public EmailSendable SimpleEmailSendable() {
        return new SimpleEmailSendable();
    }
}
