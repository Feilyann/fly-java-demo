package com.fly.demo.base.entity.design_pattern.abstract_factory;

import com.fly.demo.entity.design_pattern.simple_factory.Coffee;

public interface DessertFactory {

    Coffee createCoffee();

    Dessert createDessert();
}
