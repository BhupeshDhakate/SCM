package com.home.Validaters;

// import javax.imageio.ImageIO;
// import java.awt.image.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>{

    private static final long MAX_FILE_SIZE = 1024*1024*2; //2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File can not be empty").addConstraintViolation();
            return true;
        }
        // file size
        System.out.println("file size: "+file.getSize());
        if(file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File Size should be less than 2mb").addConstraintViolation();
            return false;
        }

        //resolution
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     if (bufferedImage.g) {
                
        //     }
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        return true;
    }

}
