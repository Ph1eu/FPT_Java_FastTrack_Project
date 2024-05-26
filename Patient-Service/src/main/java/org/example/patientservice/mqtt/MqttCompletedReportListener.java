package org.example.patientservice.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MqttCompletedReportListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MqttSubscriberService mqttSubscriberService;

    private static final Logger logger = LoggerFactory.getLogger(MqttCompletedReportListener.class);
    @Value("${completed-reports-topic}")
    private String topic;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        mqttSubscriberService.subscribe(topic);
    }
    /**
     * mock sending notifications of completed reports to patient
     */
    @EventListener
    public void handleMqttMessageEvent(MqttMessage message) {
        String patientId = message.toString().split(":")[0];
        String reportId = message.toString().split(":")[1];
        logger.info("Report Id: " + reportId+" of patient " + patientId+" has been completed. Sending notification");
    }
}
