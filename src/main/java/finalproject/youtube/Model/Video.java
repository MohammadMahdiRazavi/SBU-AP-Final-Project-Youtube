package finalproject.youtube.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Video {

    private String ID;
    private LocalDate uploadTime;
    private Integer view;
    private Integer duration; //??
    private Integer like;
    private Integer deslike;
    private String name;
    private String description;
    private ArrayList<String> category;
    private String path;

    private String IDChanel;
    public Video(String path,String ID,String idchal,String name,String description,String uplaodTime,
                 Integer duration,Integer like,Integer deslike,Integer view) {
        this.name = name;
        this.description = description;
        this.view = view;
        this.like = like;
        this.deslike = deslike;
        this.uploadTime = LocalDate.parse(uplaodTime);
        this.duration=duration; //format???
        this.ID=ID;
        this.IDChanel=idchal;
        this.setPath(path);
        this.category=new ArrayList<>();

    }

    public String getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDeslike() {
        return deslike;
    }

    public String getName() {
        return name;
    }

    public Integer getLike() {
        return like;
    }

    public int  getView() {
        return view;
    }

    public int  getDuration() {
        return duration;
    }

    public LocalDate getUploadTime() {
        return uploadTime;
    }

    public ArrayList<String> getHashtagsList() {
        return getCategory();
    }

    public void editName(String name) {
        this.name = name;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void Deslike(Integer change) {
        this.deslike += change;
    }

    public void Like(Integer change) {
        this.like += change;
    }

    public void appendView() {
        this.view ++;
    }

    public void addHashtags(String hashtag) {
        this.getCategory().add(hashtag);
    }
    public void removeHashtag(String hashtag){
        this.getCategory().remove(hashtag);
    }
    public String getHashtagString(){
        String hashtags="";
        for(String hashtag : this.getCategory()){
            hashtags += hashtag+",";
        }
        return (hashtags.substring(0,hashtags.length()-1)); // -1 or -2 ??
    }

    public String getIDChanel() {
        return IDChanel;
    }

    public void setIDChanel(String IDChanel) {
        this.IDChanel = IDChanel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.add( category);
    }
}