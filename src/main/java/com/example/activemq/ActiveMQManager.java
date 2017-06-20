package com.example.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.Connection;
import javax.jms.Session;

/**
 * @author yakamy
 *         2017-06-20 18:04
 */
public class ActiveMQManager {

    private static PooledConnectionFactory factory = null;

    public static void init(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        ActiveMQPrefetchPolicy activeMQPrefetchPolicy = new ActiveMQPrefetchPolicy();

        activeMQConnectionFactory.setPrefetchPolicy(activeMQPrefetchPolicy);

        activeMQConnectionFactory.setUserName("xcloud");
        activeMQConnectionFactory.setPassword("");
        activeMQConnectionFactory.setUseAsyncSend(true);
        activeMQConnectionFactory.setBrokerURL("tcp://127.0.0.1:61616");

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(5);
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(50);
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
        pooledConnectionFactory.setBlockIfSessionPoolIsFullTimeout(5000);
        factory = pooledConnectionFactory;
    }


    public static Session getSession(){
        try {
            Connection connection = factory.createConnection();
            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
