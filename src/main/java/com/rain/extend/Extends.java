package com.rain.extend;

public class Extends {
    @SuppressWarnings("unused")
    private int i;
    private int s;
    protected int j;
    public int k;
    int l;
    protected void hello() {
        
    }
}

class ExtendsApple extends Extends {
    public void run() {
        System.out.println(this.j);
        System.out.println(this.k);
        System.out.println(this.l);
    }
}
