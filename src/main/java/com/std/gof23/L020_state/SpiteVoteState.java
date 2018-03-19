package com.std.gof23.L020_state;

/**
 * 恶意刷票啊
 *
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class SpiteVoteState implements VoteState {
    @Override
    public void vote(String user, String voteItem, VoteManager voteManger) {
        String recItem = voteManger.getMapVote().get(user);
        if (recItem != null) {
            voteManger.getMapVote().remove(user);
        }
        System.out.println("您有恶意刷票行为，取消投票资格");
    }
}
