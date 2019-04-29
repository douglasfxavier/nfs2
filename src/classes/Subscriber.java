package classes;

import java.net.Socket;

public class Subscriber {
    private String ip;
    private Socket socket;

    public Subscriber(String name, Socket socket) {
        this.ip = name;
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "classes.Subscriber{" +
                "ip='" + ip + '\'' +
                ", socket=" + socket +
                '}';
    }
}
