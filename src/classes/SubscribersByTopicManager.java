package classes;

import classes.IEventListener;
import classes.Topic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubscribersByTopicManager implements IEventListener {
    Map<Topic, ArrayList<Subscriber>> subscribersManager;

    public SubscribersByTopicManager(){
        subscribersManager = new HashMap<Topic,ArrayList<Subscriber>>();
        for (Topic topic: Topic.values()){
            ArrayList subscribersList = new ArrayList();
            subscribersManager.put(topic,subscribersList);
        }
    }

    @Override
    public void registerSubscriber(Topic topic, Subscriber subscriber) {
        ArrayList subscribersOnTopic = subscribersManager.get(topic);
        subscribersOnTopic.add(subscriber);
    }

    @Override
    public void unregisterSubscriber(Topic topic, Subscriber subscriber) {
        ArrayList subscribersOnTopic = subscribersManager.get(topic);
        subscribersOnTopic.remove(subscriber);
    }


    @Override
    public synchronized void notifySubscribers(Topic topic,String news) {
        ArrayList<Subscriber> subscribers = subscribersManager.get(topic);
        for (Subscriber subscriber : subscribers){
            try {
                DataOutputStream dataOutputStream =
                        new DataOutputStream(subscriber.getSocket()
                                .getOutputStream());
                dataOutputStream.writeUTF(news);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
