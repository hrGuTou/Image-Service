package com.instagclone.imageservice;

import com.cloudinary.Cloudinary;
import com.instagclone.imageservice.model.Photo;
import com.instagclone.imageservice.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class ImageServiceApplicationTests {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    Cloudinary cloudinary;

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

    @Test
    public void checkIfExists(){
        try{
            Map res = cloudinary.search().expression("public_id=s2miatd4rcjr5e7wadqg").execute();
            logger.info(res.toString());

        }catch (Exception err){
            logger.error("Error: "+ err);
        }
    }

    @Test
    public void getPhotoById(){
        Photo p = photoRepository.findByPhotoID("xsln18wfru7zixm02sc7");
        logger.info(p.getUserID());
    }

}
