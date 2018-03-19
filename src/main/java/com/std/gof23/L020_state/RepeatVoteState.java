package com.std.gof23.L020_state;

/**
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class RepeatVoteState implements VoteState {
    @Override
    public void vote(String user, String voteItem, VoteManager voteManger) {
        System.out.println("请不要重复投票");
    }
}
