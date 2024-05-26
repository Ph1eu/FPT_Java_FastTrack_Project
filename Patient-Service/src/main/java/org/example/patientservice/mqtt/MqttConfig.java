package org.example.patientservice.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker-url}")
    private String brokerUrl;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.qos}")
    private int qos;

    @Value("${mqtt.clean-session}")
    private boolean cleanSession;

    @Value("${mqtt.connection-timeout}")
    private int connectionTimeout;

    @Value("${mqtt.keep-alive-interval}")
    private int keepAliveInterval;

    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(cleanSession);
            options.setConnectionTimeout(connectionTimeout);
            options.setKeepAliveInterval(keepAliveInterval);
            client.connect(options);
            return client;
        } catch (MqttException e) {
            throw new RuntimeException("Failed to connect to MQTT broker", e);
        }
    }
}
