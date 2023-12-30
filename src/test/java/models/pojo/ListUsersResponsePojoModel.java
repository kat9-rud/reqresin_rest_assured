package models.pojo;

import java.util.List;

public class ListUsersResponsePojoModel {
    int page;
    int per_page;
    int total;
    int total_pages;
    List<Object> data;
    Object support;

    public Object getSupport() {
        return support;
    }

    public void setSupport(Object support) {
        this.support = support;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
