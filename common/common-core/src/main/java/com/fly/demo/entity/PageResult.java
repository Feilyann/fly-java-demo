package com.fly.demo.entity;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private int pageNum;

    private int pageSize;

    private int pages;

    private int total;

    private List<T> list;

    public static <T> PageResult<T> pack(List<T> list) {
        return pack(new PageInfo<>(list));
    }

    public static <T> PageResult<T> pack(PageInfo<T> pageInfo) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setTotal((int) pageInfo.getTotal());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }

    public static <T> PageResult<T> createNullPage(int pageNum, int pageSize) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setList(Collections.emptyList());
        return pageResult;
    }

    public static <T> PageResult<T> createNullPage(BasePageQuery pageQuery) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageQuery.getPageNum());
        pageResult.setPageSize(pageQuery.getPageSize());
        pageResult.setList(Collections.emptyList());
        return pageResult;
    }
}
