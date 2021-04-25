package com.lfd.soa.srv.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.srv.demo.bean.entity.SysMessage;
import com.lfd.soa.srv.demo.mapper.SysMessageMapper;
import com.lfd.soa.srv.demo.service.SysMessageService;
import com.lfd.soa.srv.demo.support.queue.bean.Constants;
import com.lfd.soa.srv.demo.support.queue.bean.RabbitServiceType;
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
    public SysMessage initSysMessage(String service, String queue, Map<String, Object> msgMap) {
        if (msgMap.containsKey(Constants.MESSAGE_UUID)) {
            return this.getSysMessage(String.valueOf(msgMap.get(Constants.MESSAGE_UUID)));
        } else {
            return this.saveMessage(service, queue, msgMap);
        }
    }

    @Override
    public SysMessage getSysMessage(String uuid) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMessage::getUuid, uuid);
        return this.getOne(queryWrapper);
    }

    @Override
    public SysMessage saveMessage(String service, String queue, Map<String, Object> msgMap) {
        String uuid = UUID.randomUUID().toString();
        msgMap.put(Constants.MESSAGE_UUID, uuid);
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
