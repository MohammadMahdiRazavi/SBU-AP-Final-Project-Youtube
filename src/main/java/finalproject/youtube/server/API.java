package finalproject.youtube.server;

import java.io.*;
import java.net.Socket;

import finalproject.youtube.File.FileHandler;
import finalproject.youtube.database.DatabaseManger;
import finalproject.youtube.server.requests.*;
import org.json.JSONObject;

public class API implements Runnable{
    private final Socket userSocket;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final ObjectOutputStream objectOutputStream;

    public API(Socket userSocket){
        this.userSocket = userSocket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            this.dataInputStream = new DataInputStream(userSocket.getInputStream());
            this.dataOutputStream = new DataOutputStream(userSocket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(userSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        JSONObject messageFromClient;
        while (userSocket.isConnected()){
            try {
                String messageFromClientString = bufferedReader.readLine();
                messageFromClientString = messageFromClientString.substring(messageFromClientString.indexOf("{"));
                messageFromClient = new JSONObject(messageFromClientString);
                int userRequest = messageFromClient.getInt("code");
                if (userRequest == 134){
//                    long video_size = dataInputStream.readLong();
                    byte[] videos_byte = new byte[messageFromClient.getInt("size")];
                    dataInputStream.readFully(videos_byte);
                    FileHandler.saveVideo(videos_byte, messageFromClient.getString("video_id") + messageFromClient.getString("extension")
                            , FileHandler.PATH_TO_SAVE_SERVER_FOLDER);
                }
                makeRequest(userRequest, messageFromClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void makeRequest(int requestCode, JSONObject jsonRequest){
        if (requestCode == 125){
            String video_id = jsonRequest.getString("video_id");
            String extension = jsonRequest.getString("extension");
            byte[] video_bytes = FileHandler.readVideo(FileHandler.PATH_TO_SAVE_SERVER_FOLDER + video_id + extension);
            JSONObject response = new JSONObject();
            response.put("size", video_bytes.length);
            try {
//                bufferedWriter.write(response.toString());
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
                dataOutputStream.writeInt(video_bytes.length);
                dataOutputStream.write(video_bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Request request = switch (requestCode) {
            case 100 -> new AddComment();
            case 101 -> new AddDislike();
            case 102 -> new AddHistory();
            case 103 -> new AddLike();
            case 104 -> new SendNotif();
            case 105 -> new SubscribeChannel();
            case 106 -> new AddVideoToChannelPlayList();
            case 107 -> new CheckDislike();
            case 108 -> new CheckLike();
            case 109 -> new CreatChannel();
            case 110 -> new CreateChannelPlaylist();
            case 111 -> new EditChannel();
            case 112 -> new EditChannelPlayList();
            case 113 -> new EditProfile();
            case 114 -> new EditVideo();
            case 115 -> new GetChannel();
            case 116 -> new GetHistory();
            case 117 -> new GetLikedVideos();
            case 118 -> new GetNotifications();
            case 119 -> new GetChannelPlayLists();
            case 120 -> new GetChannelPlayListsVideos();
            case 121 -> new GetPopularChannels();
            case 122 -> new GetProfile();

            //TODO -> implement it later
            case 123 -> new GetRecommendedVideos();
            //TODO -> implement it later
            case 127 -> new GetYouTubeMix();
            //TODO -> implement it later
            case 132 -> new Trending();

            case 134 -> new UploadVideo();
            case 124 -> new GetSubscriptions();
            case 126 -> new GetVideoAttributes();
            case 128 -> new Login();
            case 129 -> new DisableNotif();
            case 130 -> new SearchVideo();
            case 131 -> new SingUp();
            case 133 -> new UnsubscribeChannel();
            case 135 -> new CreatUserPlaylist();
            case 136 -> new GetUserPlaylist();
            case 137 -> new EditUserPlaylist();
            case 138 -> new AddVideoToUserPlaylist();
            case 139 -> new RemoveVideoFromUserPlaylist();
            case 140 -> new RemoveVideoFromChannelPlaylist();
            case 141 -> new SearchChannel();
            case 142 -> new EnableNotif();
            case 143 -> new SetNotificationsAsSeen();
            case 144 -> new GetComments();
            case 145 -> new CheckIfSubscribed();
            default -> null;
        };
        startRequest(request, jsonRequest);
    }

    private void startRequest(Request request, JSONObject jsonRequest){
        new Thread(() -> {
            request.conductRequest(jsonRequest);
            if (!userSocket.isClosed() && userSocket.isConnected()) {
                sendResponse(request);
            }
        }).start();
    }

    private synchronized void sendResponse(Request request){
        String clas = getClass(request.getClass());
        if (clas.equals("Json")){
            JSONObject jsonObject = request.getJsonResponse();
            try {
                bufferedWriter.write(jsonObject.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (request instanceof GetChannel) {
                    objectOutputStream.writeObject(((GetChannel) request).getResponse());
                } else if (request instanceof GetHistory) {
                    objectOutputStream.writeObject(((GetHistory) request).getResponse());
                } else if (request instanceof GetLikedVideos) {
                    objectOutputStream.writeObject(((GetLikedVideos) request).getResponse());
                } else if (request instanceof GetNotifications) {
                    objectOutputStream.writeObject(((GetNotifications) request).getResponse());
                } else if (request instanceof GetChannelPlayLists) {
                    objectOutputStream.writeObject(((GetChannelPlayLists) request).getResponse());
                } else if (request instanceof GetChannelPlayListsVideos) {
                    objectOutputStream.writeObject(((GetChannelPlayListsVideos) request).getResponse());
                } else if (request instanceof GetPopularChannels) {
                    objectOutputStream.writeObject(((GetPopularChannels) request).getResponse());
                } else if (request instanceof GetProfile) {
                    objectOutputStream.writeObject(((GetProfile) request).getResponse());
                } else if (request instanceof GetSubscriptions) {
                    objectOutputStream.writeObject(((GetSubscriptions) request).getResponse());
                } else if (request instanceof GetVideoAttributes) {
                    objectOutputStream.writeObject(((GetVideoAttributes) request).getResponse());
                } else if (request instanceof SearchVideo) {
                    objectOutputStream.writeObject(((SearchVideo) request).getResponse());
                } else if (request instanceof GetUserPlaylist) {
                    objectOutputStream.writeObject(((GetUserPlaylist) request).getResponse());
                } else if (request instanceof SearchChannel) {
                    objectOutputStream.writeObject(((SearchChannel) request).getResponse());
                } else if (request instanceof GetComments) {
                    objectOutputStream.writeObject(((GetComments) request).getResponse());
                }
                objectOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getClass(Class<?> requestClass){
        if (requestClass == GetChannel.class){
            return "GetChannel";
        } else if (requestClass == GetHistory.class) {
            return "GetHistory";
        } else if (requestClass == GetLikedVideos.class) {
            return "GetLikedVideos";
        } else if (requestClass == GetNotifications.class){
            return "GetNotifications";
        } else if (requestClass == GetChannelPlayLists.class) {
            return "GetPlayLists";
        } else if (requestClass == GetChannelPlayListsVideos.class) {
            return "GetPlayListsVideos";
        } else if (requestClass == GetPopularChannels.class){
            return "GetPopularChannels";
        } else if (requestClass == GetProfile.class) {
            return "GetProfile";
        } else if (requestClass == GetRecommendedVideos.class) {
            return "GetRecommendedVideos";
        } else if (requestClass == GetSubscriptions.class) {
            return "GetSubscriptions";
        } else if (requestClass == GetVideo.class) {
            return "GetVideo";
        } else if (requestClass == GetVideoAttributes.class) {
            return "GetVideoAttributes";
        } else if (requestClass == GetYouTubeMix.class) {
            return "GetYouTubeMix";
        } else if (requestClass == SearchVideo.class) {
            return "Search";
        } else if (requestClass == Trending.class) {
            return "Trending";
        } else if (requestClass == GetUserPlaylist.class) {
            return "GetUserPlaylist";
        } else if (requestClass == SearchChannel.class) {
            return "SearchChannel";
        } else if (requestClass == GetComments.class) {
            return "GetComments";
        } else {
            return "Json";
        }
    }

    private static int getSerializedObjectSize(Object object){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(object);
            objStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteStream.toByteArray().length;
    }
}
