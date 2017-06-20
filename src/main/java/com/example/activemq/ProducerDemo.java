package com.example.activemq;

import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author yakamy
 *         2017-06-20 17:04
 */
public class ProducerDemo {
    public static void main(String[] args) {
        try {
            ActiveMQManager.init();
            Session session = ActiveMQManager.getSession();
            MessageProducer producer = session.createProducer(new ActiveMQTopic("cmf." + "test"));
            TextMessage text = session.createTextMessage("producer message");
            producer.send(text);
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
