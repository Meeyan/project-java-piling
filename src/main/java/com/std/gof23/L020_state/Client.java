package com.std.gof23.L020_state;

/**
 * @author zhaojy
 * @date 2018-03-16
 */
public class Client {
    public static void main(String[] args) {

        VoteManager vm = new VoteManager();
        for (int i = 0; i < 9; i++) {
            vm.vote("u1", "A");
        }

    }
}
