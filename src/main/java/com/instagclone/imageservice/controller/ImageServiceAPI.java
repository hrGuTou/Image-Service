package com.instagclone.imageservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ImageServiceAPI {


    @Autowired
    CloudinaryService cloudinaryService;

    /**
     * Health Check
     * @return
     */

    @GetMapping("/health")
    public Object healthCheck(){
        JSONObject response = new JSONObject();
        response.put("Status","Online");
        return response;
    }


    /**
     *
     * @param  "userId":string
     * @param  "photo":photo object
     * @return
     *             "userId": string
     *             "status": boolean
     */

    @PostMapping("/upload")
    public Object uploadPhoto(@RequestHeader(value = "userId") String user, @RequestParam("photo")MultipartFile file){
        JSONObject response = new JSONObject();

        Boolean uploaderStat = cloudinaryService.upload(user, file);

        response.put("userId", user);
        response.put("status", uploaderStat);

        return response;
    }

    /**
     *  Retrieve single photo url
     *
     * @param "photoId":string
     * @return
     *          "photoURL":string
     */
    @GetMapping("/view")
    public Object getPhoto(@RequestParam("photoId") String photo){
        JSONObject response = new JSONObject();

        String url = cloudinaryService.view(photo);
        response.put("photoURL", url);
        return response;
    }

    /**
     *  Retrieve all photo urls for a defined user
     *
     */
    @GetMapping("/viewAll")
    public Object getAllPhoto(@RequestParam("userId") String user){
        JSONObject response = new JSONObject();
        List<String> url = new ArrayList<>();

        List<Photo> res = cloudinaryService.findByUserId(user);
        for(Photo p:res){
            url.add(cloudinaryService.view(p.getPhotoID()));
        }

        response.put("userId", user);
        response.put("photoURL",url);

        return response;
    }

}
