package edu.hhu.taoran.service.impl;

import edu.hhu.taoran.mapper.DataSetMapper;
import edu.hhu.taoran.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataSetServiceImpl implements DataSetService {
    @Autowired
    private DataSetMapper dataSetMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer selectDataSetStatusById(String stockId) {
        return dataSetMapper.selectDataSetStatusById(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void insertWithOutDataSet(String stockId) {
        dataSetMapper.insertWithOutDataSet(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void insertWithDataSet(String stockId) {
        dataSetMapper.insertWithDataSet(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void uploadDataSet(String stockId) {
        dataSetMapper.uploadDataSet(stockId);
    }
}
