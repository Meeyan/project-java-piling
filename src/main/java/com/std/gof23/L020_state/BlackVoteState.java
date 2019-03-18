package com.std.gof23.L020_state;

/**
 * 黑名单状态
 *
 * @author zhaojy
 * @date 2018-03-16
 */
public class BlackVoteState implements VoteState {

    @Override
    public void vote(String user, String voteItem, VoteManager voteManger) {
        // 记录黑名单中，禁止登录系统
        System.out.println("进入黑名单，将禁止登陆和使用本系统");
    }

}