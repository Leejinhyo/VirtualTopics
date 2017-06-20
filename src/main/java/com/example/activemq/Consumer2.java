package com.example.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @author yakamy
 *         2017-06-20 17:04
 */
public class Consumer2 implements Runnable{
    public static void main(String[] args) {
        Consumer2 consumer2 = new Consumer2();
        consumer2.run();
    }

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = new ActiveMQQueue("Consumer.A.VirtualTopic.dmf.*");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(message -> {
                try {
                    String destination = message.getJMSDestination().toString();
                    System.out.println("destination >>>>>>>>>>>>>>>>>>" + destination);

                    String content = new String(((ActiveMQBytesMessage) message).getMessage().getContent().getData(), "UTF-8");
                    System.out.println("revieve message >>>>>>>>>>>>>>>>>>" + content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
