package com.zhy.test.dao.write;

import com.zhy.test.entity.CifCode;

public interface CifCodeMapper {
    int deleteByPrimaryKey(Integer SERIALNO);

    int insert(CifCode record);

    int insertSelective(CifCode record);

    CifCode selectByPrimaryKey(Integer SERIALNO);

    int updateByPrimaryKeySelective(CifCode record);

    int updateByPrimaryKeyWithBLOBs(CifCode record);

    int updateByPrimaryKey(CifCode record);
}