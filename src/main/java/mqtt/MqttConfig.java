package mqtt;

import lombok.Data;

@Data
public class MqttConfig {
    private String brokerUrl = "tcp://192.168.0.200:1883";
    private String clientId = "mqtt-client-java";
    private String username = "emqx";
    private String password = "Root@135799999";
    private String topic = "neuron/mqtt/pressure";
    private int qos = 0;
    private boolean cleanSession = true;
    private int connectionTimeout = 10;
    private int keepAliveInterval = 60;
}

