package com.fly.demo;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @project: creatar-common
 * @package: com.creatar.common.core.exception
 * @author: feilongyan
 * @time: 2024/8/22 0022
 * @description: TODO
 * @version: 1.0
 */
public class IllegalArgumentException extends CreatarBaseException{

    public IllegalArgumentException() {
        this(null);
    }

    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentException(CharSequence template, Object... params) {
        super(CharSequenceUtil.format(template, params));
    }
}
