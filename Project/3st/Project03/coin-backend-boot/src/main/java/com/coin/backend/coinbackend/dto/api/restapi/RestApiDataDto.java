package com.coin.backend.coinbackend.dto.api.restapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiDataDto {
    private Integer standardTime;
    private String marketPrice;
    private String closingPrice;
    private String highPrice;
    private String lowPrice;
    private String tradingVolume;
}
