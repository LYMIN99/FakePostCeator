package com.lymindev.fakepostcreator.view.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PostItem extends RealmObject {

    @PrimaryKey
    private int id;
    private String type;
    private String fullname;
    private byte[] profile;
    private boolean isImagePost;
    private byte[] photoPost;
    private String likes;
    private String comments;
    private String shares;
    private String likeType;
    private String date;
    private String description;


    public PostItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isImagePost() {
        return isImagePost;
    }

    public void setImagePost(boolean imagePost) {
        isImagePost = imagePost;
    }

    public byte[] getPhotoPost() {
        return photoPost;
    }

    public void setPhotoPost(byte[] photoPost) {
        this.photoPost = photoPost;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getShares() {
        return shares;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
