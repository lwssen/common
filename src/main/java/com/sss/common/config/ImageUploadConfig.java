package com.sss.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: sss
 * @date: 2019-08-08 10:36
 **/
@Data
@Component
public class ImageUploadConfig {

    /**
     *  图片大小
    **/
    @Value("${image.size}")
    private Long fileSize;

    /**
     *  图片类型
     **/
    @Value("${image.type}")
    private String imageType;

    /**
     *  图片保存路径
     **/
    @Value("${image.save.path}")
    private String  filePath;
    /**
     *  前端访问图片接口的前缀URL路径
     **/
    @Value("${image.urlPath}")
    private String  urLPath;

    /**
     *  WebMvcConfiguration配置类的配置访问图片的前缀URL
     **/
    @Value("${image.config.path}")
    private String  configPath;

    /**
     *  excel格式类型
     **/
    @Value("${excel.type}")
    private String excelType;
}
