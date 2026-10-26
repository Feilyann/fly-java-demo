package com.fly.demo.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.creatar.common.core.exception.CreatarBaseException;

public class BusinessException extends CreatarBaseException {

    public BusinessException() {
        this(null);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(CharSequence template, Object... params) {
        super(CharSequenceUtil.format(template, params));
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
