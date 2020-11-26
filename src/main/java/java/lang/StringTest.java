package java.lang;

/**
 * 模拟类加载的双亲委派机制
 *
 * @author zhaojy
 * @date 2020/7/25 22:53
 */
public class StringTest {

    /**
     * 直接运行 main 方法会报错，为什么？
     * 1. 类加载的双亲委派机制下， String 类 是由 Bootstrap ClassLoader 加载的。也就是加载的是 JDK lang 包下的 String 类
     * 执行 main 方法时，在jdk lang 包下的 String 类中，没有 main 方法，所以报错
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        System.out.println("hello String!");
        String name = "abc";
        String lisi = "abc";
        System.out.println(name == lisi);
    }
}
