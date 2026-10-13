package com.fly.demo;

import cn.hutool.core.util.StrUtil;
import com.creatar.common.core.entity.BasePageQuery;
import com.github.pagehelper.PageHelper;

public class PageUtil {

    public static void start(BasePageQuery pageQuery) {
        PageHelper.clearPage();
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
        String orderBy = pageQuery.getOrderBy();
        if (StrUtil.isNotBlank(orderBy)) {
            orderBy = StringUtil.camelToUnderline(orderBy);
            String sort = pageQuery.getSort();
            if (StrUtil.isBlank(sort)) {
                orderBy = orderBy + " " + pageQuery.getDefaultSort();
            } else {
                orderBy = orderBy + " " + sort;
            }
            PageHelper.orderBy(orderBy);
        }
    }

    public static void startNoOrder(BasePageQuery pageQuery) {
        PageHelper.clearPage();
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
    }

    public static void startAndOrder(BasePageQuery pageQuery, String defaultOrderBy) {
        PageHelper.clearPage();
        pageQuery.setDefaultOrderBy(defaultOrderBy);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
        String orderBy = pageQuery.getOrderBy();
        if (StrUtil.isBlank(orderBy)) {
            orderBy = pageQuery.getDefaultOrderBy();
        }
        orderBy = StringUtil.camelToUnderline(orderBy);
        String sort = pageQuery.getSort();
        if (StrUtil.isBlank(sort)) {
            orderBy = orderBy + " " + pageQuery.getDefaultSort();
        } else {
            orderBy = orderBy + " " + sort;
        }
        PageHelper.orderBy(orderBy);
    }

    public static void startAndOrder(BasePageQuery pageQuery, String defaultOrderBy, String defaultSort) {
        PageHelper.clearPage();
        pageQuery.setDefaultOrderBy(defaultOrderBy);
        pageQuery.setDefaultSort(defaultSort);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
        String orderBy = pageQuery.getOrderBy();
        if (StrUtil.isBlank(orderBy)) {
            orderBy = defaultOrderBy;
        }
        orderBy = StringUtil.camelToUnderline(orderBy);
        String sort = pageQuery.getSort();
        if (StrUtil.isBlank(sort)) {
            orderBy = orderBy + " " + pageQuery.getDefaultSort();
        } else {
            orderBy = orderBy + " " + sort;
        }
        PageHelper.orderBy(orderBy);
    }

    public static void startAndOrders(BasePageQuery pageQuery,
                                      String defaultOrderBy,
                                      String defaultSort,
                                      String... orderBySorts) {
        PageHelper.clearPage();
        pageQuery.setDefaultOrderBy(defaultOrderBy);
        pageQuery.setDefaultSort(defaultSort);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
        StringBuilder orderBy = new StringBuilder();
        for (String orderBySort : orderBySorts) {
            orderBy.append(orderBySort).append(",");
        }
        orderBy.deleteCharAt(orderBy.length() - 1);
        if (StrUtil.isBlank(pageQuery.getOrderBy())) {
            orderBy.append(",").append(defaultOrderBy);
        } else {
            orderBy.append(",").append(pageQuery.getOrderBy());
        }
        orderBy = new StringBuilder(StringUtil.camelToUnderline(orderBy.toString()));
        if (StrUtil.isBlank(pageQuery.getSort())) {
            orderBy.append(" ").append(defaultSort);
        } else {
            orderBy.append(" ").append(pageQuery.getSort());
        }
        PageHelper.orderBy(orderBy.toString());
    }

}
