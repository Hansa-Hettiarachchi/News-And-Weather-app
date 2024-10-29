package com.example.trvavelguidassistant.model;

public class SocialMediaModel {
    String userName;
    String photo;
    String location;
    String userPic;
    Long likeCount;
    String key5;

    public SocialMediaModel() {
    }

    public SocialMediaModel(String userName, String location,String photo, String userPic,Long likeCount,String key5) {
        this.userName = userName;
        this.photo = photo;
        this.location = location;
        this.userPic = userPic;
        this.likeCount =likeCount;
        this.key5 = key5;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
