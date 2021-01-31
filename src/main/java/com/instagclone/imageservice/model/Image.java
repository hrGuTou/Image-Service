package com.instagclone.imageservice.model;

import java.util.Date;

public class Image {
    private Integer photoID;
    private Integer userID;
    private String photoPath;
    private Integer photoLat;
    private Integer photoLong;
    private Integer userLat;
    private Integer userLong;
    private Date creationDate;

    public Integer getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Integer photoID) {
        this.photoID = photoID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getPhotoLat() {
        return photoLat;
    }

    public void setPhotoLat(Integer photoLat) {
        this.photoLat = photoLat;
    }

    public Integer getPhotoLong() {
        return photoLong;
    }

    public void setPhotoLong(Integer photoLong) {
        this.photoLong = photoLong;
    }

    public Integer getUserLat() {
        return userLat;
    }

    public void setUserLat(Integer userLat) {
        this.userLat = userLat;
    }

    public Integer getUserLong() {
        return userLong;
    }

    public void setUserLong(Integer userLong) {
        this.userLong = userLong;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
