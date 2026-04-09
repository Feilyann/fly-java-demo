package com.fly.demo.base.entity.design_pattern.simple_factory;

public class SimpleCoffeeFactory {

    public Coffee createCoffee(String type) {
        Coffee coffee = null;

        if ("美式咖啡".equals(type)) {
            coffee = new AmericaCoffee();
        } else if ("拿铁咖啡".equals(type)) {
            coffee = new LatteCoffee();
        } else {
            throw new RuntimeException("对不起, 您当前点的咖啡没有");
        }
        coffee.addMilk();
        coffee.addSugar();
        return coffee;
    }
}
