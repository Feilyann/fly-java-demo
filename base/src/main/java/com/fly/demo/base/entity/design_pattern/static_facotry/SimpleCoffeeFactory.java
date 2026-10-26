package com.fly.demo.base.entity.design_pattern.static_facotry;

import com.fly.demo.base.entity.design_pattern.simple_factory.AmericaCoffee;
import com.fly.demo.base.entity.design_pattern.simple_factory.Coffee;
import com.fly.demo.base.entity.design_pattern.simple_factory.LatteCoffee;

public class SimpleCoffeeFactory {

    public static Coffee createCoffee(String type) {
        Coffee coffee = null;

        if ("美式咖啡".equals(type)) {
            coffee = new AmericaCoffee();
        } else if ("拿铁咖啡".equals(type)) {
            coffee = new LatteCoffee();
        } else {
            throw new RuntimeException("对不起, 您当前点的咖啡没有");
        }

        return coffee;
    }
}
