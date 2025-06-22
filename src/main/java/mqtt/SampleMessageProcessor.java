package mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class SampleMessageProcessor implements MqttMessageCallback {
    @Override
    public void handleMessage(String topic, MqttMessage message) {
        // 这里可以实现你的业务逻辑
        String payload = new String(message.getPayload());
        log.info("处理消息 - Topic: {}, 内容: {}", topic, payload);

        // 模拟耗时操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
