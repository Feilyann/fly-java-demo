package com.fly.demo.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.creatar.common.core.exception.CreatarBaseException;

/**
 * 无权限访问异常
 *
 * @see CreatarBaseException
 */
public class AccessDeniedException extends CreatarBaseException {

    public AccessDeniedException() {
        this(null);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(CharSequence template, Object... params) {
        super(CharSequenceUtil.format(template, params));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
