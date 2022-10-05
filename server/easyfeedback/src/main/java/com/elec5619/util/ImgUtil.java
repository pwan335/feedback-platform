package com.elec5619.util;


import cn.hutool.core.codec.Base64Encoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImgUtil {

    public  static String saveImg(MultipartFile file){
        try {
            String content= Base64Encoder.encode(file.getBytes());
            System.out.println(content.length());
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
