package com.wangyang.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {
    @Autowired
    Student student;
    Logger logger = LoggerFactory.getLogger(WebApplicationTests.class);

    @Test
    public void testLog(){//日誌級別
        logger.trace("trace===========");
        logger.debug("debug===========");
        logger.info("info============");
        logger.warn("warn============");
        logger.error("error===========");

    }
//    @Test
//    public void contextLoads() {
//        System.out.println(student.toString());
//    }

}
