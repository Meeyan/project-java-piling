package com.std;

import com.std.base.Constants;
import com.std.springboot.filter.ChainFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;


/**
 * 启动类
 */
@PropertySource(value = "classpath:my.properties", encoding = Constants.CHAR_SET.UTF_8)
@ServletComponentScan(basePackages = "com.std")
@SpringBootApplication(scanBasePackages = "com.std")
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

    /**
     * 使用方式注入Filter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<ChainFilter> filterRegistrationBean() {
        FilterRegistrationBean<ChainFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ChainFilter());
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setName("chainFilter");
        return registrationBean;

    }
}
