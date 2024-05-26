package org.example.reportservice.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttPublisherService.class);

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private MqttConfig mqttConfig;

    public MqttPublisherService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
        this.mqttClient.setCallback(this);
    }

    public void publish(String topic, String payload) {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(mqttConfig.getQos());
        try {
            mqttClient.publish(topic, message);
            logger.info("Publish message:  "+message+" to topic " +topic);
        } catch (MqttException e) {
            logger.error("Error publishing MQTT message", e);
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
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("Delivery complete. Token: " + token.toString());
    }
}
