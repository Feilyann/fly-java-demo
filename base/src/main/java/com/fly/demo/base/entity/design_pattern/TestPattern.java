package com.fly.demo.base.entity.design_pattern;

import com.fly.demo.entity.design_pattern.abstract_factory.AmericanCoffeeFactory;
import com.fly.demo.entity.design_pattern.simple_factory.Coffee;
import com.fly.demo.entity.design_pattern.simple_factory.CoffeeStore;
import com.fly.demo.entity.design_pattern.single.SingletonHungry1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPattern {

    public static void main(String[] args) {
        testAbstractFactory();
    }

    public static void testSingle() {
        SingletonHungry1 singleton = SingletonHungry1.getInstance();

        SingletonHungry1 singletonHungery1 = SingletonHungry1.getInstance();

        System.out.println(singleton == singletonHungery1);
    }

    public static void testFactory() {
        CoffeeStore coffeeStore = new CoffeeStore();
        Coffee coffee = coffeeStore.orderCoffee("美式咖啡");

        log.info("您的 {}", coffee.getName());
    }

    public static void testAbstractFactory() {
        com.fly.demo.entity.design_pattern.abstract_factory.CoffeeStore coffeeStore = new com.fly.demo.entity.design_pattern.abstract_factory.CoffeeStore();
        coffeeStore.setCoffeeFactory(new AmericanCoffeeFactory());
        Coffee coffee = coffeeStore.orderCoffee();
        log.info("您的咖啡 {}", coffee.getName());
    }
}
