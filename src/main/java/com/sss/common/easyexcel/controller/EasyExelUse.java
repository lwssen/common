package com.sss.common.easyexcel.controller;

import com.sss.common.easyexcel.EasyExcelUtil;
import com.sss.common.easyexcel.ExportHeaderExcelTest;
import com.sss.common.easyexcel.ImportHeaderExcelTest;
import com.sss.common.entity.SssUser;
import com.sss.common.service.ISssUserService;
import com.sss.common.util.FileHelper;
import com.sss.common.util.FilePathHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019-09-06 16:12
 **/
@RestController
public class EasyExelUse {

    @Autowired
    private ISssUserService sssUserService;

    @GetMapping("/excel/export")
    public void easyExcelExport(HttpServletResponse servletResponse){
        //导出模型实体类
        ExportHeaderExcelTest exportHeaderExcelTest = new ExportHeaderExcelTest();
        //存储导出数据的集合
        List<ExportHeaderExcelTest> tableHeaderExcelProperties = new ArrayList<>();


        List<SssUser> list = sssUserService.list();
        for (SssUser sssUser : list) {
            ExportHeaderExcelTest exportHeaderExcelTest1 = new ExportHeaderExcelTest();
            BeanUtils.copyProperties(sssUser,exportHeaderExcelTest1);
            tableHeaderExcelProperties.add(exportHeaderExcelTest1);
        }
        try {
            EasyExcelUtil.writeExcel(servletResponse,tableHeaderExcelProperties,"测试excel","测试shee名字",exportHeaderExcelTest);
            FileHelper.setDownloadResponse(servletResponse,"测试excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/excel/import")
    public Object easyExcelImport(MultipartFile file, HttpServletRequest request) throws IOException {
        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        //读取excel文件内容
        List<ImportHeaderExcelTest> rowModels = EasyExcelUtil.readExcel(inputStream, ImportHeaderExcelTest.class);
        ArrayList<SssUser> sssUsers = new ArrayList<>();
        for (ImportHeaderExcelTest rowModel : rowModels) {
            SssUser sssUser = new SssUser();
            BeanUtils.copyProperties(rowModel,sssUser);
            sssUsers.add(sssUser);
        }
        sssUserService.saveBatch(sssUsers);
          return "aaaa" ;
    }

    /**
     * resource目录下的文件下载
     * @param response
     * @return void
     * @author SSS
    **/
    @GetMapping("/excel/template/download")
    public void  excelTemplateDowndload(HttpServletResponse response) throws IOException {
        String filePath="classpath:/static/template/测试excel.xlsx";
        String filename="测试excel.xlsx";
        FileHelper.setDownloadResponse(response,filename);
        InputStream inputStream = FilePathHelper.resourceLoader(filePath);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream,outputStream);
        response.flushBuffer();
    }
}
