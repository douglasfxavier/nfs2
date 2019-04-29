package classes;

import classes.Subscriber;
import classes.SubscribersByTopicManager;
import classes.Topic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SubscriberWorker implements Runnable {
    private Socket subscriberSocket;
    private SubscribersByTopicManager subscribersByTopicManager;

    public SubscriberWorker(Socket clientSocket,
                            SubscribersByTopicManager subscribersByTopicManager){
        this.subscriberSocket = clientSocket;
        this.subscribersByTopicManager = subscribersByTopicManager;

    }

    @Override
    public void run() {
        try {
            System.out.println(String
                    .format("Listening to the subscriber at %s", subscriberSocket));
            DataOutputStream dataOutputStream = new DataOutputStream(
                    subscriberSocket.getOutputStream());

            DataInputStream dataInputStream = new DataInputStream(
                    subscriberSocket.getInputStream());
            while (true) {
                //dataOutputStream.writeUTF("----- SERVER: Waiting for the subscriber to inform the topic -----");

                //Topic to subscribe to
                String topicIndex = dataInputStream.readUTF();
                int topicIndexInt = Integer.parseInt(topicIndex);
                Topic topic = Topic.values()[topicIndexInt];

                //Subscribing the client
                Subscriber subscriber = new Subscriber(
                        String.format("%s", subscriberSocket
                                .getInetAddress()), subscriberSocket);
                this.subscribersByTopicManager.registerSubscriber(topic, subscriber);

                System.out.println(String.format("Client %s/%s subcribed to the topic: %s",
                        subscriberSocket.getInetAddress(),
                        subscriberSocket.getPort(),
                        topic));

                dataOutputStream.writeUTF(String.format("SERVER: Subscribed to the topic %s",topic));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
