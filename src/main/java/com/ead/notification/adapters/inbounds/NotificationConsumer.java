package com.ead.notification.adapters.inbounds;

import com.ead.notification.adapters.dtos.NotificationRecordCommandDto;
import com.ead.notification.core.domain.NotificationDomain;
import com.ead.notification.core.ports.NotificationServicePort;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    final NotificationServicePort notificationService;

    public NotificationConsumer(NotificationServicePort notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ead.broker.key.notificationCommandKey}")
    )
    public void listen(@Payload NotificationRecordCommandDto notificationRecordCommandDto) {
        var notificationDomain = new NotificationDomain();
        BeanUtils.copyProperties(notificationRecordCommandDto, notificationDomain);
        notificationService.saveNotification(notificationDomain);
    }
}
