package com.std.mq.D1_helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * activeMQ-helloWorld - 消息生产者
 *
 * @author zhaojy
 * @create-time: 2018-03-11
 */
public class Sender {
    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1. 建立Connection工厂，端口在activemq.xml指定
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.199.245:61616");

        // 2. 创建一个连接，调用start方法开启连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start(); // 开启连接

        // 3. 创建session会话（上下文环境对象），用于接收消息。第一个参数：是否启用事务，第二个：签收模式，一般为自动签收
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        // 4. 通过session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在ptp模式中
        // Destination被称queue，即队列
        Destination destination = session.createQueue("first");

        // 5. 通过session对象创建消息的发送和接收对象（生产者和消费者）MessageProducer/MessageConsumer
        MessageProducer producer = session.createProducer(null);

        // 6. 我们可以使用MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性（DeliveryMode）
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // 7. 使用jms规范的TextMessage形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据，同理客户使用receive方法接收
        for (int i = 0; i < 100; i++) {
            TextMessage msg = session.createTextMessage("{\"address\":\"BeiJing\",\"name\":\"zhangsan-" + i + "\",\"sex\":\"femal\"}");
            producer.send(destination, msg);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        if (connection != null)
            connection.close();

    }
}
