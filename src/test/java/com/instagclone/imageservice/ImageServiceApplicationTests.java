package com.instagclone.imageservice;

import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class ImageServiceApplicationTests {

    @Autowired
    private PhotoRepository photoRepository;

//    @Test
//    public void testInsert(){
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDateTime now = LocalDateTime.now();
//
//        Photo photo = new Photo();
//        photo.setUserID("tester2");
//        photo.setPhotoPath("testPath");
//        photo.setPhotoLat(123);
//        photo.setPhotoLong(123);
//        photo.setUserLat(123);
//        photo.setUserLong(123);
//        //photo.setCreationDate(dtf.format(now));
//
//        photoRepository.insert(photo);
//
//    }

    @Test
    public void getByUserId(){
        List<Photo> res = photoRepository.findByUserID("test");
        for(Photo p: res){
            System.out.println(p.getPhotoID());
        }
    }

}
