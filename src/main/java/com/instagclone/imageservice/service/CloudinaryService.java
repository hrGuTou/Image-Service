package com.instagclone.imageservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.repository.PhotoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Boolean upload(String user, MultipartFile file){
        if(user == null || file == null) return false;

        String publicID;

        //save photo to cloudanry
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            publicID = uploadResult.get("public_id").toString();
            logger.info("Uploaded successfully+id: "+publicID);

        } catch (Exception err) {
            logger.error("Failed to upload photo for user: "+user);
            logger.error(err.getMessage());
            return false;
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


        return true;
    }

    public String view(String photoID){
//        logger.info(String.valueOf(cloudinary.url()));
        String url = cloudinary.url().secure(true).format("jpg").publicId(photoID).generate();
        logger.info("url: " + url);
        return url;
    }

    public List<Photo> findByUserId(String userID){
        return photoRepository.findByUserID(userID);
    }

}
