package com.sss.common.controller;

import com.sss.common.annotation.AccessToken;
import com.sss.common.config.ImageUploadConfig;
import com.sss.common.util.FileHelper;
import com.sss.common.util.ResponseDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: wyy-sss
 * @date: 2019-10-24 09:58
 **/
@RestController
@AccessToken(isCheck = false)
public class TestController {
    @Autowired
    private ImageUploadConfig imageUploadConfig;

    @PostMapping("/test")
    public Object test(@RequestParam MultipartFile file){
        String s = FileHelper.uploadFile(file, imageUploadConfig.getFilePath(), imageUploadConfig.getUrLPath());
        return ResponseDataHelper.success(s);
    }
}
