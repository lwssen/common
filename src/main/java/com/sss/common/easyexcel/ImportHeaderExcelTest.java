package com.sss.common.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @date: 2019-09-06 16:07
 **/
@Data
public class ImportHeaderExcelTest extends BaseRowModel {
    /**
     * 测试用户名
     */
    @ExcelProperty(value = "测试用户名",index = 0)
    private String userName;
    /**
     * 测试用户密码
     */
    @ExcelProperty(value = "测试用户密码",index = 1)
    private String password;
}
