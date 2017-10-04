package com.rain.method;

public class Final {
    public static void main(String[] args) {
        FinalApple finalApple = new FinalApple(1, "scott");
        final String s = "inner class";
        finalApple.useAnonymousClass("HelloWorld", new AnonymousInterface() {
            private String content = s;
            @Override
            public void method(String s, Integer i) {
                this.content = s;
                System.out.println(content);
            }
        });
    }
}

class FinalApple{
    /**
     *  final类型的初始化,可以立即初始化,也可以延迟到构造方法中初始化.
     *  但是不管怎么样,使用之前必须初始化
     *  1.对于值类型而言,其值不可改变
     *  2.final修饰的方法子类不能覆盖,但是可以被继承
     *      使用final修饰的方法代表该方法已经足够完善了,不需要子类进行扩展.
     *      因此,final修饰的方法不可改变.final方法比非final方法快,因为
     *      在编译期间已经静态绑定了(Java基于动态绑定)
     *  3.final修饰的类不可被继承.
     *      隐性地,final类中声明变量不是final类型的,可以通过set方法修改
     *      final类中声明的类是final类型的,不可被覆盖(也没记会覆盖)
     *
     */
    private final int i;
    private final FinalUser user;    
    public FinalApple(int i, String name) {
        this.i = i;
        this.user = new FinalUser(name);
    }
    @Override
    public String toString() {
        return "FinalApple [i=" + i + ", user=" + user + "]";
    }
    
    public final void run() {
        System.out.println("...");
    }
    
    public void useAnonymousClass(String s, AnonymousInterface anonymousInterface) {
        System.out.println(s + anonymousInterface);
    }
}

class FinalRedApple extends FinalApple {
    public FinalRedApple(int i, String name) {
        super(i, name);
    }
    
    /*
        public void run() {
            
        }
    */
}

final class FinalClass{
    private Integer a;
    private String b;
    private FinalUser user = new FinalUser("scott");
    public void method() {
        
    }
    public void setUser(FinalUser user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "FinalClass [a=" + a + ", b=" + b + ", user=" + user + "]";
    }
}

class FinalUser{
    private String name;
    public FinalUser(String name) {
        
    }
    @Override
    public String toString() {
        return "FinalUser [name=" + name + "]";
    }
}

interface AnonymousInterface {
    public abstract void method(String s, Integer i);
}