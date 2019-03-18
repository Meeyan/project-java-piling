package com.std.test;

/**
 * 测试静态内部类:不依赖外部类的实例【关键就是这一点】
 *
 * @author zhaojy
 * @date 2018-01-17
 */
public class TestStaticInnerClass {

    public static class Person {
        private String name;
        private String address;

        public Person(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static void main(String[] args) {
        Person person = new Person("lisi", "北京市");
        person.setName("王武");
        System.out.println(person.getName());
    }
}
