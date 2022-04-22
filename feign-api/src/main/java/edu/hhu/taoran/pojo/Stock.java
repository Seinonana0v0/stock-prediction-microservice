package edu.hhu.taoran.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String id;
    private String name;
    private String industry = "";
    private String introduction = "";
}
