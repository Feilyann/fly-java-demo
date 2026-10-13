package com.fly.demo.base.test.pattern.decorator;

/**
 * @project: fly-demo
 * @package: com.fly.demo.test.pattern.decorator
 * @author: feilongyan
 * @time: 2024/11/19 0019
 * @description: TODO
 * @version: 1.0
 */
public class DataSetClass {

    protected DataSource dataSource;

    protected Operator operator;

    protected DataSetClass(DataSource dataSource, Operator operator) {
        this.dataSource = dataSource;
        this.operator = operator;
    }
}
