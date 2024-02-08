package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResultDTO<T>{

    private Long totalSize;
    private List<T>list;

    public PaginationResultDTO(Long totalSize, List<T> list) {
        this.totalSize = totalSize;
        this.list = list;
    }
}
