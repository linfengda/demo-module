package com.lfd.soa.srv.demo.support.redis.service;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.srv.demo.bean.vo.UserVo;
import com.lfd.soa.srv.demo.support.redis.helper.LettuceTemplateHelper;
import com.lfd.soa.srv.demo.support.redis.lettuce.LettuceTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-02-19 23:31
 */
@Slf4j
public class LettuceRedisOperationServiceImpl implements RedisOperationService {
    private static LettuceTemplate<String, Object> lettuceTemplate = LettuceTemplateHelper.getTemplate();

    @Override
    public void stringSetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
    }

    @Override
    public void stringSetGetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
        lettuceTemplate.get("key:" + Thread.currentThread().getId());
    }

    @Override
    public void simpleListOperation() throws Exception {
        lettuceTemplate.set("key", "i am the boss");
        String str = (String) lettuceTemplate.get("key");
        log.info(str);
        Long row = lettuceTemplate.del("key");
        log.info("delete affect row: {}", row);


        UserVo userVo = new UserVo();
        userVo.setUserId(1);
        userVo.setUserName("流浪地球");
        row = lettuceTemplate.leftPush("myList", userVo);
        log.info("row length after leftPush: {}", row);
        userVo = (UserVo) lettuceTemplate.rightPop("myList");
        log.info("rightPop object: ", JsonUtil.toJson(userVo));
    }

    public static void main(String[] args) {
        try {
            LettuceRedisOperationServiceImpl testService = new LettuceRedisOperationServiceImpl();
            testService.simpleListOperation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
