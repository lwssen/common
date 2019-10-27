package com.sss.common.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作数据库
 * @author: wyy-sss
 * @date: 2019-09-12 11:54
 **/
public class DataBaseUtil {

    private String mysqlDriver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/test1?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
    private String newUrl = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "root";
    private Connection conn = null;
    private Connection newConn = null;



   /**
    * java代码动态创建数据库
    * @param userName 用户名
    * @param password2 用户名称
    * @param databaseName  数据库名称
    * @return java.sql.Connection
    * @author WYY-SSS
   **/
    public Connection getConn(String userName,String password2,String databaseName) {
        try {
            Class.forName(mysqlDriver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            //创建用户
            String createUserSql="CREATE user '"+userName+" '@'%' IDENTIFIED BY '"+password2+"'";
            System.out.println(createUserSql);
            //赋予该数据库的全部权限
            String grantUserSql="GRANT ALL ON "+databaseName+".* TO '"+userName+"'@'%'";
            System.out.println(grantUserSql);
            //创建数据库
            String databaseSql = "create database " + databaseName+" CHARACTER SET utf8 COLLATE utf8_general_ci";
            //刷新语句
            String flushSql = "flush PRIVILEGES;";
            String tableSql = "create table t_user (username varchar(50) not null primary key,"
                    + "password varchar(20) not null ); ";
          //  String databaseSql = "create database " + databaseName;
            conn = DriverManager.getConnection(url, username, password);
            Statement smt = conn.createStatement();
            if (conn != null) {
                System.out.println("数据库连接成功!");
                smt.executeUpdate(databaseSql);
                newConn = DriverManager.getConnection(newUrl + databaseName+"?useSSL=false&serverTimezone=Asia/Shanghai", username, password);
                if (newConn != null) {
                    System.out.println("已经连接到新创建的数据库：" + databaseName);
                    Statement newSmt = newConn.createStatement();
                    //DDL语句返回值为0;
                    int i = newSmt.executeUpdate(tableSql);
                    int userAdd = newSmt.executeUpdate(createUserSql);
                    int grantUser= newSmt.executeUpdate(grantUserSql);
                    int flush = newSmt.executeUpdate(flushSql);

                    if (i == 0) {
                        System.out.println(tableSql + "表已经创建成功!");
                    }
                }
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return conn;
    }

    /**
    * <p>运行指定的sql脚本
    * @param sqlFileName 需要执行的sql脚本的名字
    */
    public static void runSqlFile(String sqlFileName) throws IOException {
        String url2 = "jdbc:mysql://localhost:3306/test1?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "root";
        try {
//            // 获取数据库相关配置信息
//            Properties props = Resources.getResourceAsProperties("application.yml");
//            // jdbc 连接信息: 注: 现在版本的JDBC不需要配置driver，因为不需要Class.forName手动加载驱动
//            String url = props.getProperty("jdbc.url");
//            String username = props.getProperty("jdbc.username");
//            String password = props.getProperty("jdbc.password");
            // 建立连接
            Connection conn = DriverManager.getConnection(url2, username, password);
            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 执行SQL脚本  读取resource目录下的sql文件
            runner.runScript(Resources.getResourceAsReader("sql/" + sqlFileName + ".sql"));
            // 关闭连接
            conn.close();
            // 若成功，打印提示信息
            System.out.println("====== SUCCESS ======");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }



}
