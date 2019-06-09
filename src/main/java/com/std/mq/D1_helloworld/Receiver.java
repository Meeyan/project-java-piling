package com.std.mq.D1_helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息接收者
 *
 * @author zhoajy
 * @date 2018/3/11 20:17
 */
public class Receiver {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.199.245:61616"
        );

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("first");

        // 消费端
        MessageConsumer consumer = session.createConsumer(destination);

        // 实际环境中千万不要这么写，很low的一种行为
        while (true) {
            TextMessage receive = (TextMessage) consumer.receive();
            String text = receive.getText();
            System.out.println("消费数据：" + text);
        }
    }
}
