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
public class SysMessageTemplate {

    public static void consumeMessageSuccess(HashMap msgMap) throws Exception {
        if (!msgMap.containsKey(GlobalMQConfig.getConfig().getUuidName())) {
            return;
        }
        SysMessage sysMessage = SysMessageTemplate.getSysMessage(String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName())));
        if (null == sysMessage) {
            return;
        }
        sysMessage.setConsumeState(1);
        sysMessage.setSuccessTime(DateUtil.date());
        OrmTemplate.save(sysMessage);
    }

    public static void consumeMessageFail(String service, String queue, HashMap msgMap, String errorMsg) throws Exception {
        if (!msgMap.containsKey(GlobalMQConfig.getConfig().getUuidName())) {
            return;
        }
        SysMessage sysMessage = SysMessageTemplate.getSysMessage(String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName())));
        if (null == sysMessage) {
            SysMessageTemplate.saveMessage(service, queue, msgMap, errorMsg);
            return;
        }
        sysMessage.setTryCount(sysMessage.getTryCount() + 1);
        sysMessage.setTryTime(DateUtil.date());
        if (sysMessage.getTryCount().equals(GlobalMQConfig.getConfig().getMaxConsume())) {
            sysMessage.setConsumeState(2);
        }
        sysMessage.setErrorLog(errorMsg);
        OrmTemplate.save(sysMessage);
    }

    public static SysMessage getSysMessage(String uuid) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("uuid", uuid);
        return OrmTemplate.get(conditionParam, SysMessage.class);
    }

    public static void saveMessage(String service, String queue, Map<String, Object> msgMap, String errorMsg) throws Exception {
        String uuid = String.valueOf(msgMap.get(GlobalMQConfig.getConfig().getUuidName()));
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUuid(uuid);
        sysMessage.setMessage(JsonUtil.toJson(msgMap));
        sysMessage.setService(service);
        sysMessage.setQueue(queue);
        sysMessage.setType(RabbitServiceType.CONSUMER.name());
        sysMessage.setConsumeState(0);
        sysMessage.setTryCount(0);
        sysMessage.setTryTime(DateUtil.date());
        sysMessage.setErrorLog(errorMsg);
        sysMessage.setCreateUser("system");
        sysMessage.setCreateTime(DateUtil.date());
        OrmTemplate.save(sysMessage);
    }
}
