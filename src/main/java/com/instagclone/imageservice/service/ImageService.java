package com.instagclone.imageservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    public List<String> getAllImgURL(Integer userID){
        // call mapper
        List<String> url = new ArrayList<>();
        url.add("dummy123");
        url.add("dummy456");
        return url;
    }

    public boolean saveImageURL(Integer userID, String imgURL){
        // save imgurl into the database

        // call mapper
        return true;
    }
}
