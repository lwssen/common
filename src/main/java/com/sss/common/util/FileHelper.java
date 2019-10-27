package com.sss.common.util;

import com.sss.common.config.ImageUploadConfig;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2019-09-06 15:45
 **/
public class FileHelper {


    public static void setDownloadResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        //对特殊符号进行处理
        fileName = URLEncoder.encode(fileName, "UTF-8")
                .replaceAll("\\+", "%20").replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#").replaceAll("%26", "\\&").replaceAll("%2C", "\\,");
        //设置编码字符
        response.setCharacterEncoding("utf-8");
        //设置内容类型为下载类型
        response.setContentType("application/x-download");
        //设置 content-disposition 响应头控制浏览器以下载的形式打开文件、文件名称
        response.addHeader("Content-Disposition",
                "attachment;" +
                        "filename=" + fileName + ";" +
                        "filename* = UTF-8''" + fileName);
    }

    /**
     * 下载文件
     *
     * @param response HttpServletResponse
     * @param file     File型文件
     * @throws IOException 下载失败
     */
    public static void download(HttpServletResponse response, File file) throws IOException {
        setDownloadResponse(response, file.getName());
        try (InputStream in = new FileInputStream(file.getAbsolutePath());
             OutputStream out = response.getOutputStream()) {
            //文件大小
            response.setContentLength(in.available());
            byte[] b = new byte[9216];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
                out.flush();
            }
        } catch (ClientAbortException cae) {
        }
    }


    /**
     * 文件上传
     * @param file
     * @return java.lang.String
     * @author SSS
    **/
    public static  String uploadFile(MultipartFile file,String savePath,String requestPath) {
//        //图片类型
//        String imageType=imageUploadConfig.getImageType();
//        //图片大小限制
//        Long imageSize=imageUploadConfig.getFileSize();
//        if (file.isEmpty()) {
//            return "文件为空，请选择文件！！！";
//        }
        String fileName = file.getOriginalFilename();
        String  suffixName = fileName.substring(fileName.lastIndexOf("."));
//        //得到文件以 K 为单位的大小
//        long fileSize = file.getSize() / 1000;
//        if (fileSize >imageSize) {
//            return "图片过大无法上传,请选择10M以下的图片！！！";
//        }
//        boolean flag=false;
//        String[] IMAGE_TYPE = imageType.split(",");
//        for (String type : IMAGE_TYPE) {
//            //判断图片格式是否是imageType中的之一结尾
//            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)) {
//                flag=true;
//                break;
//            }
//        }
//        if (!flag) {
//            return "图片格式不是png,jpg,jpeg中的一种，请正确的图片格式！！！";
//        }
        //防止文件名字重复,重新命名文件
        String newFileName = MyDateTimeUtils.getCurrentLocalDateTimeStamp() + suffixName;
        //上传文件存放位置
        String filePath=savePath;
        //返回前端访问的URL路径
    //   String urlPath=imageUploadConfig.getUrLPath();
        File dest = new File(filePath + newFileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String fileUrl=null;
        try {
            //文件上传
            file.transferTo(dest);
            fileUrl=requestPath+ newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
         return fileUrl;
    }
}
