package com.my_company;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyCompanyApplicationTests {

    @Test
    void contextLoads() {
        int sum = 2 + 3;
        assertThat(sum).isEqualTo(5);
    }

}
