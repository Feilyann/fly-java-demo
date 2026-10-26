package com.fly.demo.base.entity.design_pattern.abstract_factory;

import com.fly.demo.base.entity.design_pattern.simple_factory.AmericaCoffee;
import com.fly.demo.base.entity.design_pattern.simple_factory.Coffee;

/*
* 美式风味工厂
* */
public class AmericanDessertFactory implements DessertFactory {
    @Override
    public Coffee createCoffee() {
        return new AmericaCoffee();
    }

    @Override
    public Dessert createDessert() {
        return new MatchaMouse();
    }
}
