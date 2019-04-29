package runnable;

import classes.Topic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Publisher {
    public static void main(String[] args) {
        try{
            Socket publisherSocket = new Socket("127.0.0.1",7005);

            DataInputStream dataInputStream =
                    new DataInputStream(publisherSocket.getInputStream());
            DataOutputStream dataOutputStream =
                    new DataOutputStream(publisherSocket.getOutputStream());
            dataOutputStream.writeUTF("PUBLISHER");
            while (true){
                System.out.println(dataInputStream.readUTF());
                for(Topic topic : Topic.values()){
                    String menuOption = String.format("%s - %s",topic.ordinal(),topic.name().toUpperCase());
                    System.out.println(menuOption);
                }
                Scanner keyboard = new Scanner(System.in);
                System.out.print("Digite o número da opção escolhida:");
                dataOutputStream.writeUTF(keyboard.nextLine());
                System.out.println(dataInputStream.readUTF());;
                System.out.print("Digite a notícia:");
                dataOutputStream.writeUTF(keyboard.nextLine());
                System.out.println(dataInputStream.readUTF());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
