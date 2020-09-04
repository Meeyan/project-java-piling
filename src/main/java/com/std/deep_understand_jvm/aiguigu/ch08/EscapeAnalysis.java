package com.std.deep_understand_jvm.aiguigu.ch08;

/**
 * 逃逸分析举例
 * 如何快速的判断是否发生了逃逸分析？就看 new 的对象实体是否有可能在方法外被调用
 * @author zhaojy
 * @date 2020/9/4 4:28 下午
 */
public class EscapeAnalysis {
    public EscapeAnalysis obj;

    /**
     * 方法返回  EscapeAnalysis 对象，发生逃逸
     *
     * @return EscapeAnalysis
     */
    public EscapeAnalysis getInstance() {
        return obj == null ? new EscapeAnalysis() : obj;
    }

    /**
     * 为成员属性复制，发生逃逸
     */
    public void setObj() {
        this.obj = new EscapeAnalysis();
    }

    /**
     * 对象的作用域在当前方法中有效，没有发生逃逸
     */
    public void useEscapeAnalysis() {
        EscapeAnalysis is = new EscapeAnalysis();
    }

    /**
     * 引用成员变量，发生逃逸
     */
    public void useEscapeAnalysis2() {
        EscapeAnalysis instance = getInstance();
        // getInstance().xxx 同样会发生逃逸
    }

}
