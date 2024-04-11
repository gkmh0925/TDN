package com.coin.backend.dao;

import com.coin.backend.dto.DtoNoticeBoard;
import com.coin.backend.dto.DtoSms;

import java.util.List;

public interface DaoSms {
    List<DtoSms> selectKey();

}
