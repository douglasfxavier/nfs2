package classes;

import classes.Topic;

interface IEventListener {
    void registerSubscriber(Topic topic, Subscriber subscriber);
    void unregisterSubscriber(Topic topic, Subscriber subscriber);
    void notifySubscribers(Topic topic,String message);
}
