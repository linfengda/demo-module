package com.lfd.soa.demo.srv.support.statemachine;



import com.lfd.soa.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态机实现
 * @author linfengda
 * @date 2020-11-08 23:58
 */
public class GenericStateMachine<S, E> implements StateMachine<S, E> {
    /**
     * 状态机初始状态
     */
    private S initialState;
    /**
     * 状态机下一状态
     */
    private S targetState;
    /**
     * 状态机描述列表
     */
    private List<StateEvent<S, E>> stateEvents = new ArrayList<>();



    @Override
    public StateMachine<S, E> initState(S state) {
        this.initialState = state;
        return this;
    }

    @Override
    public StateMachine<S, E> build(S source, S target, E event) {
        StateEvent<S, E> stateEvent = new StateEvent<>(source, target, event);
        checkConflictStateEvent(stateEvent);
        stateEvents.add(stateEvent);
        return this;
    }

    @Override
    public StateMachine<S, E> build(List<S> sources, S target, E event) {
        for (S source : sources) {
            StateEvent<S, E> stateEvent = new StateEvent<>(source, target, event);
            checkConflictStateEvent(stateEvent);
            stateEvents.add(stateEvent);
        }
        return this;
    }

    /**
     * 检查状态冲突
     * @param se    状态事件
     */
    private void checkConflictStateEvent(StateEvent<S, E> se) {
        for (StateEvent<S, E> stateEvent : stateEvents) {
            if (se.getEvent().equals(stateEvent.getEvent())) {
                if (se.getSource() == stateEvent.getSource()) {
                    throw new com.lfd.soa.common.exception.BusinessException("状态机状态冲突，存在事件[" + stateEvent.getEvent() + "]可以触发状态[" + stateEvent.getSource() + "]变更到[" + stateEvent.getTarget() + "]");
                }
            }
        }
    }

    @Override
    public boolean sendEvent(E event) {
        if (null == this.initialState) {
            throw new BusinessException("未初始化状态机状态！");
        }
        if (null == event) {
            throw new BusinessException("触发事件为空！");
        }
        for (StateEvent<S, E> stateEvent : stateEvents) {
            if (event.equals(stateEvent.getEvent())) {
                if (this.initialState == stateEvent.getSource()) {
                    this.targetState = stateEvent.getTarget();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public S getTargetState() {
        return this.targetState;
    }
}
