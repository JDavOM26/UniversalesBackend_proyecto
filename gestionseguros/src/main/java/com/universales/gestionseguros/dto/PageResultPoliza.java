package com.universales.gestionseguros.dto;

import java.util.List;

import lombok.Data;
@Data
public class PageResultPoliza<T> {
 private final List<T> content;
 private final long totalElements;
 private final int number;
 private final int size;
 private final int totalPages;
 private final boolean first;
 private final boolean last;
 private final boolean hasNext;
 private final boolean hasPrevious;

 public PageResultPoliza(List<T> content, long totalElements, int number, int size) {
     this.content = content;
     this.totalElements = totalElements;
     this.number = number;
     this.size = size;
     this.totalPages = size == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
     this.first = number == 0;
     this.last = number >= this.totalPages - 1;
     this.hasNext = number < this.totalPages - 1;
     this.hasPrevious = number > 0;
 }
 
}