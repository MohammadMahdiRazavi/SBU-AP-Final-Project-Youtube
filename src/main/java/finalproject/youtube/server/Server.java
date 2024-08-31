package finalproject.youtube.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    private void startServer(){
        while (!serverSocket.isClosed()){
            try {
                Socket newUser = serverSocket.accept();
                API api = new API(newUser);
                Thread newThread = new Thread(api);
                newThread.start();
                System.out.println("[SERVER]: A client has connected to the server");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server youTube = new Server(new ServerSocket(8888));
        youTube.startServer();
    }
}
