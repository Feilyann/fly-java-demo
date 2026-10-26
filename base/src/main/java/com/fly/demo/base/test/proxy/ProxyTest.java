package com.fly.demo.base.test.proxy;

import com.fly.demo.base.test.proxy.cglib.CglibTarget;
import com.fly.demo.base.test.proxy.jdk.Advance;
import com.fly.demo.base.test.proxy.jdk.TargetImpl;
import com.fly.demo.base.test.proxy.jdk.Target;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        cglibProxyTest();
    }

    public static void jdkProxyTest() {
        Target target = new TargetImpl();
        Target targetProxy = (Target) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), (proxy, method, args) -> {
                    Advance.before();
                    Object result = method.invoke(target, args);
                    Advance.after();
                    return result;
                });
        targetProxy.save("JDK动态代理测试");
    }

    public static void cglibProxyTest() {
        CglibTarget target = new CglibTarget();
        CglibTarget targetProxy = (CglibTarget)Enhancer.create(target.getClass(),
                (MethodInterceptor) (o, method, objects, methodProxy) -> {
                    Advance.before();
                    method.invoke(target, objects);
                    Advance.after();
                    return null;
        });
        targetProxy.save();
    }
}
