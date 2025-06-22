package mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttMessageCallback {
    void handleMessage(String topic, MqttMessage message) throws Exception;
}
