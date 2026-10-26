package com.fly.demo.base.pattern.decorator;

/**
 * @project: fly-demo
 * @package: com.fly.demo.test.pattern.decorator
 * @author: feilongyan
 * @time: 2024/11/20 0020
 * @description: TODO
 * @version: 1.0
 */
public class PGDataSource extends DataSource{

    protected PGDataSource(Operator operator) {
        super(operator);
    }
}
