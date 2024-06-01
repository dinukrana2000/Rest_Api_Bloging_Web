package com.exmple.dinuk.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class WhatsAppConfig {
    @Value("${twilio.account.sid}")
    private String AccountSid;

    @Value("${twilio.auth.token}")
    private String AuthToken;

    @Value("${twilio.phone.number}")
    private String businessNumber;



}
