package runnable;

import classes.Server;

public class ServerRunner {

    public static void main(String[] args) {
        Server server = new Server();

        server.listen();
    }
}
