package org.example.patientservice.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriberService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriberService.class);

    @Autowired
    private MqttClient mqttClient;

    @Value("${mqtt.qos}")
    private int qos;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public void subscribe(String topic) {
        try {
            mqttClient.subscribe(topic, qos);
            mqttClient.setCallback(this);
            logger.info("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            logger.error("Error subscribing to topic: " + topic, e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MQTT connection lost, attempting to reconnect", cause);
        while (!mqttClient.isConnected()) {
            try {
                mqttClient.reconnect();
            } catch (MqttException e) {
                logger.error("Reconnection failed, retrying in 5 seconds", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("Message arrived. Topic: " + topic + " Message: " + message.toString());
        applicationEventPublisher.publishEvent(message);
    }


        @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
