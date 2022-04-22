package edu.hhu.taoran.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDataSet {
    private Stock stock;
    private Integer dataSetStatus;
}
