package com.rain.reflect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Proxy {


    private static class UserServiceImpl {
        public UserServiceImpl() {}

        public void process() {
            System.out.println("process user");
        }
    }

    private static class UserProxy implements MethodInterceptor {
        // 被代理对象
        private Object obj;

        /**
         * 拦截所有的方法
         * o 被代理的对象
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before process user");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("after process user");
            return result;
        }

        public Object getInstanceOfProxy(Object obj) {
            this.obj = obj;
            // 设置被代理类的父类
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(obj.getClass());

            // 设置回调方法,个人觉得此处会改写方法的实现体(仅仅设置回调方法)
            enhancer.setCallback(this);

            // 返回代理对象
            return enhancer.create();
        }
    }

    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = (UserServiceImpl) new UserProxy().getInstanceOfProxy(new UserServiceImpl());
        System.out.println(userServiceImpl);
        userServiceImpl.process();
    }
}
