package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private Integer totalPages;
    private Long totalElement;
    private Integer page;
    private Integer size;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
