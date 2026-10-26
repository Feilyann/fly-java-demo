package com.fly.demo.base.entity.design_pattern.abstract_factory;

import com.fly.demo.base.entity.design_pattern.simple_factory.Coffee;
import com.fly.demo.base.entity.design_pattern.simple_factory.LatteCoffee;

public class LatteCoffeeFactory implements CoffeeFactory {
    @Override
    public Coffee createCoffee() {
        return new LatteCoffee();
    }
}
