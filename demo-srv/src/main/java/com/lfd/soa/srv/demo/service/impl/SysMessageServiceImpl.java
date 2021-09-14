package com.lfd.soa.srv.demo.service.impl;

import com.lfd.soa.srv.demo.bean.entity.SysMessage;
import com.lfd.soa.srv.demo.mapper.SysMessageMapper;
import com.lfd.soa.srv.demo.service.SysMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * mq消息表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-09-14
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements SysMessageService {

}
