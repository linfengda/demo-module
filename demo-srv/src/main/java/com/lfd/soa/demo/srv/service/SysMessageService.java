package com.lfd.soa.demo.srv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfd.soa.demo.srv.bean.entity.SysMessage;

import java.util.Map;

/**
 * <p>
 * mq消息表 服务类
 * </p>
 *
 * @author linfengda
 * @since 2021-09-14
 */
public interface SysMessageService extends IService<SysMessage> {

    /**
     * 根据uuid查询消息
     * @param uuid  uuid
     * @return      消息
     */
    SysMessage getSysMessage(String uuid);

    /**
     * 保存消息
     * @param service   服务
     * @param queue     队列
     * @param msgMap    消息
     * @return          消息
     */
    SysMessage saveMessage(String service, String queue, Map<String, Object> msgMap);
}
