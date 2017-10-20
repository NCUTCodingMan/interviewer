package com.rain.thread;

/**
 * 非线程安全生成序列的方式
 * @author Administrator
 */
public class UnsafeSequence {
    private int i;

    public int getNext() {
        return i++;
    }
    
    public int getContent() {
        return this.i;
    }
    
    public static void main(String[] args) {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        JobA jobA = new JobA();
        jobA.setUnsafeSequence(unsafeSequence);
        JobB jobB = new JobB();
        jobB.setUnsafeSequence(unsafeSequence);
        
        while (true) {
            new Thread(jobA).start();
            new Thread(jobB).start();
        }
    }
}

class JobA implements Runnable {
    private UnsafeSequence unsafeSequence;
    public void setUnsafeSequence(UnsafeSequence unsafeSequence) {
        this.unsafeSequence = unsafeSequence;
    }
    @Override
    public void run() {
        this.unsafeSequence.getNext();
    }
}

class JobB implements Runnable {
    private UnsafeSequence unsafeSequence;
    public void setUnsafeSequence(UnsafeSequence unsafeSequence) {
        this.unsafeSequence = unsafeSequence;
    }
    @Override
    public void run() {
        this.unsafeSequence.getNext();
        System.out.println(unsafeSequence.getContent());
    }
}