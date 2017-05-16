package jms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Message;
import service.ClientListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin.
 */
public class JMSClient implements JMSMessageReceiver {
    private final static String SERVER_QUEUE = "chatServer";
    private List<ClientListener> listeners;

    public JMSClient() {
        JMSDispatcher.getInstance();
        JMSConsumer.getInstance();

        listeners = new ArrayList<ClientListener>();
        startListening();
    }

    public void sendMessage(Message message) {
        String jsonMessage = messageToJson(message);
        try {
            JMSDispatcher.publishMessage(message.getReceiver().getUsername(), jsonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(ClientListener clientListener) {
        this.listeners.add(clientListener);
    }

    public void messageReceived(String message) {
        Message receivedMessage = jsonToMessage(message);
        notifyListeners(receivedMessage);
    }

    private void notifyListeners(Message message) {
        for (ClientListener listener : listeners) {
            listener.handleMessageReceived(message);
        }
    }

    private void startListening() {
        try {
            JMSConsumer.addListener(this);
            JMSConsumer.receiveMessages(SERVER_QUEUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String messageToJson(Message message) {
        Gson jsonMessage = new GsonBuilder().create();
        return jsonMessage.toJson(message);
    }

    private static Message jsonToMessage(String json) {
        Gson jsonMessage = new GsonBuilder().create();
        return jsonMessage.fromJson(json, Message.class);
    }

}
