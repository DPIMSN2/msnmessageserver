package jms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Kevin.
 */

public class MessageSenderGateway {
    private static MessageSenderGateway instance;
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Channel channel;

    private MessageSenderGateway() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MessageSenderGateway getInstance() {
        if (instance == null) {
            instance = new MessageSenderGateway();
        }
        return instance;
    }

    private static void declareQueue(String queueName) throws IOException {
        channel.queueDeclare(queueName, false, false, false, null);
    }

    public static void publishMessage(String queueName, String message) throws IOException {
        declareQueue(queueName);
        channel.basicPublish("", queueName, null, message.getBytes());
    }

    public static void closeDispatcher() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
