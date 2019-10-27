package com.sss.common.generatorcode;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.sql.Timestamp;
import java.util.ArrayList;


public class MyGenerator {

    private static String dbUrl =
            "jdbc:mysql://127.0.0.1:3306/myshiro?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT"
                    + "&nullNamePatternMatchesAll=true";
    private static String dbUserName = "root";
    private static String dbPassword = "root";
    private static String dbDriverClassName = "com.mysql.jdbc.Driver";

    private static String outputDir = System.getProperty("user.dir") + "\\src\\main\\java";

    private static String packageName = "com.sss.common";

    private static String[] tableNames = {"sss_role","sss_menu","sss_role_menu","sss_user_role"};

    public static void main(String[] args) {
          generatorCode();
    }

    private static void generatorCode(){
        //mySql的数据类型转换
         MySqlTypeConvert mySqlTypeConvert=new MySqlTypeConvert(){
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                String s = fieldType.toLowerCase();
                if (s.contains("datetime")) {
                    return DbColumnType.TIMESTAMP;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        };
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
       // String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(outputDir);
        gc.setAuthor("sss");
        //设置生成代码后是否打开代码文件目录
        gc.setOpen(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        gc.setActiveRecord(false);
        gc.setFileOverride(true);
        gc.setServiceName("I%sService").setMapperName("I%sDao").setXmlName("I%sDao").setBaseResultMap(true).setBaseColumnList(true);
//        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setTypeConvert(mySqlTypeConvert);
        dsc.setUrl(dbUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(dbDriverClassName);
        dsc.setUsername(dbUserName);
        dsc.setPassword(dbPassword);
//        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
       // pc.setModuleName("com.sss.common");
        pc.setEntity("entity").setController("controller").setService("service").setMapper("dao").setXml("dao");
        //设置entity，service,dao上级包
        pc.setParent(packageName);
//        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置要生成的表
        strategy.setInclude(tableNames);
        //设置自动填充字段
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        //设置逻辑删除字段
        strategy.setLogicDeleteFieldName("is_deleted");
//        strategy.setTablePrefix("");
        //设置entity，service,dao继承的父类
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
//        strategy.setSuperServiceClass("com.baomidou.ant.common.BaseService");
//        strategy.setSuperServiceImplClass("com.baomidou.ant.common.BaseServiceImpl");
//       strategy.setSuperMapperClass("com.baomidou.ant.common.BaseMapper");
//       strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        //是否使用lombook注解
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        mpg.setStrategy(strategy);
//       mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc).setDataSource(dsc).setStrategy(strategy).setPackageInfo(pc);
        mpg.execute();
    }
}
