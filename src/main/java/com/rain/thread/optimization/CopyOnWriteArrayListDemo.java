package com.rain.thread.optimization;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 免锁容器 CopyOnWriteArrayList
 *  在修改容器时, 可以读容器的内容, 即读取与修改操作可以同时发生
 *  修改是在容器数据结构的某个部分的一个单独副本上执行的, 并且这个副本在修改过程中是不可视的
 *  只有当修改完成时, 被修改的机构才会与主数据结构交换, 之后的读取才可以看到这个修改
 *
 *  任何修改完成以前, 任何读取这人就不能看到他们
 *
 * @author rain
 * created on 2018/4/29
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        CopyOnWriteArrayList<Entity> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(new Entity("scott"));

        executorService.execute(new Modify(copyOnWriteArrayList));
        executorService.execute(new Read(copyOnWriteArrayList));

        TimeUnit.SECONDS.sleep(11);

        executorService.shutdownNow();
    }

    static class Modify implements Runnable {
        private CopyOnWriteArrayList<Entity> list;

        private Modify(CopyOnWriteArrayList<Entity> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (!list.isEmpty()) {
                    Entity entity = list.get(list.size() - 1);
                    entity.setName("rain");
                }
            }
        }
    }

    static class Read implements Runnable {
        private CopyOnWriteArrayList<Entity> list;

        private Read(CopyOnWriteArrayList<Entity> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (!list.isEmpty()) {
                    Entity entity = list.get(list.size() - 1);
                    System.out.println(entity);
                }
            }
        }
    }
}

class Entity {
    private String name;

    Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}