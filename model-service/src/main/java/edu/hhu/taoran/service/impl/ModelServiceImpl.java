package edu.hhu.taoran.service.impl;

import edu.hhu.taoran.entity.ModelStatus;
import edu.hhu.taoran.mapper.ModelMapper;
import edu.hhu.taoran.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public ModelStatus selectModelStatusById(String stockId) {
        return modelMapper.selectModelStatusById(stockId);
    }

    @Override
    public void insertModel(String stockId) {
        modelMapper.insertModel(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void updateCloseStatus(String stockId) {
        modelMapper.updateCloseStatus(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void updateOpenStatus(String stockId) {
        modelMapper.updateOpenStatus(stockId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void updateDate(String stockId, String date) {
        modelMapper.updateDate(stockId,date);
    }


}
