package com.std.mq.D2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhaojy
 * @create-time 2018-03-22
 */
public class Producer {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;

    private Destination destination;
    private MessageProducer producer;

    /**
     * 初始化链接
     */
    public Producer() {
        this.connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.199.248:61616"
        );

        try {
            this.connection = this.connectionFactory.createConnection();
            this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            this.destination = this.session.createQueue("first");
            this.producer = this.session.createProducer(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return this.session;
    }

    public void send_1() {
        try {
            Destination destination = this.session.createQueue("first");    // 发送位置

            MapMessage mapMessage_1 = this.session.createMapMessage();
            mapMessage_1.setString("name", "ZhangSan");
            mapMessage_1.setString("age", "23");
            mapMessage_1.setStringProperty("color", "blue");
            mapMessage_1.setIntProperty("sal", 2000);

            MapMessage mapMessage_2 = this.session.createMapMessage();
            mapMessage_2.setString("name", "Lisi");
            mapMessage_2.setString("age", "21");
            mapMessage_2.setStringProperty("color", "red"); // 条件，必须是property？
            mapMessage_2.setIntProperty("sal", 3230);

            MapMessage mapMessage_3 = this.session.createMapMessage();
            mapMessage_3.setString("name", "wangwu");
            mapMessage_3.setString("age", "31");
            mapMessage_3.setStringProperty("color", "green");
            mapMessage_3.setIntProperty("sal", 130);

            MapMessage mapMessage_4 = this.session.createMapMessage();
            mapMessage_4.setString("name", "zhaoliu");
            mapMessage_4.setString("age", "38");
            mapMessage_4.setStringProperty("color", "red");
            mapMessage_4.setIntProperty("sal", 2430);

            this.producer.send(destination, mapMessage_1, DeliveryMode.NON_PERSISTENT, 2, 1000 * 60 * 10L);
            this.producer.send(destination, mapMessage_2, DeliveryMode.NON_PERSISTENT, 3, 1000 * 60 * 10L);
            this.producer.send(destination, mapMessage_3, DeliveryMode.NON_PERSISTENT, 6, 1000 * 60 * 10L);
            this.producer.send(destination, mapMessage_4, DeliveryMode.NON_PERSISTENT, 9, 1000 * 60 * 10L);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send_1();
    }

}
