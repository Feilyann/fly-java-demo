package com.fly.demo.entity;

import cn.hutool.core.util.StrUtil;
import com.creatar.common.core.util.StringUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNum = 1;

    private int pageSize = 10;

    private String defaultOrderBy = "create_time";

    private String defaultSort = "desc";

    private String orderBy;

    private String sort;

    public void setOrderBy(String orderBy) {
        this.orderBy = StringUtil.camelToUnderline(orderBy);
    }

    public String getOrderBy() {
        if (StrUtil.isBlank(orderBy)) {
            return defaultOrderBy;
        }
        return orderBy;
    }

    public String getSort() {
        if (StrUtil.isBlank(sort)) {
            return defaultSort;
        }
        return sort;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum <= 0 ? 1 : (Math.min(pageNum, 10000000)) ;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 1 : Math.min(pageSize, 1000);
    }

    public void setSort(String sort) {
        if (StrUtil.isNotBlank(sort)) {
            if (!("asc".equalsIgnoreCase(sort) || "desc".equalsIgnoreCase(sort))) {
                sort = "asc";
            }
            this.sort = sort;
        }
    }
}
