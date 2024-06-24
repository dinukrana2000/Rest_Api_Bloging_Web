package com.exmple.dinuk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*") // Allow CORS for all origins
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //This line enables a simple in-memory message broker for destinations prefixed with "/topic
        config.enableSimpleBroker("/topic");
        //This line sets the application destination prefix to "/app", meaning that any message sent to a destination prefixed with "/app" will be routed to a controller method
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("Registering Stomp endpoints...");
        //STOMP endpoint at "/web-socket" and allows all origins to access it
        registry.addEndpoint("/web-socket").setAllowedOrigins("*");
        logger.info("Stomp endpoints registered.");

    }

}

