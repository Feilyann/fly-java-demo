package com.fly.demo.base.entity.design_pattern.abstract_factory;

import com.fly.demo.base.entity.design_pattern.simple_factory.AmericaCoffee;
import com.fly.demo.base.entity.design_pattern.simple_factory.Coffee;

public class AmericanCoffeeFactory implements CoffeeFactory {

    @Override
    public Coffee createCoffee() {
        return new AmericaCoffee();
    }
}
