package com.exmple.dinuk.service;
import com.exmple.dinuk.config.WhatsAppConfig;
import com.exmple.dinuk.dto.WhatappDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {
    private final WhatsAppConfig config;
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);


    @Autowired
    public WhatsAppService(WhatsAppConfig config) {
        this.config = config;
        Twilio.init(config.getAccountSid(), config.getAuthToken());
    }

    public WhatappDTO sendWhatsAppMessage(WhatappDTO whatappDTO) {
        String recipient = whatappDTO.getRecipient();
        String message = whatappDTO.getMessage();
        try {
            Twilio.init(config.getAccountSid(), config.getAuthToken());

            Message.creator(
                            new PhoneNumber("whatsapp:" + recipient),
                            new PhoneNumber("whatsapp:" + config.getBusinessNumber()),
                            message)
                    .create();

            logger.info("WhatsApp message sent to: {}", recipient);

        } catch (Exception e) {
            logger.error("Error sending WhatsApp message: ", e);
        }
        return whatappDTO;
    }

}
