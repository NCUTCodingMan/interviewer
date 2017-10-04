package com.rain.extend;

public class FinalOrOverride {
    public static void main(String[] args) {
        OverrideApple apple = new OverrideAppleExtends();
        apple.hello();
    }
}
/**
 * final可以重载父类中的方法
 * final并不会修改方法的归属,是属于实例的还是属于类的,但是static可以
 * */
class OverrideApple {
    public void hello() {
        System.out.println("father");
    }
//  public static void hello() {}
    public static void say() {
        
    }
}

class OverrideAppleExtends extends OverrideApple {
    @Override
    public final void hello() {
        System.out.println("son");
    }
    
    public final void hello(int i) {
        
    }
}