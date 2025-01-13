package com.ead.notification.adapters.configs;

import com.ead.notification.NotificationApplication;
import com.ead.notification.core.ports.NotificationPersistencePort;
import com.ead.notification.core.ports.NotificationServicePort;
import com.ead.notification.core.services.NotificationServicePortImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationApplication.class)
public class BeanConfiguration {

    @Bean
    NotificationServicePort notificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        return new NotificationServicePortImpl(notificationPersistencePort);
    }

}
