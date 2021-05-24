package com.example.test.entity;

import java.io.Serializable;

public class PageRequest implements Serializable {

    public final static int DEFAULT_PAGE_SIZE = 10;

    private static final long serialVersionUID = -1L;

    /**
     * 分页起始页索引，从0开始
     */
    private int pageIndex;

    /**
     * 分页每页记录数，默认值10
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 是否是导出请求
     */
    private int isExcel = 0;

    public PageRequest() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    public PageRequest(int pageSize) {
        this(0, pageSize);
    }

    public PageRequest(int pageIndex, int pageSize) {
        checkPageIndex(pageIndex);
        checkPageSize(pageSize);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", isExcel=" + isExcel +
                '}';
    }

    public PageRequest buildPageIndex(int pageIndex) {
        checkPageIndex(pageIndex);
        setPageIndex(pageIndex);
        return this;
    }

    public PageRequest buildPageSize(int pageSize) {
        checkPageSize(pageSize);
        setPageSize(pageSize);
        return this;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        checkPageIndex(pageIndex);
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        checkPageSize(pageSize);
        this.pageSize = pageSize;
    }

    private void checkPageIndex(int pageIndex) {
        if (pageIndex < 0) {
            throw new IndexOutOfBoundsException("The pageIndex should be than -1.");
        }
    }

    public int getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(int isExcel) {
        this.isExcel = isExcel;
    }

    private void checkPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IndexOutOfBoundsException("The pageSize should be than 0.");
        }
        if (isExcel != 1 && pageSize > 50) {
            throw new IndexOutOfBoundsException("The pageSize is too large.");
        }
    }

}
