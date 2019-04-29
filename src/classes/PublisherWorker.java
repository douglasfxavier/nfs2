package classes;

import classes.SubscribersByTopicManager;
import classes.Topic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PublisherWorker implements  Runnable{
    private Socket publisherSocket;
    private SubscribersByTopicManager subscribersByTopicManager;

    public PublisherWorker(Socket publisherSocket,
                        SubscribersByTopicManager subscribersByTopicManager){
        this.publisherSocket= publisherSocket;
        this.subscribersByTopicManager = subscribersByTopicManager;

    }

    @Override
    public void run() {
        try {
            System.out.println(String
                        .format("Listening to the publisher at %s", publisherSocket));
            DataOutputStream dataOutputStream = new DataOutputStream(
                    publisherSocket.getOutputStream());

            DataInputStream dataInputStream = new DataInputStream(
                    publisherSocket.getInputStream());
            while (true) {
                dataOutputStream.writeUTF("------ SERVER: Waiting for the publisher to inform the topic ------");

                //Topic to publish on
                String topicIndex = dataInputStream.readUTF();
                //System.out.println("model.Topic index: " + topicIndex);
                int topicIndexInt = Integer.parseInt(topicIndex);
                Topic topic = Topic.values()[topicIndexInt];
                System.out.println(String.format("Publisher %s/%s chose the topic: %s",
                                                    publisherSocket.getInetAddress(),
                                                    publisherSocket.getPort(),
                                                    topic));
                //News to publish
                dataOutputStream.writeUTF("----- SERVER: Waiting for the publisher to inform the news -----");
                String news = dataInputStream.readUTF();
                System.out.println(String.format("Publisher %s/%s posted the news: %s",
                                                    publisherSocket.getInetAddress(),
                                                    publisherSocket.getPort(),
                                                    news));


                //Notfying all the subcribers of the topic
                this.subscribersByTopicManager
                        .notifySubscribers(topic, news);
                dataOutputStream.writeUTF(String
                        .format("------- All subscribers of %s were notified ------ ",topic));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
