package com.instagclone.imageservice.service;

import com.alibaba.fastjson.JSONObject;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.repository.PhotoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    Cloudinary cloudinary;

    public String upload(String user, MultipartFile file){
        if(user == null || file == null) return null;

        String publicID;

        //save photo to cloudanry
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            publicID = uploadResult.get("public_id").toString();
            logger.info("Uploaded successfully+id: "+publicID);

        } catch (Exception err) {
            logger.error("Failed to upload photo for user: "+user);
            logger.error(err.getMessage());
            return null;
        }

        //get the path
        //store into mongodb
        if(publicID != null){

            Photo photo = new Photo();
            photo.setPhotoID(publicID);
            photo.setCreationDate(new Date());
            photo.setUserID(user);

            photoRepository.insert(photo);
            logger.info("Saved metadata to database");

        }


        return publicID;
    }


    public String getPhotoURL(String photoID){
        return cloudinary.url().secure(true).format("jpg").publicId(photoID).generate();
    }

    public JSONObject view(String photoID){
        JSONObject res = new JSONObject();

//        logger.info(String.valueOf(cloudinary.url()));
        String url = getPhotoURL(photoID);

        Photo p = photoRepository.findByPhotoID(photoID);
        res.put("photoId", p.getPhotoID());
        res.put("userId", p.getUserID());
        res.put("creationDate", p.getCreationDate());
        res.put("photoURL", url);

        logger.info("url: " + url);
        return res;
    }

    public List<Photo> findByUserId(String userID){
        return photoRepository.findByUserID(userID);
    }

    public Boolean deletePhoto(String userID, String photoID) throws Exception {

        Photo p = photoRepository.findByPhotoID(photoID);

        if (p!=null && userID.equals(p.getUserID())) {
            // allow deletion
            // 1. check exist
            try {
                if (photoRepository.findByPhotoID(photoID) != null && (Integer) cloudinary.search().expression("public_id:" + photoID).execute().get("total_count") != 0) {
                    // preferred synchronized
                    // 2. delete from database
                    photoRepository.deleteByPhotoID(photoID);
                    // 3. delete from cloudinary
                    cloudinary.uploader().destroy(photoID, ObjectUtils.asMap("resource_type", "image"));

                    return true;
                }
            } catch (Exception err) {
                logger.error("Error at Deletion: " + err);
            }
        } else{
            throw new Exception("Photo not found or you don't have the permission");
        }

        return false;
    }
}
