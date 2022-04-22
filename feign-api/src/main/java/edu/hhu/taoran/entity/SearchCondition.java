package edu.hhu.taoran.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {
    private Integer page;
    private Integer size;
    private String searchString;
    private String classify;
}
