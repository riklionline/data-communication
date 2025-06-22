package mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MqttClientManager {
    private final MqttConfig config;
    private MqttClient mqttClient;
    private final ExecutorService messageProcessor;
    private final MqttMessageCallback messageCallback;

    public MqttClientManager(MqttConfig config, MqttMessageCallback messageCallback, int threadPoolSize) {
        this.config = config;
        this.messageCallback = messageCallback;
        this.messageProcessor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void connect() throws MqttException {
        try {
            // 设置持久化
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(config.getUsername());
            options.setPassword(config.getPassword().toCharArray());
            options.setCleanSession(config.isCleanSession());
            options.setConnectionTimeout(config.getConnectionTimeout());
            options.setKeepAliveInterval(config.getKeepAliveInterval());

            mqttClient = new MqttClient(config.getBrokerUrl(), config.getClientId(), new MemoryPersistence());
            mqttClient.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    log.info("MQTT连接成功: {}", serverURI);
                }

                @Override
                public void connectionLost(Throwable cause) {
                    log.error("MQTT连接丢失", cause);
                    // 这里可以添加重连逻辑
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    log.debug("收到消息 - Topic: {}, Qos: {}, Payload: {}",
                            topic, message.getQos(), new String(message.getPayload()));

                    // 使用线程池处理消息
                    messageProcessor.execute(() -> {
                        try {
                            messageCallback.handleMessage(topic, message);
                        } catch (Exception e) {
                            log.error("处理MQTT消息异常", e);
                        }
                    });
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.debug("消息投递完成: {}", token.getMessageId());
                }
            });

            mqttClient.connect(options);
            mqttClient.subscribe(config.getTopic(), config.getQos());
            log.info("已订阅主题: {}", config.getTopic());
        } catch (MqttException e) {
            log.error("MQTT连接失败", e);
            throw e;
        }
    }

    public void disconnect() throws MqttException {
        if (mqttClient != null && mqttClient.isConnected()) {
            mqttClient.disconnect();
            log.info("MQTT客户端已断开连接");
        }
        messageProcessor.shutdown();
    }

    public void publish(String topic, String payload, int qos, boolean retained) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        mqttClient.publish(topic, message);
    }
}
