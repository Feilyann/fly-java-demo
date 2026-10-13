package com.fly.demo.base.entity.design_pattern.simple_factory;

public class CoffeeStore {

    public Coffee orderCoffee(String type) {
        SimpleCoffeeFactory factory = new SimpleCoffeeFactory();
        return factory.createCoffee(type);
     }
}
