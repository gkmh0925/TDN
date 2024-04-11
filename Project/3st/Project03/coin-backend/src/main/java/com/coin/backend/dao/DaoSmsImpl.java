package com.coin.backend.dao;

import com.coin.backend.dto.DtoSms;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoSmsImpl implements DaoSms{

    private static final String NAME_SPACE = "com.coin.backend.mapper.SmsMapper";

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<DtoSms> selectKey(){
        return sqlSession.selectList(NAME_SPACE + ".smsList");
    }
}
