package com.coin.backend.coinbackend.dto.api.restapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RestApiDto {
    private String status;
    private List<RestApiDataDto> data;
}
