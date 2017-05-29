package service;

import dao.MessageDAO;
import domain.Message;
import jms.MessageServerApplicationGateway;

/**
 * Created by Kevin.
 */
public class ServerService implements ClientListener {
    MessageServerApplicationGateway messageServer;
    MessageDAO messageDAO;

    public ServerService() {
        messageServer = new MessageServerApplicationGateway();
        messageServer.addListener(this);
    }

    private void sendMessage(Message message) {
        //todo Mogelijkheid om te checken of de receiver de gebruiker geblocked heeft waardoor het bericht niet verzonden moet worden
        messageServer.sendMessage(message);
        System.out.println("handled message:" + message.getMessageText() + " Sender:" + message.getSender() + " Receiver:" + message.getReceiver());
    }

    private void saveMessage(Message message) {
        //todo het persisten van de data naar de database
    }

    @Override
    public void handleMessageReceived(Message message) {
        sendMessage(message);
    }
}
