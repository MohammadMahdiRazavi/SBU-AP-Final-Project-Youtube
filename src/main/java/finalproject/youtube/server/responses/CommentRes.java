package finalproject.youtube.server.responses;

import finalproject.youtube.Model.Comment;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentRes implements Serializable {
    int SQLResponse;
    ArrayList<Comment> comments;

    public CommentRes(){
        comments = new ArrayList<>();
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public int getSQLResponse() {
        return SQLResponse;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setSQLResponse(int SQLResponse) {
        this.SQLResponse = SQLResponse;
    }
}
