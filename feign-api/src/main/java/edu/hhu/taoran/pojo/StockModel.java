package edu.hhu.taoran.pojo;

import edu.hhu.taoran.entity.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockModel {
    private Stock stock;
    private ModelStatus modelStatus;
}
