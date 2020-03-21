package com;

import com.std.BFBCDYS.v0009.L009_Demo;
import com.std.base.Constants;
import com.std.springboot.filter.ChainFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;


/**
 * 启动类
 *
 * @author zhaojy
 */
@PropertySource(value = "classpath:my.properties", encoding = Constants.CHAR_SET.UTF_8)
@ServletComponentScan(basePackages = "com.std")
@SpringBootApplication(scanBasePackages = "com.std")
public class ApplicationRun {

    private final Logger logger = LoggerFactory.getLogger(ApplicationRun.class);


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

    @Autowired
    L009_Demo demo;

    @PostConstruct
    public void testAsync() {
        /*System.out.println("-------------method begins----------------");
        demo.methodB();
        demo.methodA();
        System.out.println("-------------method ends----------------");*/
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("test");
                }
            }
        }.start();

    }

}
