package testspringcloud.data.mybatis.write;

import testspringcloud.data.mybatis.entity.CifCode;

public interface CifCodeMapper {
    int deleteByPrimaryKey(Integer SERIALNO);

    int insert(CifCode record);

    int insertSelective(CifCode record);

    CifCode selectByPrimaryKey(Integer SERIALNO);

    int updateByPrimaryKeySelective(CifCode record);

    int updateByPrimaryKeyWithBLOBs(CifCode record);

    int updateByPrimaryKey(CifCode record);
}