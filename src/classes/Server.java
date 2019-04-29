package classes;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serverSocket;
    private SubscribersByTopicManager subscribersByTopicManager;

    public Server(){
        try {
            this.serverSocket = new ServerSocket(7005);
            this.subscribersByTopicManager =
                    new SubscribersByTopicManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while(true){

            try {

                Socket clientSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                String role = dataInputStream.readUTF();
                Thread thread = null;

                if (role.equals("PUBLISHER")){
                    PublisherWorker publisherWorker;
                    publisherWorker = new PublisherWorker(clientSocket,
                            this.subscribersByTopicManager);
                    thread = new Thread(publisherWorker);
                    thread.start();
                }else if (role.equals("SUBSCRIBER")) {
                    SubscriberWorker subscriberWorker;
                    subscriberWorker = new SubscriberWorker(clientSocket,
                            this.subscribersByTopicManager);
                    thread = new Thread(subscriberWorker);
                    thread.start();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
