package com.instagclone.imageservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.service.CloudinaryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ImageServiceAPI {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


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
        logger.info("Called upload");
        JSONObject response = new JSONObject();
        boolean uploaderStat = false;
        // TODO: try catch
        String publicID = cloudinaryService.upload(user, file);

        if(publicID != null){
            uploaderStat = true;
        }

        response.put("userId", user);
        response.put("status", uploaderStat);
        response.put("public_id", publicID);

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
        return cloudinaryService.view(photo);
    }

    /**
     *  Retrieve all photo urls for a defined user
     *
     */
    @GetMapping("/viewAll")
    public Object getAllPhoto(@RequestHeader("userId") String user){
        JSONObject response = new JSONObject();
        List<String> url = new ArrayList<>();

        List<Photo> res = cloudinaryService.findByUserId(user);
        for(Photo p:res){
            url.add(cloudinaryService.getPhotoURL(p.getPhotoID()));
        }

        response.put("userId", user);
        response.put("photoURL",url);

        return response;
    }

    @DeleteMapping("/delete/")
    public Object deletePhoto(@RequestHeader(value="userId") String user, @RequestParam String photoId){
        JSONObject response = new JSONObject();

        boolean res = false;
        String error = "";

        try{
            res = cloudinaryService.deletePhoto(user, photoId);
        }catch (Exception err){
            error = err.getMessage();
        }

        response.put("userId", user);
        response.put("status", res);
        response.put("message", error);
        return response;
    }



}
