package com.std;

import com.std.base.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@ServletComponentScan(basePackages = "com.std")
@SpringBootApplication(scanBasePackages = "com.std")
@PropertySource(value = "classpath:my.properties", encoding = Constants.CHAR_SET.UTF_8)
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}
