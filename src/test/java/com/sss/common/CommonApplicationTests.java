package com.sss.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Console;
import com.sss.common.dao.ISssMenuDao;
import com.sss.common.rbmq.HelloReceiver;
import com.sss.common.rbmq.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonApplicationTests {


    @Autowired
    private ISssMenuDao sssMenuDao;
    @Autowired
    private HelloSender sender;
    @Test
    public void contextLoads() throws IOException {
//        int i = sssMenuDao.deleteById(1);
//        System.out.println(i);

        ClassPathResource resource = new ClassPathResource("application.yml");
        Properties properties = new Properties();
        properties.load(resource.getStream());

        Console.log("Properties: {}", properties);

    }

    @Test
    public void rabbitMQTest(){
        sender.send();
    }
}
