package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> data;
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;

    public PageResponse(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
    }
}
