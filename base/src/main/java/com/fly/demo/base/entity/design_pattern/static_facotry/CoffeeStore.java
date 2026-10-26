package com.fly.demo.base.entity.design_pattern.static_facotry;

import com.fly.demo.base.entity.design_pattern.simple_factory.Coffee;

public class CoffeeStore {

    public Coffee orderCoffee(String type) {
        Coffee coffee = SimpleCoffeeFactory.createCoffee(type);
        coffee.addMilk();
        coffee.addSugar();
        return coffee;
    }
}
