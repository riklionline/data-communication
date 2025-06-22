package mqtt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttClientApplication {
    public static void main(String[] args) {
        try {
            // 1. 创建配置
            MqttConfig config = new MqttConfig();

            // 2. 创建消息处理器
            MqttMessageCallback messageProcessor = new SampleMessageProcessor();

            // 3. 创建并启动MQTT客户端(线程池大小为10)
            MqttClientManager clientManager = new MqttClientManager(config, messageProcessor, 10);
            clientManager.connect();

            // 添加关闭钩子
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    clientManager.disconnect();
                } catch (Exception e) {
                    log.error("关闭MQTT客户端时出错", e);
                }
            }));

            // 保持程序运行
            Thread.currentThread().join();

        } catch (Exception e) {
            log.error("MQTT客户端异常终止", e);
        }
    }
}
