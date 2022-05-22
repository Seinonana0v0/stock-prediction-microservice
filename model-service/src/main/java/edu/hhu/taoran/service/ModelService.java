package edu.hhu.taoran.service;

import edu.hhu.taoran.entity.ModelStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ModelService {
    ModelStatus selectModelStatusById(String stockId);
    void insertModel(String stockId);
    void updateCloseStatus( String stockId);
    void updateOpenStatus(String stockId);
    void updateDate( String stockId,String date);
}
