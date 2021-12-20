package com.lfd.soa.demo.srv.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.bean.entity.SysMessage;
import com.lfd.soa.demo.srv.mapper.SysMessageMapper;
import com.lfd.soa.demo.srv.service.SysMessageService;
import com.lfd.soa.demo.srv.support.queue.GlobalQueueConfig;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * mq消息表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-04-25
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements SysMessageService {

    @Override
    public SysMessage getSysMessage(String uuid) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMessage::getUuid, uuid);
        return this.getOne(queryWrapper);
    }

    @Override
    public SysMessage saveMessage(String service, String queue, Map<String, Object> msgMap) {
        String uuid = UUID.randomUUID().toString();
        msgMap.put(GlobalQueueConfig.getConfig().getUuidName(), uuid);
        SysMessage sysMessage = SysMessage.builder()
                .uuid(uuid)
                .message(JsonUtil.toJson(msgMap))
                .service(service)
                .queue(queue)
                .type(RabbitServiceType.CONSUMER.name())
                .consumeState(0)
                .tryCount(0)
                .tryTime(DateUtil.date())
                .build();
        this.save(sysMessage);
        return sysMessage;
    }
}
