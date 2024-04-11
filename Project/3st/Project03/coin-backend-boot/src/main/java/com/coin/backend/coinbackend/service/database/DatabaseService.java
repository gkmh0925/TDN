package com.coin.backend.coinbackend.service.database;

import com.coin.backend.coinbackend.dao.DatabaseDao;
import com.coin.backend.coinbackend.dto.database.DatabaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired  //빈으로 등록된 객체를 참조하는 어노테이션 쉽게말해 sampleUserDao 사용할거다 이말임. 참조할놈들 빈 등록(어노테이션 등록) 안해주면 에러난다
    private DatabaseDao databaseDao;

    public List<DatabaseDto> selectUser() {   //SELECT * FROM user
        return databaseDao.selectUser();
    }

    public List<DatabaseDto> selectLogin(@RequestBody DatabaseDto databaseDto) {  //SELECT * FROM user WHERE UserId=? AND UserPw=?
        return databaseDao.selectLogin(databaseDto);
    }
}
