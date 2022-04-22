package edu.hhu.taoran.service;



public interface DataSetService {
    Integer selectDataSetStatusById(String stockId);
    void insertWithOutDataSet(String stockId);
    void insertWithDataSet(String stockId);
    void uploadDataSet(String stockId);
}
