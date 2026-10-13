package com.fly.demo.base.test.reflect;

import com.fly.demo.entity.People;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.Arrays;

@Slf4j
public class ReflectDemo {

    public static void main(String[] args) {
        testReflect();
    }

    public static void testReflect() {
        try {
            //1. 获取class对象的三种方式
            //方式一
            Class<?>  aClass = People.class;
            //方式二
    //        try {
    //            aClass = Class.forName("com.fly.demo.entity.People");
    //        } catch (ClassNotFoundException e) {
    //            e.printStackTrace();
    //        }
            //方式三
    //        People people = new People();
    //        aClass = people.getClass();
            log.info("class name: {}", aClass.getName());
            log.info("class simple name: {}", aClass.getSimpleName());
            log.info("class super: {}", aClass.getSuperclass());
            //2. 根据class对象获取构造方法
            //只能获取public修饰的构造方法
    //        Constructor[] constructors = aClass.getConstructors();
            aClass.getDeclaredConstructor();
            //可以获取所有的构造方法
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                log.info("class constructor name: {}", constructor.getName());
                log.info("class modifier size: {}", Modifier.toString(constructor.getModifiers()));
                log.info("class parameter types: {}", Arrays.toString(constructor.getParameterTypes()));
            }
            //获取无参构造函数
    //        try {
    //            Constructor<?> constructor = aClass.getConstructor();
    //        } catch (NoSuchMethodException e) {
    //            e.printStackTrace();
    //        }

            //3. 根据反射创建对象

            //方式一, 是通过无参构造创建的对象
            People people = (People)aClass.newInstance();
            //方式二, 可以指定对应参数的构造函数创建对象
//            People people2 = (People)aClass.getConstructor().newInstance();

            //4. 获取属性
            //方式一, 只能获取public属性
//            Field[] fields = aClass.getFields();
            //方式二, 可以获取所有属性
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                log.info(declaredField.getName());
            }
            Field peopleId = aClass.getDeclaredField("peopleId");
            //私有属性无法直接操作, 需要暴力破解
            peopleId.setAccessible(true);
            peopleId.set(people, "123456");
            log.info("people id: {}", peopleId.get(people));

            //5. 获取方法
            Method[] declaredMethods = aClass.getDeclaredMethods();
            Method declaredMethod = aClass.getDeclaredMethod("setPeopleId", String.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(people, "987654");
            log.info("people id: {}", peopleId.get(people));
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
