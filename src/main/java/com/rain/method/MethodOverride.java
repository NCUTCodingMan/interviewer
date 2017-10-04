package com.rain.method;

import com.rain.extend.Extends;

/**
 * 覆盖中final与static的限制
 *  1.子类不能覆盖父类中final修饰的方法
 *  
 * */
public class MethodOverride {
    public static void main(String[] args) {
        UpperClass down = new DownClass();
        down.run();
        
    }
}

class UpperClass extends Extends {
    public final void run() {
        System.out.println("anything...");
    }
    public void ss() {
        this.j = 0;
        System.out.println(new Extends().k);
//      System.out.println(new Extends().j);
    }
} 

class DownClass extends UpperClass {
    /*
    public void run() {
        System.out.println("nothing...");
    }
    */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
}