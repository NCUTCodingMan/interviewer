package com.rain.reflect;

public class Callback {
    public static void main(String[] args) {
        new SuperCaculator().add(1, 2, new Job() {
            @Override
            public void fill(int a, int b, int result) {
                System.out.println(a + b);
            }
        });
    }
}

class SuperCaculator {
    public void add(int a, int b, Job job) {
        int result =  a + b;
        job.fill(a, b, result);
    }
}

class Student {
    private String name;
    public Student(String name) {
        this.name = name;
    }
    public void add(int a, int b) {
        new SuperCaculator().add(a, b, new Caculator());
    }

    private static class Caculator implements Job {
        @Override
        public void fill(int a, int b, int result) {
            System.out.println("学生:" + a + "" + b + "" + result);
        }
    }
}

class Granpa {
    private String name;
    public Granpa(String name) {
        this.name = name;
    }

    public void add(int a, int b) {
        new SuperCaculator().add(a, b, new Caculator());
    }

    private static class Caculator implements Job {
        @Override
        public void fill(int a, int b, int result) {
            System.out.println("奶奶:" + a + "" + b + "" + result);
        }
    }
}

interface Job {
    public abstract void fill(int a, int b, int result);
}