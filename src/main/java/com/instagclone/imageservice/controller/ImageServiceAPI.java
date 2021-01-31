package com.instagclone.imageservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.instagclone.imageservice.model.Image;
import com.instagclone.imageservice.model.User;
import com.instagclone.imageservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ImageServiceAPI {

    @Autowired
    ImageService imageService;

    /**
     *  GET all images' metadata from database for the user
     *
     * @param: user object
     * @return: list of img urls
     */
    @GetMapping("/image")
    public Object getImage(@RequestBody User user){
        JSONObject res = new JSONObject();
        List<String> imgURLs;

        if(user==null) {
            res.put("status", "Error");
            res.put("error", "Required user object");
            return res;
        }

        imgURLs = imageService.getAllImgURL(user.getUserID());

        res.put("user", user);
        res.put("imgURL", imgURLs);
        return res;
    }

    /**
     *  Save image metadata into database for the user
     *
     * @param: user object, photo object
     * @return: boolean status
     */
    @PostMapping("/image")
    public Object saveImage(@RequestBody User user, Image image){
        JSONObject res = new JSONObject();
        boolean status;

        status = imageService.saveImageURL(user.getUserID(),image.getPhotoPath());

        res.put("status", status);
        return res;
    }
}
