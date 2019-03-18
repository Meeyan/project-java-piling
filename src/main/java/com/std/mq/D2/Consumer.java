package com.std.mq.D2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhaojy
 * @date 2018-03-22
 */
public class Consumer {
    // 选择器，类似sql选择条件
    public final String SELECTOR_0 = "age > 25";
    public final String SELECTOR_1 = "color = 'red'";
    public final String SELECTOR_2 = "color = 'red' AND sal > 2000";
    public final String SELECTOR_3 = "receiver = 'A'";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private Destination destination;

    public Consumer() {
        // 链接工厂
        System.out.println("-------初始化consumer - 开始------------");
        this.connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.199.248:61616"
        );
        try {
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            this.destination = this.session.createQueue("first");

            this.consumer = this.session.createConsumer(this.destination, SELECTOR_2);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("-------初始化consumer - 完成------------");

    }

    public void receiver() {
        try {
            this.consumer.setMessageListener(new MyMsgListener());  // 等待消息主动推过来
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyMsgListener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage) {

                }
                if (message instanceof MapMessage) {
                    MapMessage msg = (MapMessage) message;
                    System.out.println(message.toString());
                    System.out.println(msg.getString("name"));
                    System.out.println(msg.getString("age"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receiver();
    }
}
