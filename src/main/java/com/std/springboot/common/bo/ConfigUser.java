package com.std.springboot.common.bo;

import com.std.base.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@PropertySource(value = "classpath:object.properties", encoding = Constants.CHAR_SET.UTF_8)
@ConfigurationProperties(prefix = "config.user")
@Component
public class ConfigUser {
    private String userName;
    private String gender;
    private String userEmail;
    private List<String> userPhone;
}
