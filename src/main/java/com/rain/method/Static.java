package com.rain.method;

public class Static {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        StaticRedApple redApple = new StaticRedApple();
        System.out.println(redApple.eat);
        StaticApple apple = new StaticRedApple();
        System.out.println(apple.eat);
    }
}

/**
 * static
 *  static代表着静态,共享.static可以用来修饰成员变量,成员方式,类(仅仅针对内部类).
 *  1.局部变量为啥不能声明为static?
 *      局部变量,存活期仅仅存在于方法中,方法执行完毕,变量就被撤销了.
 *  2.static成员变量与方法属于所有对象共享的,在了解类加载过程的基础上.会在方法区初始化静态变量,
 *      整个过程中只维护一份引用,修改了其值对任何一个对象而言都改变了.这一点是静态变量与成员变量
 *      最大的区别.
 *  3.使用static也会存在一定的局限性,如静态方法只能访问静态变量.
 *  4.类的构造方法其实也是static final类型的
 * */
class StaticApple {
    public static String eat;
    @SuppressWarnings("unused")
    private Integer count = 1;
    static {
        eat = "eat";
    }
    public static void run() {
        
    }
    public static class InnerStaticClass {
        private Integer count;
        private String desc;
        public InnerStaticClass(){
            
        }
        public InnerStaticClass(Integer count, String desc) {
            this.count = count;
            this.desc = desc;
        }
        public Integer getCount() {
            return count;
        }
        public void setCount(Integer count) {
            this.count = count;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

class StaticRedApple extends StaticApple {
    public static String eat;
    static {
        eat = "no eat";
    }
}