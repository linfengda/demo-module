package com.lfd.soa.demo.srv.service.springbootimport.target;

import com.lfd.soa.demo.srv.service.springbootimport.color.Yellow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: @Import可以传入配置类
 *
 * @author linfengda
 * @create 2019-12-30 15:09
 */
@Configuration
public class ColorRegistrarConfiguration {

    @Bean
    public Yellow yellow(){
        return new Yellow();
    }
}
