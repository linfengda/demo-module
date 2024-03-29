package com.lfd.soa.demo.srv.support.statemachine;

import java.util.List;

/**
 * 状态机抽象
 * @author linfengda
 * @date 2020-11-08 23:55
 */
public interface StateMachine<S, E> {

    /**
     * 初始化状态机状态
     * @param state     初始状态
     * @return          状态机对象
     */
    StateMachine<S, E> initState(S state);

    /**
     * 构建状态机
     * @param source    开始状态
     * @param target    结束状态
     * @param event     触发事件
     * @return          状态机对象
     */
    StateMachine<S, E> build(S source, S target, E event);

    /**
     * 构建状态机
     * @param sources   开始状态列表
     * @param target    结束状态
     * @param event     触发事件
     * @return          状态机对象
     */
    StateMachine<S, E> build(List<S> sources, S target, E event);

    /**
     * 触发状态机事件
     * @param event     触发事件
     * @return          true：状态机成功，false：状态机失败
     */
    boolean sendEvent(E event);

    /**
     * 获取下一个状态
     * @return  如果状态机校验不通过，返回null
     */
    S getTargetState();
}
