package finalproject.youtube.server;

import java.net.Socket;

public interface Response {
    void sendResponse(Socket socket);
}
