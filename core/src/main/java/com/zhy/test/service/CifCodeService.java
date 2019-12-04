package com.zhy.test.service;

import com.zhy.test.dao.write.CifCodeMapper;
import com.zhy.test.entity.CifCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CifCodeService {

    @Autowired
    private CifCodeMapper cifCodeMapper;

    public CifCode testSelect(int serialNo){
        CifCode cifCode = cifCodeMapper.selectByPrimaryKey(serialNo);
        return cifCode;
    }
}
