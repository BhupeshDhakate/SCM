package com.home.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.home.Helpers.AppConstants;
import com.home.services.imageService;

@Service
public class imgServiceImp implements imageService{

    private Cloudinary cloudinary;

    public imgServiceImp(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename){
        //code how upload the image on server
        //and return utl
        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id",filename));
            return this.getUrlFromPublicId(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId){
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP))
        .generate(publicId);
    }
}
