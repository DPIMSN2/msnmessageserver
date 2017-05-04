import com.rabbitmq.client.*;
import jms.JMSConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Kevin.
 */
public class Main {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv){
        JMSConsumer.getInstance();

        try{
            JMSConsumer.receiveMessages(QUEUE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
