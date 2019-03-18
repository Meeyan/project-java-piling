package com.std.gof23.L020_state;

import java.util.HashMap;
import java.util.Map;

/**
 * 投票管理器
 *
 * @author zhaojy
 * @date 2018-03-16
 */
public class VoteManager {
    private VoteState voteState;

    // 记录用户投票的结果，Map<String,String>对应Map<用户名称，投票的选项>
    private Map<String, String> mapVote = new HashMap<>();

    // 记录用户投票次数，Map<String,Integer>对应Map<用户名称，投票的次数>
    private Map<String, Integer> mapVoteCnt = new HashMap<>();

    public Map<String, String> getMapVote() {
        return mapVote;
    }

    public void vote(String user, String voteItem) {
        // 1.为该用户增加投票次数
        // 从记录中取出该用户已有的投票次数
        Integer voteCnt = mapVoteCnt.get(user);
        if (voteCnt == null) {
            voteCnt = 0;
        }

        voteCnt++;

        mapVoteCnt.put(user, voteCnt);

        // 2.判断该用户的投票类型，就相当于判断对应的状态到底是正常投票、重复投票、恶意投票还是上黑名单的状态
        if (voteCnt == 1) {
            voteState = new NormalVoteState();
        } else if (voteCnt > 1 && voteCnt < 5) {
            voteState = new RepeatVoteState();
        } else if (voteCnt >= 5 && voteCnt < 8) {
            voteState = new SpiteVoteState();
        } else {
            voteState = new BlackVoteState();
        }

        // 然后转调状态对象来进行相应的操作
        voteState.vote(user, voteItem, this);
    }
}
