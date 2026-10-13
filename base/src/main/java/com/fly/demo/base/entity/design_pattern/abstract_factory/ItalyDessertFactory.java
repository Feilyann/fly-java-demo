package com.fly.demo.base.entity.design_pattern.abstract_factory;

import com.fly.demo.entity.design_pattern.simple_factory.Coffee;
import com.fly.demo.entity.design_pattern.simple_factory.LatteCoffee;

public class ItalyDessertFactory implements DessertFactory {
    @Override
    public Coffee createCoffee() {
        return new LatteCoffee();
    }

    @Override
    public Dessert createDessert() {
        return new Tramisu();
    }
}
