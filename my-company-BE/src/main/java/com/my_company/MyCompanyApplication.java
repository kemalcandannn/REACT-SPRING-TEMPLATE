package com.my_company;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
public class MyCompanyApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyCompanyApplication.class, args);
        ApplicationVariable applicationVariable = context.getBean(ApplicationVariable.class);

        log.info("Application Start. You can access it from the address: http://localhost:{}{}/swagger-ui/index.html", applicationVariable.getPort(), applicationVariable.getServerServletContextPath());
    }

    @Component
    @Getter
    static class ApplicationVariable {
        @Value("${server.port}")
        private String port;

        @Value("${server.servlet.context-path}")
        private String serverServletContextPath;
    }
}
