package com.fly.demo.base.test.pattern.decorator;

/**
 * @project: fly-demo
 * @package: com.fly.demo.test.pattern.decorator
 * @author: feilongyan
 * @time: 2024/11/19 0019
 * @description: TODO
 * @version: 1.0
 */
public abstract class DataSource {

    protected final Operator operator;

    protected DataSource(Operator operator) {
        this.operator = operator;
    }

    public void createDataSet() {
        operator.createFeatureDataSet();
    }
}
