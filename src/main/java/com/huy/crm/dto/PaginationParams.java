package com.huy.crm.dto;


import lombok.Setter;

public class PaginationParams {
    @Setter
    private int page;

    private int pageSize;

    public int getPage() {
        if (page <= 0) {
            return 1;
        }

        return page;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            return 5;
        }

        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            this.pageSize = 5;
            return;
        }

        if (pageSize > 50) {
            this.pageSize = 50;
            return;
        }

        this.pageSize = pageSize;
    }
}
