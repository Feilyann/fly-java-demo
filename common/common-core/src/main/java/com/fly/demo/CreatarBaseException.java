package com.fly.demo;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * @project: creatar-common
 * @package: com.creatar.common.core.exception
 * @author: feilongyan
 * @time: 2024/8/22 0022
 * @description: 基础异常类
 * @version: 1.0
 */
public abstract class CreatarBaseException extends RuntimeException {

    public CreatarBaseException() {
        this(null);
    }

    public CreatarBaseException(Throwable cause) {
        super(cause);
    }

    public CreatarBaseException(CharSequence template, Object... params) {
        super(CharSequenceUtil.format(template, params));
    }

    /**
     * 创建带消息和原因的异常
     *
     * @param message 异常消息
     * @param cause   原因异常
     */
    public CreatarBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
