package com.std.gof23.L020_state;

/**
 * 投票状态
 *
 * @author zhaojy
 * @create-time 2018-03-16
 */
public interface VoteState {
    /**
     * 投票管理
     *
     * @param user
     * @param voteItem
     * @param voteManger
     */
    public void vote(String user, String voteItem, VoteManager voteManger);
}
