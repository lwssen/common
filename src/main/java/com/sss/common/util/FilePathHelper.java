package com.sss.common.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sss
 * @date: 2019-09-12 11:44
 **/
public class FilePathHelper {



    /**
     * 根据文件路径获取resource资源目录下的文件输入流
     * @param fileFullPath
     * @return java.io.InputStream
     * @author SSS
    **/
    public   static InputStream resourceLoader(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getInputStream();
    }

    /**
     * 根据文件路径获取文件绝对路径
     * @param fileFullPath
     * @return java.io.InputStream
     * @author SSS
     **/
    public   static String getFilePath(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getFile().getPath();
    }

    /**
     * 获取某个目录下的所有文件
     * @param strPath
     * @return java.util.List<java.lang.String>
     * @author SSS
     **/
    public static List<String> getFileList(String strPath) {
        List<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) {
                    // 获取文件绝对路径
                    getFileList(files[i].getAbsolutePath());
                } else {
                    // 获取文件绝对路径
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    filelist.add(fileName);
                }
            }
        }
        return filelist;
    }
}
