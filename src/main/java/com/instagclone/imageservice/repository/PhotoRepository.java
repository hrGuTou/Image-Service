package com.instagclone.imageservice.repository;

import com.instagclone.imageservice.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo, String> {
    // TODO: get all photos for the user
    List<Photo> findByUserID(String userID);

    Photo findByPhotoID(String photoID);

    Long deleteByPhotoID(String photoID);
}
