package com.lfd.soa.demo.srv.support.queue.message;

import cn.hutool.core.date.DateUtil;
import com.lfd.soa.common.orm.OrmTemplate;
import com.lfd.soa.common.orm.entity.ConditionParam;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.support.queue.GlobalMQConfig;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceType;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * mq消息表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-04-25
 */
public class SysMqMessageTemplate {

    public static void consumeMessageSuccess(HashMap msgMap) throws Exception {
        if (!msgMap.containsKey(GlobalMQConfig.getConfig().getUuidName())) {
            return;
        }
        SysMqMessage sysMqMessage = SysMqMessageTemplate.getSysMessage(String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName())));
        if (null == sysMqMessage) {
            return;
        }
        sysMqMessage.setConsumeState(1);
        sysMqMessage.setSuccessTime(DateUtil.date());
        OrmTemplate.save(sysMqMessage);
    }

    public static void consumeMessageFail(String service, String queue, HashMap msgMap, String errorMsg) throws Exception {
        if (!msgMap.containsKey(GlobalMQConfig.getConfig().getUuidName())) {
            return;
        }
        SysMqMessage sysMqMessage = SysMqMessageTemplate.getSysMessage(String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName())));
        if (null == sysMqMessage) {
            SysMqMessageTemplate.saveMessage(service, queue, msgMap, errorMsg);
            return;
        }
        sysMqMessage.setTryCount(sysMqMessage.getTryCount() + 1);
        sysMqMessage.setTryTime(DateUtil.date());
        if (sysMqMessage.getTryCount().equals(GlobalMQConfig.getConfig().getMaxConsume())) {
            sysMqMessage.setConsumeState(2);
        }
        sysMqMessage.setErrorLog(errorMsg);
        OrmTemplate.save(sysMqMessage);
    }

    public static SysMqMessage getSysMessage(String uuid) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("uuid", uuid);
        return OrmTemplate.get(conditionParam, SysMqMessage.class);
    }

    public static void saveMessage(String service, String queue, Map<String, Object> msgMap, String errorMsg) throws Exception {
        String uuid = String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName()));
        SysMqMessage sysMqMessage = new SysMqMessage();
        sysMqMessage.setUuid(uuid);
        sysMqMessage.setMessage(JsonUtil.toJson(msgMap));
        sysMqMessage.setService(service);
        sysMqMessage.setQueue(queue);
        sysMqMessage.setType(RabbitServiceType.CONSUMER.name());
        sysMqMessage.setConsumeState(0);
        sysMqMessage.setTryCount(0);
        sysMqMessage.setTryTime(DateUtil.date());
        sysMqMessage.setErrorLog(errorMsg);
        sysMqMessage.setCreateUser("system");
        sysMqMessage.setCreateTime(DateUtil.date());
        OrmTemplate.save(sysMqMessage);
    }
}
