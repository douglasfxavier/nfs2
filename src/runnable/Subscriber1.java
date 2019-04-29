package runnable;

import classes.Topic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Subscriber1 {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("127.0.0.1",7005);

            System.out.println(clientSocket);
            DataOutputStream dataOutputStream =
                    new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream =
                    new DataInputStream(clientSocket.getInputStream());

            dataOutputStream.writeUTF("SUBSCRIBER");

            for(Topic topic : Topic.values()){
                String menuOption = String.format("%s - %s",topic.ordinal(),topic.name().toUpperCase());
                System.out.println(menuOption);
            }
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Digite o número da opção escolhida:");
            dataOutputStream.writeUTF(keyboard.nextLine());
            while (true){
                //System.out.println(dataInputStream.readUTF());

                //Generate menu
                //System.out.println("\n");
//                for(Topic topic : Topic.values()){
//                    String menuOption = String.format("%s - %s",topic.ordinal(),topic.name().toUpperCase());
//                    System.out.println(menuOption);
//                }
//                Scanner keyboard = new Scanner(System.in);
//                System.out.print("Digite o número da opção escolhida:");
//                dataOutputStream.writeUTF(keyboard.nextLine());
                System.out.println(dataInputStream.readUTF());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
