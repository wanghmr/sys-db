package com.cy;

import com.cy.entity.Log;
import com.cy.mapper.LogMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DbApplicationTests {

    @Autowired
    private LogMapper logMapper;

    @Test
    void contextLoads() {
        int count = logMapper.findAllLogCount("admin");
        System.out.println("----------"+count);
    }

    @Test
    void contextLoads2() {
        List<Log> logPage = logMapper.findAllLogPage("admin", 1, 6);
        System.out.println("----------"+logPage.toString());
        System.out.println("----------"+logPage.size());
    }


}
