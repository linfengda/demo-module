package com.lfd.soa.demo.srv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfd.soa.demo.srv.bean.req.UserUpdateReq;
import com.lfd.soa.demo.srv.bean.entity.SysUser;
import com.lfd.soa.demo.srv.bean.resp.UserResp;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询部门下的所有用户
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    Set<UserResp> getDepartmentUserList(Integer departmentId);

    /**
     * 查询团队下的所有用户
     * @param teamId    团队id
     * @return
     * @throws Exception
     */
    List<UserResp> getTeamUserList(Integer teamId);

    /**
     * 查询用户信息
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    UserResp getUserInfo(Integer userId);

    /**
     * 更新用户信息
     * @param userId
     * @param userUpdateReq
     * @return
     * @throws Exception
     */
    UserResp updateUserInfo(Integer userId, UserUpdateReq userUpdateReq);
}
