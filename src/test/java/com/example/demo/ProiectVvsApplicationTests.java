package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProiectVvsApplicationTests {

    @Autowired
    private BookingController controller;

    @Test
    void contextLoads() {
        //assertThat(controller).isNotNull();
    }

}
