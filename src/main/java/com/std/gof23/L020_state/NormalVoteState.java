package com.std.gof23.L020_state;

/**
 *
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class NormalVoteState implements VoteState {
    @Override
    public void vote(String user, String voteItem, VoteManager voteManger) {
        voteManger.getMapVote().put(user, voteItem);
        System.out.println("恭喜投票成功");
    }
}
