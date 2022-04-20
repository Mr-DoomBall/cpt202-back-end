package com.blog;

import com.blog.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册bean
 */
@Configuration
public class SpringConfiguration {

    /**
     * 注册全局异常处理bean
     */
    @Bean
    public GlobalExceptionHandler exceptionBean() {
        return new GlobalExceptionHandler();
    }

}
