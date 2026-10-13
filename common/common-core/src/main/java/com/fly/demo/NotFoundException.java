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
public class NotFoundException extends CreatarBaseException{

    public NotFoundException() {
        this(null);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(CharSequence template, Object... params) {
        super(CharSequenceUtil.format(template, params));
    }
}
