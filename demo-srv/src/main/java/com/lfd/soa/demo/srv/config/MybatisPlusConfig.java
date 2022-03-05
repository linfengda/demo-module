package com.lfd.soa.demo.srv.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lfd.soa.demo.srv.support.mybatis.MybatisResultInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MybatisPlusConfig
 * @Description: mybatisPlus配置
 * @date: 2018/1/18
 * @author heweiming
 * 1.0
 */
@Configuration
@MapperScan({"com.patpat.msp.**.mapper"})
public class MybatisPlusConfig {

    /**
     * @Author mochengqi
     * @Description mybatis plus 自带分页插件,一缓和二缓遵循mybatis的规则
     * @Date 16:23 2021/8/18
     **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInnerInterceptor.setOverflow(false);

        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 结果集拦截插件
     */
    @Bean
    public MybatisResultInterceptor mspResultInterceptor() {
        return new MybatisResultInterceptor();
    }
}
