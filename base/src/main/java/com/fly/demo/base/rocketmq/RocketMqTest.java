package com.fly.demo.base.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @Author: feilongyan
 * @Description:
 * @Date: 2023/1/31 10:32
 * @Version: 1.0
 */
public class RocketMqTest {

    final static DefaultMQProducer producer = new DefaultMQProducer();

    private final static String nameserver = "192.168.1.91:9876";

    private final static String topic = "UPLOAD_MQ_TOPIC";

    private final static String tag = "test";

    public static void main(String[] args) {
        try {
            testSendMsg();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void testSendMsg() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        producer.setNamesrvAddr(nameserver);
        producer.setProducerGroup("test_producer");
        try {
            producer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        Message message = new Message();
        message.setTopic(topic);
        message.setKeys("key888");
        message.setBody("嘎嘎乱杀".getBytes());
        message.setTags(tag);
        producer.send(message);
        Thread.sleep(1000);
        producer.shutdown();
    }
}
