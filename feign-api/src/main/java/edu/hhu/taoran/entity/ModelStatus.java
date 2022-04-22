package edu.hhu.taoran.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelStatus {
    private Integer openStatus;
    private Integer closeStatus;
}
