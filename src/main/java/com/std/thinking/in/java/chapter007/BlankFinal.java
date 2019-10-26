package com.std.thinking.in.java.chapter007;

/**
 * 空白final
 * <p>
 * 空白final数据（引用）在类初始化前必须被初始化。
 *
 * @author zhaojy
 * @date 2019/10/26 12:04 PM
 */
public class BlankFinal {

    // final域已经初始化
    private final int i = 0;

    // 空白final
    private final int j;

    private final Poppet poppet;

    /**
     * 初始化
     */
    public BlankFinal() {
        j = 10;
        poppet = new Poppet(2);
    }

    /**
     * 初始化
     *
     * @param j
     */
    public BlankFinal(int j) {
        this.j = j;
        poppet = new Poppet(j);
    }

    public static void main(String[] args) {
        BlankFinal bf = new BlankFinal();
        System.out.println(bf);
    }


}

class Poppet {
    private int i;

    public Poppet(int i) {
        this.i = i;
    }
}