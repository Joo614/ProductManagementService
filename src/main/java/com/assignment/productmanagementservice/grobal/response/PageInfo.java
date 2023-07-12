package com.assignment.productmanagementservice.grobal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;


// TODO 검증용
@AllArgsConstructor
@Getter
public class PageInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static PageInfo of(Page<?> obj, int page, int size) {
        return new PageInfo(page, size, (int)obj.getTotalElements(), obj.getTotalPages());
    }
}
